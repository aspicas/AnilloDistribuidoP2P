/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.net.Socket;

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
    
    public abstract void disconnet();
    
    @Override
    public abstract void run();
}
