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

/**
 *
 * @author david
 */
public abstract class GlobalThread extends Thread{
    protected Socket client;
   
    public GlobalThread(Socket client) {
        this.client = client;
    }
    
    public abstract void disconnet();
    
    @Override
    public abstract void run();
}
