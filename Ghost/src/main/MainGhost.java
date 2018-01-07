/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import communication.Server;

/**
 *
 * @author david
 */
public class MainGhost {

    public static void main(String args[]) {
        System.out.println("Start");
        Server server = new Server();
        server.start();
    }
    
}
