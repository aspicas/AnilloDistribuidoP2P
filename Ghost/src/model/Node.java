/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Clase modelo para implementar los nodos
 * Implementa un Comparable para ordenar sus valores en una lista
 * @author david
 * @see Comparable
 */
public class Node implements Comparable{
    
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
     * Contructor que inicializa el nodo con una direccion ip
     * @param address Direccion ip
     */
    public Node(String address) {
        this.address = address;
    }

    /**
     * Constructor que inicializa todos los valores del nodo
     * @param nodeId Id unico del nodo
     * @param address Direccion ip
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
     * Metodo para comparar nodos entre si
     * @param o Objeto de tipo Node
     * @return 
     */
    @Override
    public int compareTo(Object o) {
        String compareId=((Node) o).getNodeId();
        /* For Ascending order*/
        return this.nodeId.compareTo(compareId);        
    }
}
