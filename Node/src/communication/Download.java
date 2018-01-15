/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Download extends Thread {
    public ServerSocket server = null;
    public Socket client = null;
    public DataOutputStream output = null;
    public DataInputStream input = null;

    /**
     * Constructor de la clase
     *
     */
    public Download() {
        try {
            this.server = new ServerSocket(Registry.downloadPort);            
        }
        catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion de escucha del Socket
     */
    public void Listening(){
        try {
            while (true){
                this.client = server.accept();
                ((DownloadThread) new DownloadThread(client)).start();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion para mantener el Socket escuchando por una nueva peticion.
     */
    @Override
    public void run(){
        Listening();
    }

}
