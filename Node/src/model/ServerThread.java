/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import global.GlobalThread;
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

    @Override
    public void disconnet() {
        try {
            super.client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @Override
    public void run() {
        
    }
    
}
