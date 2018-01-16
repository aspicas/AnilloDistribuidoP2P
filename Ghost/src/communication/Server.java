/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.GlobalThread;
import global.Registry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de manejar el puerto de escucha del servidor
 * @author david
 */
public class Server extends GlobalThread {    
    
    private ServerSocket server = null;
    
    /**
     * Constructor de la clase
     */
    public Server() {
        super();        
        try {
            this.server = new ServerSocket(Registry.port);
            super.client = server.accept();            
            super.input = new DataInputStream(super.client.getInputStream());
            super.output = new DataOutputStream(super.client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo encargado de escuchar las peticiones de los clientes.
     * @throws IOException
     * @throws Exception
     * @see GlobalThread
     */
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
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo heredado del GlobalThread que corre el hilo el puerto
     * de escucha en segundo plano.
     */
    @Override
    public void run() {
        System.out.println("Starting ghost server");
        listen();
    }
    
}