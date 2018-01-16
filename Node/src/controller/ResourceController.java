package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Resource;
import view.ResourceView;
import global.Registry;
import java.util.Iterator;

/**
 * Clase que controla los recursos del nodo
 * @author jesus
 */
public class ResourceController {
    private Resource resource = null;
    private ResourceView resourceView = null;
    private List<Resource> resourceList = new ArrayList<>();
    
    /**
     * Constructor que inicializa el recurso y la vista
     * @param resource Recurso
     * @param resourceView Vista
     */
    public ResourceController(Resource resource, ResourceView resourceView) {
        this.resource = resource;        
        this.resourceView = resourceView;        
    }
    
    /**
     * Busca los archivos en el directorio Registry.downloadPath
     * @see Registry
     */
    public void getFileNamesFromDirectory() { 
        System.out.println(Registry.downloadPath);
        File directory = new File(Registry.downloadPath);
        File[] files = directory.listFiles();
        for (File f:files) {            
            resource = new Resource(getSHA1(f.getName()), f.getAbsolutePath(), f.getName());
            resourceList.add(resource);
        }
    }
    
    /**
     * Metodo que devuelve en JSON la lista de recursos del nodo
     * @return 
     */
    public String getNodeResourceList() {
        List<Resource> resourceList = new ArrayList<>();
        File directory = new File(Registry.downloadPath);
        File[] files = directory.listFiles();
        for (File f:files) {            
            resource = new Resource(getSHA1(f.getName()), f.getAbsolutePath(), f.getName());
            resourceList.add(resource);
        }
        String output = resourceListToJson(resourceList);
        return output;
    }
    
    /**
     * Metodo que buscara un recurso especifico por nombre
     * @param resourceName Nombre del recurso
     * @return 
     */
    public Resource searchResource(String resourceName) {
        List<Resource> resourceList = new ArrayList<>();
        File directory = new File(Registry.downloadPath);
        File[] files = directory.listFiles();
        for (File f:files) {            
            resource = new Resource(getSHA1(f.getName()), f.getAbsolutePath(), f.getName());
            if(resource.getName().equals(resourceName)) {
                System.out.println("Recurso encontrado: " + resource.getName());
                return resource;
            }
            resourceList.add(resource);
        }
        System.out.println("Recurso no encontrado: " + resource.getName() + " != " + resourceName);
        return null;
    }
    
    /**
     * Metodo que encripta en SHA1 un texto
     * @param txt Texto a encriptar
     * @return 
     */
    public String getSHA1(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(Registry.encrypted);
            byte[] array = md.digest(txt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Convierte un recurso a JSON
     * @return 
     */
    public String resourceToJson(){
        Gson gson = new Gson();
        return gson.toJson(resource);
    }
    
    /**
     * Convierte un JSON string a una instancia de recurso
     * @param json 
     */
    public void jsonToResource(String json){
        Gson gson = new Gson();
        this.resource = gson.fromJson(json, Resource.class);
    }
    
    /**
     * Convierte la lista de recursos en un JSON
     * @return 
     */
    public String resourceListToJson(){
        Gson gson = new Gson();
        return gson.toJson(resourceList);
    }
    
    /**
     * Convierte una lista de recursos especifica en un JSON
     * @param resourceList Lista de recursos externa
     * @return 
     */
    public String resourceListToJson(List<Resource> resourceList){
        Gson gson = new Gson();
        return gson.toJson(resourceList);
    }
    
    /**
     * Convierte un JSON string en una lista de recursos
     * @param json String JSON
     */
    public void jsonToResourceList(String json){
        Gson gson = new Gson();
        this.resourceList = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());
    }
    
    /**
     * Borrar todo los recursos externos al nodo
     */
    public void deleteExternalResources(){
        Iterator itr = resourceList.iterator();
        while (itr.hasNext()){
            if (!((Resource) itr.next()).getAddress().equals(Registry.nodeController.getNode().getAddress())) {
                itr.remove();
            }
        }
    }
    
    /**
     * Borra todos los recursos
     */
    public void deleteAllResources(){
        resourceList.clear();
    }
    
    /**
     * Obtiene un String de la lista de recursos externos al nodo
     * @return 
     */
    public String getExternalResources(){
        Gson gson = new Gson();
        List<Resource> aux = new ArrayList<Resource>();
        for (Resource resource: resourceList) {
            if (!resource.getAddress().equals(Registry.nodeController.getNode().getAddress())){
                aux.add(resource);
            }
        }
        return gson.toJson(aux);
    }
    
    /**
     * Agrega recursos externos a traves de JSON
     * @param json JSON string
     */
    public void addExternalResources(String json){
        Gson gson = new Gson();
        List<Resource> aux = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());        
        for (Resource resource: aux) {
            this.resourceList.add(resource);
        }
    }
    
    /**
     * Agrega recursos externos en un JSON diferentes a una direccion ip especificada
     * @param json String JSON
     * @param address Direccion ip que no guardara
     */
    public void addExternalResources(String json, String address){
        Gson gson = new Gson();
        List<Resource> aux = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());        
        for (Resource resource: aux) {
            if (!resource.getAddress().equals(address)) {
                this.resourceList.add(resource);
            }            
        }
    }
    
    /**
     * Muestra las lista de recursos
     */
    public void showResourceList() {
        resourceView.showResourceList(resourceList);
    }
}
