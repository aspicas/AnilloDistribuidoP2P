package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Resource;
import view.ResourceView;
import global.Registry;

/**
 *
 * @author jesus
 */
public class ResourceController {
    private Resource resource = null;
    private ResourceView resourceView = null;
    private List<Resource> resourceList = new ArrayList<Resource>();
    
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
    
    public String getSHA1(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(Registry.encrypted);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
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
    
    public void showResourceList() {
        resourceView.showResourceList(resourceList);
    }
}
