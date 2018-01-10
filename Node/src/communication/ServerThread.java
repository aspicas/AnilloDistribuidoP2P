/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.GlobalThread;
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
public class ServerThread extends GlobalThread {
    
    private DataInputStream input;
    private DataOutputStream output;    
    
    public ServerThread(Socket client) {
        super(client);
        try {
            input = new DataInputStream(super.client.getInputStream());
            output = new DataOutputStream(super.client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void offerResource(){
        
    }
    
    public void answerResource(){
        
    }
    
    public void downloadNumberXVideo(){
        
    }
    
    public void definingRequest(){
        System.out.println("Start defining request");        
        try {
            String request = "";

            //establish connection         
            request = input.readUTF();
            System.out.println("request: " + request);
            if (request.equals(Registry.startCommunication)) {
                output.writeUTF(Registry.startCommunication);
                request = input.readUTF();
                System.out.println("request: " + request);
                switch (request) {
                    case Registry.changePredecessor: //Talking to predecessor
                        //Change predecsessor
                        output.writeUTF(Registry.changePredecessor);
                        request = input.readUTF();
                        System.out.println("request: " + request);
                        Registry.nodeController.getNode().setPredecessor(request);
                        System.out.println("Predecessor: " + Registry.nodeController.getNode().getPredecessor());
                        //Exchange of resources
                        request = input.readUTF();
                        System.out.println("request: " + request);
                        if (request.equals(Registry.giveResources)) {
                            Registry.resourceController.deleteExternalResources();
                            request = input.readUTF();
                            System.out.println("request: " + request);
                            Registry.resourceController.addExternalResources(request);
                        } else {
                            output.writeUTF(Registry.invalidRequest);
                        }
                        //End communication
                        output.writeUTF(Registry.endCommunication);
                        break;
                    case Registry.changeSuccessor: //Talking to successor 
                        //Change successor
                        output.writeUTF(Registry.changeSuccessor);
                        request = input.readUTF();
                        Registry.nodeController.getNode().setSuccessor(request);
                        System.out.println("Successor: " + Registry.nodeController.getNode().getSuccessor());
                        //Exchange of resources
                        request = input.readUTF();
                        System.out.println("request: " + request);
                        if (request.equals(Registry.getResources)) {
                            System.out.println(Registry.resourceController.getNodeResourceList());
                            output.writeUTF(Registry.resourceController.getNodeResourceList());
                        } else {
                            System.out.println(Registry.invalidRequest);
                            output.writeUTF(Registry.invalidRequest);
                        }
                        //End communication
                        System.out.println(Registry.endCommunication);
                        output.writeUTF(Registry.endCommunication);
                        break;
                    default:
                        System.out.println(Registry.invalidRequest);
                        output.writeUTF(Registry.invalidRequest);
                        break;
                }
            } else {
                System.out.println(Registry.invalidRequest);
                output.writeUTF(Registry.endCommunication);
            }
            Registry.nodeController.showNode();
            Registry.resourceController.showResourceList();
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        System.out.println("Defining request");
        definingRequest();      
    }
    
}
