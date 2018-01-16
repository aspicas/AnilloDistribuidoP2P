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
import communication.Server;
import communication.ServerThread;

/**
 * Clase abstracta global para manejar los hilos en el nodo
 * @author david
 */
public abstract class GlobalThread extends Thread{
    protected Socket client;

    /**
     * Constructor vacio de la clase
     */
    public GlobalThread() {
        this.client = null;
    }
    
    /**
     * Constructor que inicializa el cliente
     * @param client Cliente socket
     */
    public GlobalThread(Socket client) {
        this.client = client;
    }
    
    /**
     * Cerrar el socket del cliente
     */
    public void disconnet(){
        try{
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo heredado de la clase Thread
     * @see Thread
     */
    @Override
    public abstract void run();
}
