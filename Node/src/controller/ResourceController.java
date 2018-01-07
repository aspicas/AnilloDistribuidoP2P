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
    
    public ResourceController(Resource resource, ResourceView resourceView) {
        this.resource = resource;
        this.resource.setPath(Registry.path);
        this.resourceView = resourceView;
    }
    
    public List<String> getFileNamesFromDirectory(String path) {
        List<String> filesList = new ArrayList<String>();
        
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File f:files) {
            filesList.add(f.getAbsolutePath());
        }
        
        return filesList;
    }
    
    public void showFileNamesFromFilesList(List<String> filesList) {
        resourceView.showFileNamesFromFilesList(filesList);
    }
}
