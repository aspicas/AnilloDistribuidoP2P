/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import communication.Server;
import communication.ServerThread;
import java.net.ServerSocket;

/**
 *
 * @author david
 */
public abstract class GlobalThread extends Thread{
    protected Socket client;
    protected DataInputStream input;
    protected DataOutputStream output;

    public GlobalThread() {
        this.client = null;
        this.input = null;
        this.output = null;
    } 
    
    public GlobalThread(Socket client) {
        try {
            this.client = client;
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(GlobalThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void disconnet(){
        try{
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public abstract void run();
}
