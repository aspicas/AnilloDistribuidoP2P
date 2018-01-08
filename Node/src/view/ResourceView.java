/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import controller.ResourceController;

import java.util.List;
import model.Resource;

/**
 *
 * @author jesus
 */
public class ResourceView {
    
    public void showResource(String id, String path, String name) {
        System.out.println("Node");
        System.out.println("id: " + id);
        System.out.println("path: " + path);
        System.out.println("name: " + name);
    }
    
    public void showFullPathResourceList(List<Resource> resourceList){
        for (Resource resource : resourceList) {
            System.out.println("Resource: {id: "+ resource.getId() +", "
            + "path: "+ resource.getPath() +", "
                    + "name: "+ resource.getName() +", "
                            + "address: "+ resource.getAddress() +"}");
        }
    }
    
    public void showResourceList(List<Resource> resourceList){
        for (Resource resource : resourceList) {
            System.out.println("Resource: {id: "+ resource.getId() +", "
                    + "name: "+ resource.getName() +", "
                            + "address: "+ resource.getAddress() +"}");
        }
    }
}
