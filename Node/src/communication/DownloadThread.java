/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que se encarga de manejar las peticiones de descarga de recursos
 * @author david
 */
public class DownloadThread extends Thread{
    public Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public String file;
    public String home;

    /**
     * Constructor vacio de la clase
     * @param socket 
     */
    public DownloadThread (Socket socket) {
        this.home = System.getProperty("user.home");
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Desconexion del nodo
     */
    public void desconectar(){
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Metodo encargado de enviar el archivo solicitado y de llevar el numero de descargas
     * @throws IOException 
     */
    public void sendFile() throws IOException {    
        System.out.println(Registry.downloadPath);
        System.out.println(Registry.downloadPath);
        Registry.downloadNumber++;
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        };

        try {
            File myFile = new File(Registry.downloadPath + file);
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = socket.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
        }
        finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
        }
    }

    /**
     * Metodo encargado de correr el nodo en segundo plano
     */
    @Override
    public void run(){
        try {
            this.file = input.readUTF();
            sendFile();
        }
        catch (Exception e) {
            Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE,null,e);
        }
        desconectar();
    }
}
