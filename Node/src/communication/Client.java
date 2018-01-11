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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Client {
    private Socket client = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Client() {
        try {
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
    
    public void searchResource() throws IOException {
        int socketPort = Registry.port;
        String server = Registry.nodeController.getNode().getPredecessor();
        String fileToReceive = Registry.downloadPath + "coins_drop.mp3";
        int fileSize = 99999999;
        
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        
        try {
            sock = new Socket(server, socketPort);
            System.out.println("Conectando...");

            // receive file
            byte [] mybytearray  = new byte [fileSize];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(fileToReceive);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
               bytesRead =
                  is.read(mybytearray, current, (mybytearray.length-current));
               if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("Archivo " + fileToReceive
                + " descargado (" + current + " bytes leidos)");
        } finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
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
                    } else {
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
