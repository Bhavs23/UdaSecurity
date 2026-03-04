package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.data.Sensor;
import com.udacity.catpoint.data.SensorType;
import com.udacity.catpoint.securityservice.SecurityService;
import com.udacity.catpoint.securityservice.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Panel for managing sensors.
 */
public class SensorPanel extends JPanel implements StatusListener {

    private SecurityService securityService;

    private JLabel panelLabel = new JLabel("Sensor Management");
    private JLabel newSensorName = new JLabel("Name:");
    private JLabel newSensorType = new JLabel("Sensor Type:");
    private JTextField newSensorNameField = new JTextField();
    private JComboBox<SensorType> newSensorTypeDropdown = new JComboBox<>(SensorType.values());
    private JButton addNewSensorButton = new JButton("Add New Sensor");

    private JPanel sensorListPanel;
    private JPanel newSensorPanel;

    public SensorPanel(SecurityService securityService) {
        super();
        this.securityService = securityService;
        securityService.addStatusListener(this);

        setLayout(new MigLayout());
        panelLabel.setFont(StyleService.HEADING_FONT);

        newSensorPanel = new JPanel();
        newSensorPanel.setLayout(new MigLayout());
        newSensorPanel.add(newSensorName);
        newSensorPanel.add(newSensorNameField, "width 50:100:200");
        newSensorPanel.add(newSensorType);
        newSensorPanel.add(newSensorTypeDropdown, "wrap");
        newSensorPanel.add(addNewSensorButton, "span 3");

        sensorListPanel = new JPanel();
        sensorListPanel.setLayout(new MigLayout());

        addNewSensorButton.addActionListener(e -> {
            Sensor sensor = new Sensor(newSensorNameField.getText(),
                    (SensorType) newSensorTypeDropdown.getSelectedItem());
            securityService.addSensor(sensor);
            newSensorNameField.setText("");
            updateSensorList();
        });

        updateSensorList();

        add(panelLabel, "wrap");
        add(newSensorPanel, "span, wrap");
        add(sensorListPanel, "span");
    }

    private void updateSensorList() {
        sensorListPanel.removeAll();
        securityService.getSensors().forEach(sensor -> {
            JLabel sensorLabel = new JLabel(String.format("%s(%s): %s",
                    sensor.getName(), sensor.getSensorType().toString(),
                    sensor.getActive() ? "Active" : "Inactive"));

            JButton activateButton = new JButton("Activate");
            JButton deactivateButton = new JButton("Deactivate");
            JButton removeButton = new JButton("Remove");

            activateButton.addActionListener(e -> {
                securityService.changeSensorActivationStatus(sensor, true);
                updateSensorList();
            });

            deactivateButton.addActionListener(e -> {
                securityService.changeSensorActivationStatus(sensor, false);
                updateSensorList();
            });

            removeButton.addActionListener(e -> {
                securityService.removeSensor(sensor);
                updateSensorList();
            });

            sensorListPanel.add(sensorLabel, "width 300:300:300");
            sensorListPanel.add(activateButton);
            sensorListPanel.add(deactivateButton);
            sensorListPanel.add(removeButton, "wrap");
        });

        repaint();
        revalidate();
    }

    @Override
    public void notify(AlarmStatus status) {
        // Not needed for this panel
    }

    @Override
    public void catDetected(boolean catDetected) {
        // Not needed for this panel
    }

    @Override
    public void sensorStatusChanged() {
        updateSensorList();
    }
}