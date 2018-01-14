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
 *
 * @author david
 */
public class NodeController {
    private Node node = null;
    private NodeView nodeView = null;
    private ArrayList<Node> fingerTable = new ArrayList<Node>();

    public NodeController(Node node, NodeView nodeView) {
        this.node = node;
        this.node.setNodeId(getSHA1(node.getAddress()));
        this.nodeView = nodeView;
    }
    
    public String getNodeAddress(){
        return node.getAddress();
    }
    
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
    
    public String nodeToJson(){
        Gson gson = new Gson();
        return gson.toJson(node);
    }
    
    public void jsonToNode(String json){
        Gson gson = new Gson();
        this.node = gson.fromJson(json, Node.class);
    }
    
    public void addNodeToFingerTable(){
        fingerTable.add(node);
        Collections.sort(fingerTable);
        setNodeSuccessorAndPredecessor();
    }
    
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
    
    public void showNode(){
        nodeView.showNode(node.getNodeId(), node.getAddress(), node.getSuccessor(), node.getPredecessor());
    }
    
    public void showFingerTable(){
        nodeView.showFingerTable(this.fingerTable);
    }
}
