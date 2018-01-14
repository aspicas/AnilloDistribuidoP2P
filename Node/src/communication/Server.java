/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.GlobalThread;
import global.Registry;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Server extends GlobalThread{

    private ServerSocket server = null;
       
    public Server() {
        super();
        try {
            this.server = new ServerSocket(Registry.port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    public void listen(){
        System.out.println("Listenning request");
        try {
            while (true) {
                super.client = server.accept();
                ((ServerThread) new ServerThread(super.client)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @Override
    public void run() {
        System.out.println("Starting client server");
        listen();
    }
    
}
