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
    public static final String ghost = "192.168.1.100";
    public static final int port = 2000;
    public static final String address = "192.168.1.106";
    
    //Controller
    public static NodeController nodeController = new NodeController(new Node(address), new NodeView());
    public static ResourceController resourceController = new ResourceController(new Resource(), new ResourceView());
    
    //Encrypted
    public static final String encrypted = "SHA1";
    
    //Commands
    public static final String startCommunication = "START";
    public static final String endCommunication = "FINISH";
    public static final String invalidRequest = "Invalid request";
    public static final String changePredecessor = "Send Predeccessor";
    public static final String changeSuccessor = "Send Succesor";
    public static final String getResources = "Get Resources";
    public static final String giveResources = "Give Resources";
    
    //Paths
    public static final String downloadPath = "/home/jesus/Downloads/NetBeans_Downloads/";
}
