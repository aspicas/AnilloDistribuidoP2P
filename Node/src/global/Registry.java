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
    public static String ghost = "192.168.1.1";
    public static int port = 2000;
    public static NodeController nodeController = new NodeController(new Node("localhost"), new NodeView());
    public static String encrypted = "SHA1";
}
