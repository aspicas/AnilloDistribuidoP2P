/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * Vista del nodo
 * @author david
 */
public class NodeView {
    
    /**
     * Metodo que muestra los valores del nodo
     * @param id Id unico del nodo
     * @param address Direccion ip del nodo
     * @param successor Sucesor del nodo
     * @param predecessor Predecesor del nodo
     */
    public void showNode(String id, String address, String successor, String predecessor) {
        System.out.println("Node");
        System.out.println("id: " + id);
        System.out.println("address: " + address);
        System.out.println("successor: " + successor);
        System.out.println("predecessor: " + predecessor);
    }
}
