/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Server;
import model.ServerThread;

/**
 *
 * @author david
 */
public abstract class GlobalThread extends Thread{
    protected Socket client;

    public GlobalThread() {
        this.client = null;
    }
    
    public GlobalThread(Socket client) {
        this.client = client;
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
