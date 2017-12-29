/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import model.Server;

/**
 *
 * @author david
 */
public class Ghost {

    public static void main(String args[]) {
        Socket client = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        
        Server server = new Server(client, input, output);
        server.start();
    }
    
}
