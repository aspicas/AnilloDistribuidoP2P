/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import global.Registry;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Resource; // VER

/**
 * Clase encargada de hacer una peticion a otro nodo
 * @author david
 * @see Thread
 */
public class Client extends Thread{
    private Socket client = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    /**
     * Constructor vacio de la clase
     */
    public Client() {
        try {
            System.out.println("Ghost" + Registry.ghost);
            System.out.println("Ghost" + Registry.ghost);
            this.client = new Socket(Registry.ghost, Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que busca el recurso por nombre
     * @param resourceName Nombre del recurso
     * @return
     * @throws IOException 
     */
    public Resource searchResource(String resourceName) throws IOException {
        return Registry.resourceController.searchResource(resourceName);
    }
    
    /**
     * Metodo que abre un canal de comunicacion con el sucesor
     * @throws IOException
     */
    public void openCommunicationChannelToSuccessor(){
        try {            
            this.client = new Socket(Registry.nodeController.getNode().getSuccessor(), Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que abre un canal de comunicacion con el predecesor
     * @throws IOException
     */
    public void openCommunicationChannelToPredecessor(){
        try {            
            this.client = new Socket(Registry.nodeController.getNode().getPredecessor(), Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que abre un canal de comunicacion con el nodo fantasma
     * @throws IOException
     */
    public void openCommunicationChannelToGhost(){
        try {            
            this.client = new Socket(Registry.ghost, Registry.port);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que busca su sucesor y predecesor en el fantasma
     * @throws IOException
     */
    public void defineGhostRing(){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            switch (response) {
                case Registry.startCommunication:
                    System.out.println("establishing node in the ring");
                    output.writeUTF(Registry.nodeController.nodeToJson());
                    response = input.readUTF();
                    System.out.println("json: " + response);
                    Registry.nodeController.jsonToNode(response);
                    Registry.nodeController.showNode();
                    output.writeUTF(Registry.endCommunication);
                    break;
                default:
                    System.out.println(response);                    
                    break;
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * Metodo encargado de la salida del nodo en el lado del fantasma
     * @throws IOException
     */
    public void deleteNodeInGhost(){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            if (response.equals(Registry.startCommunication)) {
                System.out.println("delete node in the ring");
                output.writeUTF(Registry.deleteNode);
                output.writeUTF(Registry.nodeController.nodeToJson());
                response = input.readUTF();
                System.out.println("response: " + response);
            } else {
                System.out.println(response);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que actualiza el los sucesores y predecesores del nodo con sus respectivos recursos
     * @param command 
     * @throws IOException
     */
    public void updateNodeRing(String command){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            switch (response) {
                case Registry.startCommunication:
                    System.out.println("update node in the ring");
                    System.out.println(command);
                    output.writeUTF(command);
                    response = input.readUTF();
                    System.out.println("response: " + response);
                    if (response.equals(Registry.changePredecessor)) { //Talking to successor
                        //Change Predeccessor
                        output.writeUTF(Registry.nodeController.getNode().getAddress());
                        //Exchange of resources
                        output.writeUTF(Registry.giveResources);
                        Registry.resourceController.showResourceList();
                        output.writeUTF(Registry.resourceController.getNodeResourceList());
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    } else if (response.equals(Registry.changeSuccessor)) { //Talking to predeccessor
                        //Change Successor
                        System.out.println(Registry.nodeController.getNode().getAddress());
                        output.writeUTF(Registry.nodeController.getNode().getAddress());
                        //Exchange of resources
                        output.writeUTF(Registry.getResources);
                        response = input.readUTF();
                        System.out.println("response: " + response);
                        Registry.resourceController.addExternalResources(response);
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    }else {
                        //End Communication
                        response = input.readUTF();
                        System.out.println("response: " + response);
                    }
                default:
                    System.out.println(Registry.invalidCommand);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (command.equals(Registry.changePredecessor)) {
            Registry.nodeController.showNode();
            Registry.resourceController.showResourceList();
        }
    }
    
    /**
     * Salida del nodo del anillo
     * @param command 
     * @throws IOException
     */
    public void exitNodeInRing(String command){
        try {
            String response = "";
            
            //establish connection
            output.writeUTF(Registry.startCommunication);
            response = input.readUTF();
            System.out.println("response: " + response);
            if (response.equals(Registry.startCommunication)) {
                output.writeUTF(command);
                response = input.readUTF();
                if (response.equals(Registry.changePredecessor)) {
                    //Change Predeccessor
                    System.out.println("predecessor: " + Registry.nodeController.getNode().getPredecessor());
                    output.writeUTF(Registry.nodeController.getNode().getPredecessor());
                    //Exchange of resources                        
                    output.writeUTF(Registry.deleteNode);                    
                    output.writeUTF(Registry.resourceController.getExternalResources());                                        
                    //End Communication
                    response = input.readUTF();
                    System.out.println("response: " + response);
                } else if (response.equals(Registry.changeSuccessor)){
                    //Change Predeccessor
                    System.out.println("successor: " + Registry.nodeController.getNode().getSuccessor());
                    output.writeUTF(Registry.nodeController.getNode().getSuccessor());
                    //Exchange of resources
                    output.writeUTF(Registry.deleteNode);
                    //End Communication
                    response = input.readUTF();
                    System.out.println("response: " + response);
                } else {
                    System.out.println(Registry.invalidRequest);
                }
            } else {
                System.out.println(Registry.invalidCommand);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Desconexion del cliente con el nodo servidor
     * @exception Exception
     * @exception IOException
     */
    public void disconnet(){
        try {
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
