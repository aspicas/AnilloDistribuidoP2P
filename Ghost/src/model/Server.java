/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import global.GlobalThread;
import global.Registry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Server extends GlobalThread {
    private int port = Registry.serverPort;
    private ServerSocket server = null;
    
    public Server(Socket client, DataInputStream input, DataOutputStream output) {
        super(client, input, output);
        
        try {
            this.server = new ServerSocket(Registry.serverPort);
            super.client = server.accept();
            super.input = new DataInputStream(super.client.getInputStream());
            super.output = new DataOutputStream(super.client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listen(){
        try {
            while (true) {                
                super.client = server.accept();
                ((ServerThread) new ServerThread(super.client, super.input, super.output)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        listen();
    }
    
}
