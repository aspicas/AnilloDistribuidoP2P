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
 *
 * @author david
 */
public class NodeController {
    private Node node = null;
    private NodeView nodeView = null;

    public NodeController(Node node, NodeView nodeView) {
        this.node = node;
        this.node.setNodeId(getSHA1(node.getAddress()));
        this.nodeView = nodeView;
    }
    
    public String getNodeAddress(){
        return node.getAddress();
    }
    
    public Node getNode(){
        return node;
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
    
    public void showNode(){
        nodeView.showNode(node.getNodeId(), node.getAddress(), node.getSuccessor(), node.getPredecessor());
    }
}
