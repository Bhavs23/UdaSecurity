package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.securityservice.SecurityService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Main GUI for the security application.
 */
public class CatpointGui extends JFrame implements StatusListener {

    private SecurityService securityService;

    private DisplayPanel displayPanel = new DisplayPanel();
    private ControlPanel controlPanel;
    private SensorPanel sensorPanel;
    private ImagePanel imagePanel;

    public CatpointGui(SecurityService securityService) {
        this.securityService = securityService;
        securityService.addStatusListener(this);

        setLayout(new MigLayout());

        controlPanel = new ControlPanel(securityService);
        sensorPanel = new SensorPanel(securityService);
        imagePanel = new ImagePanel(securityService);

        add(displayPanel, "wrap");
        add(imagePanel, "wrap");
        add(controlPanel, "wrap");
        add(sensorPanel);
    }

    @Override
    public void notify(AlarmStatus status) {
        displayPanel.notify(status);
    }

    @Override
    public void catDetected(boolean catDetected) {
        displayPanel.catDetected(catDetected);
    }

    @Override
    public void sensorStatusChanged() {
        displayPanel.sensorStatusChanged();
    }
}