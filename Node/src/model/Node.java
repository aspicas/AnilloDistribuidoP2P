/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Clase nodo
 * @author david
 */
public class Node {
    
    private String nodeId;
    private String address;
    private String successor;
    private String predecessor;

    /**
     * Constructor vacio de la clase
     */
    public Node() {
        this.nodeId = "";
        this.address = "";
        this.successor = "";
        this.predecessor = "";
    }

    /**
     * Constructor inicializado con la direccion ip
     * @param address Direccion ip del nodo
     */
    public Node(String address) {
        this.address = address;
    }

    /**
     * Constructor que inicializa todos los valores del nodo
     * @param nodeId Id unico del nodo
     * @param address Direccion del nodo
     * @param successor Sucesor del nodo
     * @param predecessor Predecesor del nodo
     */
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

    /**
     * Muestra el contenido del nodo
     * @return 
     */
    @Override
    public String toString() {
        return "Node{" + "nodeId=" + nodeId + ", address=" + address + ", successor=" + successor + ", predecessor=" + predecessor + '}';
    }
    
}
