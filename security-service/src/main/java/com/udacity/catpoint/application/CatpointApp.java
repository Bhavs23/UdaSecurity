package com.udacity.catpoint.application;

import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.SecurityRepository;
import com.udacity.catpoint.securityservice.SecurityService;
import com.udacity.catpoint.service.FakeImageService;
import com.udacity.catpoint.service.ImageService;

import javax.swing.*;

/**
 * Main application entry point.
 */
public class CatpointApp {

    public static void main(String[] args) {
        SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
        ImageService imageService = new FakeImageService();
        SecurityService securityService = new SecurityService(securityRepository, imageService);

        CatpointGui gui = new CatpointGui(securityService);
        gui.setTitle("Very Secure App");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(600, 850);
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
    }
}