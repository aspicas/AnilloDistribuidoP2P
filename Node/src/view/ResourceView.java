/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import controller.ResourceController;

import java.util.List;

/**
 *
 * @author jesus
 */
public class ResourceView {
    
    public void showFileNamesFromFilesList(List<String> filesList) {
        for (int i = 0; i < filesList.size(); i++) {
            System.out.println(filesList.get(i));
        }
    }
}
