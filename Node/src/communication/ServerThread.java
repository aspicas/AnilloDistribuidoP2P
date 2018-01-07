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
            if (request == Registry.startCommunication) {
                output.writeUTF(Registry.startCommunication);
                request = input.readUTF();
                System.out.println("request: " + request);
                switch (request) {
                    case Registry.changePredeccessor:
                        output.writeUTF(Registry.changePredeccessor);
                        request = input.readUTF();
                        Registry.nodeController.getNode().setPredecessor(request);
                        output.writeUTF(Registry.endCommunication);
                        break;
                    case Registry.changeSuccessor:
                        output.writeUTF(Registry.changePredeccessor);
                        request = input.readUTF();
                        Registry.nodeController.getNode().setSuccessor(request);
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
