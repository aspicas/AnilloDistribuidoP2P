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
public class ServerThread extends GlobalThread{

    public ServerThread(Socket client) {
        super(client);
    }
    
    public void defineRing(){
        System.out.println("Start defining ring");
        try {
            String request = "";
            
            //establish connection
            request = super.input.readUTF();
            System.out.println("request: " + request);            
            switch (request) {
                case Registry.startCommunication:                     
                    output.writeUTF(Registry.startCommunication);
                    break;
                default:
                    System.out.println("Invalid request");
                    output.writeUTF(Registry.endCommunication);
                    break;
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Define ring action");
        defineRing();
    }
    
}
