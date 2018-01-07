package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import communication.Client;
import communication.Server;
import global.Registry;

/**
 *
 * @author david
 */
public class MainNode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start server");
        Server server = new Server();
        server.start();
        
        Client client = new Client();
        client.defineGhostRing();
        client.disconnet();
        client.changeCommunicationChannelToSuccessor();
        client.updateNodeRing(Registry.changePredeccessor);
        client.disconnet();
        client.changeCommunicationChannelToPredecessor();
        client.updateNodeRing(Registry.changeSuccessor);
    }
}
