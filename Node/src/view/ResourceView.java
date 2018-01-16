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
 * Vista del recurso
 * @author jesus
 */
public class ResourceView {
    
    /**
     * Muestra los valores de un recurso
     * @param id Id unico del recurso
     * @param path Direccion del recurso
     * @param name Nombre del recurso
     */
    public void showResource(String id, String path, String name) {
        System.out.println("Node");
        System.out.println("id: " + id);
        System.out.println("path: " + path);
        System.out.println("name: " + name);
    }
    
    /**
     * Muestra la lista de recursos con su id, camino absoluto, nombre y direccion
     * @param resourceList 
     */
    public void showFullPathResourceList(List<Resource> resourceList){
        for (Resource resource : resourceList) {
            System.out.println("Resource: {id: "+ resource.getId() +", "
            + "path: "+ resource.getPath() +", "
                    + "name: "+ resource.getName() +", "
                            + "address: "+ resource.getAddress() +"}");
        }
    }
    
    /**
     * Muestra los recursos con su id nombre y direccion
     * @param resourceList 
     */
    public void showResourceList(List<Resource> resourceList){
        for (Resource resource : resourceList) {
            System.out.println("Resource: {id: "+ resource.getId() +", "
                    + "name: "+ resource.getName() +", "
                            + "address: "+ resource.getAddress() +"}");
        }
    }
}
