/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author david
 */
public abstract class GlobalThread extends Thread{
    protected Socket client;
    protected DataInputStream input;
    protected DataOutputStream output;

    public GlobalThread(Socket client, DataInputStream input, DataOutputStream output) {
        this.client = client;
        this.input = input;
        this.output = output;
    } 
    
    public abstract void disconnet();
    
    @Override
    public abstract void run();
}
