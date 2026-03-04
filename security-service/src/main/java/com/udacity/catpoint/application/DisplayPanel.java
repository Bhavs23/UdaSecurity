package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.securityservice.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Displays the current status of the system.
 */
public class DisplayPanel extends JPanel implements StatusListener {

    private JLabel currentStatusLabel;

    public DisplayPanel() {
        super();
        setLayout(new MigLayout());

        currentStatusLabel = new JLabel();
        currentStatusLabel.setFont(StyleService.HEADING_FONT);

        notify(AlarmStatus.NO_ALARM);
        add(currentStatusLabel);
    }

    @Override
    public void notify(AlarmStatus status) {
        currentStatusLabel.setText(status.getDescription());
        currentStatusLabel.setBackground(status.getColor());
        currentStatusLabel.setOpaque(true);
    }

    @Override
    public void catDetected(boolean catDetected) {
        // Not needed for this panel
    }

    @Override
    public void sensorStatusChanged() {
        // Not needed for this panel
    }
}