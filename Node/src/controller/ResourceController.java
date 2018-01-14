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
 *
 * @author jesus
 */
public class ResourceController {
    private Resource resource = null;
    private ResourceView resourceView = null;
    private List<Resource> resourceList = new ArrayList<>();
    
    public ResourceController(Resource resource, ResourceView resourceView) {
        this.resource = resource;        
        this.resourceView = resourceView;        
    }
    
    public void getFileNamesFromDirectory() {        
        File directory = new File(Registry.downloadPath);
        File[] files = directory.listFiles();
        for (File f:files) {            
            resource = new Resource(getSHA1(f.getName()), f.getAbsolutePath(), f.getName());
            resourceList.add(resource);
        }
    }
    
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
    
    public String resourceToJson(){
        Gson gson = new Gson();
        return gson.toJson(resource);
    }
    
    public void jsonToResource(String json){
        Gson gson = new Gson();
        this.resource = gson.fromJson(json, Resource.class);
    }
    
    public String resourceListToJson(){
        Gson gson = new Gson();
        return gson.toJson(resourceList);
    }
    
    public String resourceListToJson(List<Resource> resourceList){
        Gson gson = new Gson();
        return gson.toJson(resourceList);
    }
    
    public void jsonToResourceList(String json){
        Gson gson = new Gson();
        this.resourceList = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());
    }
    
    public void deleteExternalResources(){
        Iterator itr = resourceList.iterator();
        while (itr.hasNext()){
            if (!((Resource) itr.next()).getAddress().equals(Registry.nodeController.getNode().getAddress())) {
                itr.remove();
            }
        }
    }
    
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
    
    public void addExternalResources(String json){
        Gson gson = new Gson();
        List<Resource> aux = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());        
        for (Resource resource: aux) {
            this.resourceList.add(resource);
        }
    }
    
    public void addExternalResources(String json, String address){
        Gson gson = new Gson();
        List<Resource> aux = gson.fromJson(json, new TypeToken<List<Resource>>(){}.getType());        
        for (Resource resource: aux) {
            if (!resource.getAddress().equals(address)) {
                this.resourceList.add(resource);
            }            
        }
    }
    
    public void showResourceList() {
        resourceView.showResourceList(resourceList);
    }
}
