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
            //Predecessor
            predecessor = fingerTable.get(position - 1).getAddress();
//            fingerTable.get(position - 1).setPredecessor(fingerTable.get(position).getAddress());
            fingerTable.get(position - 1).setPredecessor(node.getAddress());
//            node.setPredecessor(predecessor);
            
            //Node
//            fingerTable.get(position).setPredecessor(fingerTable.get(position - 1).getAddress());
//            fingerTable.get(position).setSuccessor(fingerTable.get(position + 1).getAddress());            
            
            //Successor
            successor = fingerTable.get(position + 1).getAddress();
//            fingerTable.get(position + 1).setSuccessor(fingerTable.get(position).getAddress());
            fingerTable.get(position + 1).setSuccessor(node.getAddress());
//            node.setSuccessor(successor);
        } else if (position + 1 < max) {
            //Predecessor
            predecessor = fingerTable.get(max - 1).getAddress();
//            fingerTable.get(max - 1).setPredecessor(fingerTable.get(position).getAddress());
            fingerTable.get(max - 1).setPredecessor(node.getAddress());
//            node.setPredecessor(predecessor);
            
            //Node            
//            fingerTable.get(position).setPredecessor(fingerTable.get(max - 1).getAddress());            
//            fingerTable.get(position).setSuccessor(fingerTable.get(position + 1).getAddress());
            
            //Successor
            successor = fingerTable.get(position + 1).getAddress();
//            fingerTable.get(position + 1).setSuccessor(fingerTable.get(position).getAddress());
            fingerTable.get(position + 1).setSuccessor(node.getAddress());
//            node.setSuccessor(successor);            
        } else if (position - 1 >= 0) {
            //Predecessor
            predecessor = fingerTable.get(position - 1).getAddress();
//            fingerTable.get(position - 1).setPredecessor(fingerTable.get(position).getAddress());
            fingerTable.get(position - 1).setPredecessor(node.getAddress());
//            node.setPredecessor(predecessor);
            
            //Node            
//            fingerTable.get(position).setPredecessor(fingerTable.get(position - 1).getAddress());            
//            fingerTable.get(position).setSuccessor(fingerTable.get(0).getAddress());            
            
            //Successor
            successor = fingerTable.get(0).getAddress();
//            node.setSuccessor(successor);
            fingerTable.get(0).setSuccessor(fingerTable.get(position).getAddress());
            fingerTable.get(0).setSuccessor(node.getAddress());
        }                
    }
    
    public void showNode(){
        nodeView.showNode(node.getNodeId(), node.getAddress(), node.getSuccessor(), node.getPredecessor());
    }
    
    public void showFingerTable(){
        nodeView.showFingerTable(fingerTable);
    }
}
