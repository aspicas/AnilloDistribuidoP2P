/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import controller.NodeController;
import model.Node;
import view.NodeView;

/**
 * Clase con parametros generales de la aplicacion
 * @author david
 */
public class Registry {
    //Communication
    public static final String address = "192.168.0.107";
    public static final int port = 2000;
    
    //Encrypted
    public static final String encrypted = "SHA1";
    
    //Commands
    public static final String startCommunication = "START";
    public static final String endCommunication = "FINISH";
    public static final String invalidRequest = "Invalid request";
    public static final String deleteNode = "Delete node";
    
    //Controller
    public static final NodeController nodeController = new NodeController(new Node(), new NodeView());
}
