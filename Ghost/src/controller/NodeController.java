/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import global.Registry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import model.Node;
import view.NodeView;

/**
 * Clase que controla el look and feel del nodo
 * @author david
 */
public class NodeController {
    private Node node = null;
    private NodeView nodeView = null;
    private ArrayList<Node> fingerTable = new ArrayList<Node>();

    /**
     * Constructor de la clase que inicializa el nodo y la vista
     * @param node Nodo que entra en el anillo
     * @param nodeView Vista del nodo
     */
    public NodeController(Node node, NodeView nodeView) {
        this.node = node;
        this.node.setNodeId(getSHA1(node.getAddress()));
        this.nodeView = nodeView;
    }
    
    /**
     * Metodo que te da la direccion del nodo
     * @return
     */
    public String getNodeAddress(){
        return node.getAddress();
    }
    
    /**
     * Metodo que calcula el SHA-1 de un texto
     * @param txt Texto al que se le calculara el SHA-1
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
     * Metodo que convierte un nodo en un Json
     * @see Node
     * @return 
     */
    public String nodeToJson(){
        Gson gson = new Gson();
        return gson.toJson(node);
    }
    
    /**
     * Metodo que convierte un Json en una instancia de Node
     * @param json 
     */
    public void jsonToNode(String json){
        Gson gson = new Gson();
        this.node = gson.fromJson(json, Node.class);
    }
    
    /**
     * Metodo que agrega un nodo a la tabla finger en orden descendente
     */
    public void addNodeToFingerTable(){
        fingerTable.add(node);
        Collections.sort(fingerTable);
        setNodeSuccessorAndPredecessor();
    }
    
    /**
     * Metodo que estable el sucesor y el predecesor de la tabla finger
     */
    public void setNodeSuccessorAndPredecessor(){
        int position = fingerTable.indexOf(node);
        int max = fingerTable.toArray().length;
        String successor;
        String predecessor;
        
        if (position != -1 && position + 1 < max && position - 1 >= 0){
            System.out.println("Request1");
            //Predecessor
            predecessor = fingerTable.get(position - 1).getAddress();
            node.setPredecessor(predecessor);            
            fingerTable.get(position - 1).setSuccessor(fingerTable.get(position).getAddress());
            
            //Node
            fingerTable.get(position).setPredecessor(fingerTable.get(position - 1).getAddress());
            fingerTable.get(position).setSuccessor(fingerTable.get(position + 1).getAddress());            
            
            //Successor
            successor = fingerTable.get(position + 1).getAddress();
            node.setSuccessor(successor);
            fingerTable.get(position + 1).setPredecessor(fingerTable.get(position).getAddress());
        } else if (position + 1 < max) {
            System.out.println("Request2");
            //Predecessor
            predecessor = fingerTable.get(max - 1).getAddress();
            node.setPredecessor(predecessor);
            fingerTable.get(max - 1).setSuccessor(fingerTable.get(position).getAddress());
            
            //Node
            fingerTable.get(position).setPredecessor(fingerTable.get(max - 1).getAddress());
            fingerTable.get(position).setSuccessor(fingerTable.get(position + 1).getAddress());
            
            //Successor
            successor = fingerTable.get(position + 1).getAddress();
            node.setSuccessor(successor);
            fingerTable.get(position + 1).setPredecessor(fingerTable.get(position).getAddress());            
        } else if (position - 1 >= 0) {
            System.out.println("Request3");
            //Predecessor
            predecessor = fingerTable.get(position - 1).getAddress();
            node.setPredecessor(predecessor);            
            fingerTable.get(position - 1).setSuccessor(fingerTable.get(position).getAddress());            
            
            //Node
            fingerTable.get(position).setPredecessor(fingerTable.get(position - 1).getAddress());            
            fingerTable.get(position).setSuccessor(fingerTable.get(0).getAddress());            
            
            //Successor
            successor = fingerTable.get(0).getAddress();
            node.setSuccessor(successor);
            fingerTable.get(0).setPredecessor(fingerTable.get(position).getAddress());
        }
    }
    
    /**
     * Metodo que borra un nodo
     */
    public void deleteNode(){
        int position = 0;
        int max = fingerTable.toArray().length;
        
        for (Node node: fingerTable){
            if (node.getNodeId().equals(this.node.getNodeId())){
                position = fingerTable.indexOf(node);
            }
        }
        
//        if (!node.getSuccessor().equals(node.getPredecessor())){
//            fingerTable.get(position - 1).setSuccessor(node.getSuccessor());            
//            fingerTable.get(position + 1).setPredecessor(node.getPredecessor());
//        }
        if (position != -1 && position + 1 < max && position - 1 >= 0){
            fingerTable.get(position - 1).setSuccessor(node.getSuccessor());
            fingerTable.get(position + 1).setPredecessor(node.getPredecessor());
        } else if (position + 1 < max) {
            fingerTable.get(max - 1).setSuccessor(node.getSuccessor());
            fingerTable.get(position + 1).setPredecessor(node.getPredecessor());
        } else if (position - 1 >= 0) {
            fingerTable.get(position - 1).setSuccessor(node.getSuccessor());
            fingerTable.get(0).setPredecessor(node.getPredecessor());
        }
        Iterator itr = fingerTable.iterator();
        while (itr.hasNext()){
            if (((Node) itr.next()).getAddress().equals(node.getAddress())) {
                itr.remove();               
            }
        }
        if (max == 2) {
            fingerTable.get(0).setPredecessor("");
            fingerTable.get(0).setSuccessor("");
        }
    }
    
    /**
     * Metodo que muestra el nodo actual
     */
    public void showNode(){
        nodeView.showNode(node.getNodeId(), node.getAddress(), node.getSuccessor(), node.getPredecessor());
    }
    
    /**
     * Metodo que muestra la tabla finger
     */
    public void showFingerTable(){
        nodeView.showFingerTable(this.fingerTable);
    }
}
