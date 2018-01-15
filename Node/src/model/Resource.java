/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import global.Registry;

/**
 *
 * @author jesus
 */
public class Resource {
    private String id;
    private String path;
    private String name;
    private String address;
    
    public Resource() {
        this.id = "";
        this.name = "";
        this.path = "/";
        this.address = Registry.address;
    }

    public Resource(String id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
        System.out.println(Registry.address);
        this.address = Registry.address;
    }        

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
