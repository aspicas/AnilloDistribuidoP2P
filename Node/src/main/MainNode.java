package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import communication.Client;
import communication.Server;
import controller.ResourceController;
import global.Registry;
import java.io.IOException;
import java.util.Scanner;
import model.Resource;
import view.ResourceView;

/**
 *
 * @author david
 */
public class MainNode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Registry.resourceController.getFileNamesFromDirectory();
        System.out.println("Start server");
        System.out.println("Start server");
        Server server = new Server();
        server.start();
        
        Client client = new Client();
        client.defineGhostRing();
        client.disconnet();
        
        
        if (!Registry.nodeController.getNode().getPredecessor().equals("")) {
            client.openCommunicationChannelToPredecessor();
            client.updateNodeRing(Registry.changeSuccessor);
            client.disconnet();
        }
        
        if (!Registry.nodeController.getNode().getSuccessor().equals("")) {
            client.openCommunicationChannelToSuccessor();
            client.updateNodeRing(Registry.changePredecessor);
            client.disconnet();
        }
        
        /* CLIENTE*/
        String line = "";
        Scanner scanner = new Scanner(System.in);        
        do {
            while (line.toUpperCase() != "SALIR") {
                System.out.print("$ ");
                line = scanner.nextLine();
                System.out.println(line);
                switch (line) {
                    case Registry.offerResources:
                        Registry.resourceController.showResourceList();
                        break;
                    case Registry.searchResource:
                        client.openCommunicationChannelToPredecessor();
                        client.searchResource(Registry.nodeController.getNode().getPredecessor(), "lenguaje_C.pdf");
                        client.disconnet();
                        break;
                    case Registry.requestStatus:
                        break;
                    case Registry.answerStatus:
                        break;
                    case (Registry.downloadNumberXVideo):
                        break;
                    case (Registry.exit):
                        //Exit proccess of the ring
                        //Update ghost table
                        client.openCommunicationChannelToGhost();
                        client.deleteNodeInGhost();
                        client.disconnet();
                        
                        if (!Registry.nodeController.getNode().getPredecessor().equals("")) {
                            client.openCommunicationChannelToPredecessor();
                            client.exitNodeInRing(Registry.changeSuccessor);
                            client.disconnet();
                        }
                        
                        if (!Registry.nodeController.getNode().getSuccessor().equals("")) {
                            client.openCommunicationChannelToSuccessor();
                            client.exitNodeInRing(Registry.changePredecessor);
                            client.disconnet();
                        }
                        
                        System.exit(0);
                        break;
                    default:
                        System.out.println(Registry.invalidCommand);
                        break;
                }
            }
        } while (!line.equals("EXIT"));
        
        //Proceso de salida del anillo
    }
}
