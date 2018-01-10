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
    public static void main(String[] args) {
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
                    case ("RECURSOS_OFRECIDOS"):
                        
                        break;
                    case ("BUSCAR_RECURSO"):
                        
                        break;
                    case ("ESTADO_SOLICITUDES"):
                        break;
                    case ("ESTADO_RESPUESTAS"):
                        break;
                    case ("NUM_DESCARGAS"):
                        
                    case ("SALIR"):
                        System.exit(0);
                    default:
                        System.out.println("Comando invalido. Intentelo de nuevo.");
                        break;
                }
            }
        } while (!line.equals("EXIT"));/**/
    }
}
