/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Resource; // VER

/**
 *
 * @author david
 */
public class Client extends Thread{
    private Socket client = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Client() {
        try {
            System.out.println("Ghost" + Registry.ghost);
            System.out.println("Ghost" + Registry.ghost);
            this.client = new Socket(Registry.ghost, Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public /*Resource*/ void searchResource(String resourceName) throws IOException {
        Registry.resourceController.searchResource(resourceName);
    }
    
    public void requestStatus(){

    }
    
        
    public void openCommunicationChannelToSuccessor(){
        try {            
            this.client = new Socket(Registry.nodeController.getNode().getSuccessor(), Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openCommunicationChannelToPredecessor(){
        try {            
            this.client = new Socket(Registry.nodeController.getNode().getPredecessor(), Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openCommunicationChannelToGhost(){
        try {            
            this.client = new Socket(Registry.ghost, Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void defineGhostRing(){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            switch (response) {
                case Registry.startCommunication:
                    System.out.println("establishing node in the ring");
                    output.writeUTF(Registry.nodeController.nodeToJson());
                    response = input.readUTF();
                    System.out.println("json: " + response);
                    Registry.nodeController.jsonToNode(response);
                    Registry.nodeController.showNode();
                    output.writeUTF(Registry.endCommunication);
                    break;
                default:
                    System.out.println(response);                    
                    break;
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void deleteNodeInGhost(){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            if (response.equals(Registry.startCommunication)) {
                System.out.println("delete node in the ring");
                output.writeUTF(Registry.deleteNode);
                output.writeUTF(Registry.nodeController.nodeToJson());
                response = input.readUTF();
                System.out.println("response: " + response);
            } else {
                System.out.println(response);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateNodeRing(String command){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            switch (response) {
                case Registry.startCommunication:
                    System.out.println("update node in the ring");
                    System.out.println(command);
                    output.writeUTF(command);
                    response = input.readUTF();
                    System.out.println("response: " + response);
                    if (response.equals(Registry.changePredecessor)) { //Talking to successor
                        //Change Predeccessor
                        output.writeUTF(Registry.nodeController.getNode().getAddress());
                        //Exchange of resources
                        output.writeUTF(Registry.giveResources);
                        Registry.resourceController.showResourceList();
                        output.writeUTF(Registry.resourceController.getNodeResourceList());
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    } else if (response.equals(Registry.changeSuccessor)) { //Talking to predeccessor
                        //Change Successor
                        System.out.println(Registry.nodeController.getNode().getAddress());
                        output.writeUTF(Registry.nodeController.getNode().getAddress());
                        //Exchange of resources
                        output.writeUTF(Registry.getResources);
                        response = input.readUTF();
                        System.out.println("response: " + response);
                        Registry.resourceController.addExternalResources(response);
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    }else {
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    }
                default:
                    System.out.println(Registry.invalidCommand);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (command.equals(Registry.changePredecessor)) {
            Registry.nodeController.showNode();
            Registry.resourceController.showResourceList();
        }
    }
    
    public void exitNodeInRing(String command){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            if (response.equals(Registry.startCommunication)) {
                output.writeUTF(command);
                response = input.readUTF();
                if (response.equals(Registry.changePredecessor)) {
                    //Change Predeccessor
                    System.out.println("predecessor: " + Registry.nodeController.getNode().getPredecessor());
                    output.writeUTF(Registry.nodeController.getNode().getPredecessor());
                    //Exchange of resources                        
                    output.writeUTF(Registry.deleteNode);                    
                    output.writeUTF(Registry.resourceController.getExternalResources());                                        
                    //End Communication
                    response = input.readUTF();
                    System.out.println("response: " + response);
                } else if (response.equals(Registry.changeSuccessor)){
                    //Change Predeccessor
                    System.out.println("successor: " + Registry.nodeController.getNode().getSuccessor());
                    output.writeUTF(Registry.nodeController.getNode().getSuccessor());
                    //Exchange of resources
                    output.writeUTF(Registry.deleteNode);
                    //End Communication
                    response = input.readUTF();
                    System.out.println("response: " + response);
                } else {
                    System.out.println(Registry.invalidRequest);
                }
            } else {
                System.out.println(Registry.invalidCommand);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnet(){
        try {
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
