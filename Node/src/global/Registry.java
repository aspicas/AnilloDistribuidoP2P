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
 *
 * @author david
 */
public class Registry {
    public static final String ghost = "192.168.1.101";
    public static final int port = 2000;
    public static NodeController nodeController = new NodeController(new Node("192.168.1.112"), new NodeView());
    public static final String encrypted = "SHA1";
    public static final String startCommunication = "START";
    public static final String endCommunication = "FINISH";
    public static final String invalidRequest = "Invalid request";
    public static final String changePredeccessor = "Send Predeccessor";
    public static final String changeSuccessor = "Send Succesor";
    public static String path = "/home/jesus/Desktop";
}
