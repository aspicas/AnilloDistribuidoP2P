/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ClientThread extends Thread {
    private Socket client = null;
    private DataInputStream input;
    private DataOutputStream output;
    private String file = "";

    public ClientThread(Socket client, String file) {
        this.client = client;
        this.file = file;
        try {
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
        }
        catch (IOException ex){
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void requestFile(String file){
        try {
            System.out.println("Se solicita el archivo: " + file);
            output.writeUTF(file);
            receiveResource();            
        }
        catch (IOException ex){
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receiveResource () throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        System.out.println("Se comienza a recibir el archivo");
        try {
            byte[] mybytearray = new byte[1024];
            InputStream is = client.getInputStream();
            fos = new FileOutputStream(Registry.downloadPath + this.file);
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, bytesRead);
            bos.flush();
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (client != null) client.close();
        }
    }

    public void desconectar(){        
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run() {
        requestFile(file);
    }
    
    
}
