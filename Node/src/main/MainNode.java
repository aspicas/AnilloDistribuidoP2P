package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import communication.Client;
import communication.Download;
import communication.Load;
import communication.Server;
import controller.ResourceController;
import global.Registry;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import jdk.nashorn.internal.parser.JSONParser;
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
        
        Download download = new Download();
        download.start();
        
        /* CLIENTE*/
        String line = "";
        Scanner scanner = new Scanner(System.in);        
        do {
            while (line.toUpperCase() != "SALIR") {
                System.out.print("$ ");
                line = scanner.nextLine();
                System.out.println(line);
                List<String> items = Arrays.asList(line.split(" "));
                switch (items.get(0)) {
                    case Registry.offerResources:
                        String resources = Registry.resourceController.getExternalResources();
                        Registry.resourceController.deleteAllResources();
                        Registry.resourceController.getFileNamesFromDirectory();
                        Registry.resourceController.addExternalResources(resources);                        
                        Registry.resourceController.showResourceList();
                        break;
                    case Registry.searchResource:
                        //client.openCommunicationChannelToPredecessor();
                        try {
                            Resource resource = client.searchResource(items.get(1));
                            if (resource == null) {
                                /*START DAVID*/
                                Load load = new Load();
                                load.receiveNewFile(Registry.nodeController.getNode().getPredecessor(), items.get(1));
                                System.out.println("Se ha iniciado la descarga.");
                                /*END DAVID*/
                            }
                        } catch (IOException e) {
                            System.out.println("¡Error buscando el recurso!");
                        }
//                        client.disconnet();
                        break;
                    case Registry.requestStatus:
                        break;
                    case Registry.answerStatus:
                        break;
                    case (Registry.downloadNumberXVideo):
                        System.out.println("Hasta la fecha se han descargado " + Registry.downloadNumber + " archivos.");
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
