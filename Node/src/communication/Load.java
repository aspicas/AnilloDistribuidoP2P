/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Load extends Thread {
    public Load() {

    }

    public void receiveNewFile (String ip, String file) {
        try {
            Socket socket = new Socket(ip, 3000);
            ((LoadThread) new LoadThread(socket, file)).start();
        } catch (IOException e) {
            Logger.getLogger(Load.class.getName()).log(Level.SEVERE,null,e);
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {
    }
}
