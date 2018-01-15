/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import controller.NodeController;
import controller.ResourceController;
import model.Node;
import model.Resource;
import view.NodeView;
import view.ResourceView;

/**
 *
 * @author david
 */
public class Registry {
    //Communication
    public static final String ghost = "192.168.43.182";
    public static final int port = 2000;
    public static final int downloadPort = 2001;
    public static final String address = "192.168.43.61";
    //public static final String address = "192.168.43.160";
    
    //Controller
    public static NodeController nodeController = new NodeController(new Node(address), new NodeView());
    public static ResourceController resourceController = new ResourceController(new Resource(), new ResourceView());
    
    //Encrypted
    public static final String encrypted = "SHA1";
    
    //Commands
    public static final String startCommunication = "START";
    public static final String endCommunication = "FINISH";    
    public static final String changePredecessor = "Send Predeccessor";
    public static final String changeSuccessor = "Send Succesor";
    public static final String getResources = "Get Resources";
    public static final String giveResources = "Give Resources";
    public static final String searchResource = "BUSCAR_RECURSOS";
    public static final String requestStatus = "ESTADO_SOLICITUDES";
    public static final String offerResources = "RECURSOS_OFRECIDOS";
    public static final String answerStatus = "ESTADO_RESPUESTAS";
    public static final String downloadNumberXVideo = "NUM_DESCARGASXVIDEO";
    public static final String downloadResource = "Download";
    public static final String exit = "EXIT";
    public static final String deleteNode = "Delete node";
    
    //Messages
    public static final String invalidRequest = "Invalid request";
    public static final String invalidCommand = "Invalid command";
    
    //Paths
    public static final String downloadPath = "/home/jesus/Downloads/NetBeans_Downloads/"; 
    //public static final String downloadPath = "/home/david/Downloads/";
}
