/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author david
 */
public class Node implements Comparable{
    
    private String nodeId;
    private String address;
    private String successor;
    private String predecessor;

    public Node() {
        this.nodeId = "";
        this.address = "";
        this.successor = "";
        this.predecessor = "";
    }

    public Node(String address) {
        this.address = address;
    }

    public Node(String nodeId, String address, String successor, String predecessor) {
        this.nodeId = nodeId;
        this.address = address;
        this.successor = successor;
        this.predecessor = predecessor;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuccessor() {
        return successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor;
    }

    public String getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
    }   

    @Override
    public int compareTo(Object o) {
        String compareId=((Node) o).getNodeId();
        /* For Ascending order*/
        return this.nodeId.compareTo(compareId);        
    }
}
