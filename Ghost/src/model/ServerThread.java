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
public class ServerThread extends GlobalThread{

    public ServerThread(Socket client, DataInputStream input, DataOutputStream output) {
        super(client, input, output);
    }
    
    public void defineRing(){
        System.out.print("Entra");
    }

    @Override
    public void run() {
        defineRing();
    }
    
}
