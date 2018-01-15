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
public class LoadThread extends Thread{
    public Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public String file;
    public String home;

    public LoadThread (Socket socket, String file)
    {
        this.home = System.getProperty("user.home");
        System.out.println("Se creo el hilo de recepcion");
        this.file = file;
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            Logger.getLogger(LoadThread.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void desconectar(){
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(LoadThread.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void requestFile(String file){
        try {
            System.out.println("Se solicita el archivo: " + file);
            output.writeUTF(file);
            receiveFile();
        }
        catch (IOException ex){
            Logger.getLogger(LoadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        }


    public void receiveFile () throws IOException {
        
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        System.out.println("Se comienza a recibir el archivo");
        System.out.println(Registry.downloadPath);
        try {
            byte[] mybytearray = new byte[1024];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(Registry.downloadPath + this.file);
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("¡Archivo descargado satisfactoriamente!");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (socket != null) socket.close();
        }
    }

    @Override
    public void run(){
        System.out.println("Comienza a correr el hilo");
        requestFile(file);
        desconectar();
    }
}
