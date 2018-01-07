/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            this.client = new Socket(Registry.ghost, Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchResource(){
        
    }
    
    public void requestStatus(){

    }
    
        
    public void changeCommunicationChannel(){
        try {            
            this.client = new Socket(Registry.nodeController.getNode().getSuccessor(), Registry.port);
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
                    output.writeUTF(Registry.endCommunication);
                    break;
                default:
                    System.out.println(response);                    
                    break;
            }            
            disconnet();
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateNodeRing(){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            switch (response) {
                case Registry.startCommunication:
                    System.out.println("update node in the ring");
                    output.writeUTF(Registry.nodeController.nodeToJson());
                    response = input.readUTF();                    
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
