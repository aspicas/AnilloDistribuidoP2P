/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de pedir los recursos en segundo plano
 * @author david
 */
public class Load extends Thread {
    public Load() {

    }
    
    /**
     * Peticion de recursos que corre en segundo plano
     * @param ip Direccion del servidor al que le hara la peticion
     * @param file Recurso que pide
     */
    public void receiveNewFile (String ip, String file) {
        try {
            Socket socket = new Socket(ip, Registry.downloadPort);
            ((LoadThread) new LoadThread(socket, file)).start();
        } catch (IOException e) {
            Logger.getLogger(Load.class.getName()).log(Level.SEVERE,null,e);
        } catch (Exception e) {

        }
    }

    /**
     * Metodo sobreescrito de Thread
     */
    @Override
    public void run() {
    }
}
