/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import global.Registry;
import model.Node;
import view.NodeView;

/**
 * Clase que controla al nodo
 * @author david
 */
public class NodeController {
    private Node node = null;
    private NodeView nodeView = null;

    /**
     * Constructor que inicializa el nodo y la vista
     * @param node Nodo
     * @param nodeView Vista
     */
    public NodeController(Node node, NodeView nodeView) {
        this.node = node;
        this.node.setNodeId(getSHA1(node.getAddress()));
        this.nodeView = nodeView;
    }
    
    /**
     * Metodo que indica la direccion del nodo
     * @return 
     */
    public String getNodeAddress(){
        return node.getAddress();
    }
    
    /**
     * Metodo que devuelve el nodo
     * @return 
     */
    public Node getNode(){
        return node;
    }
    
    /**
     * Metodo que encripta en SHA1 un texto
     * @param txt Texto a encriptar
     * @return 
     */
    public String getSHA1(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(Registry.encrypted);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Metodo que convierte un Nodo a un Json
     * @return 
     */
    public String nodeToJson(){
        Gson gson = new Gson();
        return gson.toJson(node);
    }
    
    /**
     * Metodo que convierte un Json a una instancia de Node
     * @param json String en formato JSON
     */
    public void jsonToNode(String json){
        Gson gson = new Gson();
        this.node = gson.fromJson(json, Node.class);
    }
    
    /**
     * Mostrar los valores del nodo actual
     */
    public void showNode(){
        nodeView.showNode(node.getNodeId(), node.getAddress(), node.getSuccessor(), node.getPredecessor());
    }
}
