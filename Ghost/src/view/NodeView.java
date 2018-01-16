/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import model.Node;

/**
 * Vista que muestra los valores del nodo
 * @author david
 */
public class NodeView {
    
    /**
     * Muestra los valores del nodo
     * @param id Id del nodo
     * @param address Direccion ip
     * @param successor Sucessor del nodo
     * @param predecessor Predecesor del nodo
     */
    public void showNode(String id, String address, String successor, String predecessor) {
        System.out.println("Node");
        System.out.println("id: " + id);
        System.out.println("address: " + address);
        System.out.println("successor: " + successor);
        System.out.println("predecessor: " + predecessor);
    }
    
    /**
     * Muestra los valores de la tabla finger
     * @param fingerTable Lista con los valores de la tabla finger
     */
    public void showFingerTable(ArrayList<Node> fingerTable){
        for (Node node: fingerTable){
            System.out.println("Node: {id: "+ node.getNodeId() +", "
                    + "address: "+ node.getAddress() +", "
                            + "successor: "+ node.getSuccessor() +", "
                                    + "predecessor: "+ node.getPredecessor() +"}");
        }
    }
}
