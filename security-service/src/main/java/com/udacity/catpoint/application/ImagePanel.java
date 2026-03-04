package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.securityservice.SecurityService;
import com.udacity.catpoint.securityservice.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Component that handles image capture and analysis for cat detection.
 * Provides UI controls for image upload and processing.
 */
public class ImagePanel extends JPanel implements StatusListener {

    private final SecurityService service;
    private JLabel statusDisplay;
    private JLabel imageDisplay;
    private BufferedImage capturedImage;

    private static final int DISPLAY_WIDTH = 300;
    private static final int DISPLAY_HEIGHT = 225;

    public ImagePanel(SecurityService securityService) {
        super();
        this.service = securityService;
        this.service.addStatusListener(this);

        initializeLayout();
        setupComponents();
        assembleInterface();
    }

    /**
     * Sets up the panel layout configuration
     */
    private void initializeLayout() {
        setLayout(new MigLayout());
    }

    /**
     * Creates and configures UI components
     */
    private void setupComponents() {
        // Status text at top
        statusDisplay = new JLabel("Camera Feed");
        statusDisplay.setFont(StyleService.HEADING_FONT);

        // Image preview area
        imageDisplay = new JLabel();
        imageDisplay.setBackground(Color.WHITE);
        imageDisplay.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        imageDisplay.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    /**
     * Assembles all components into the panel
     */
    private void assembleInterface() {
        add(statusDisplay, "span 3, wrap");
        add(imageDisplay, "span 3, wrap");
        add(createUploadButton());
        add(createAnalysisButton());
    }

    /**
     * Creates button for uploading images
     */
    private JButton createUploadButton() {
        JButton uploadBtn = new JButton("Refresh Camera");
        uploadBtn.addActionListener(e -> handleImageUpload());
        return uploadBtn;
    }

    /**
     * Creates button for analyzing images
     */
    private JButton createAnalysisButton() {
        JButton analyzeBtn = new JButton("Scan Picture");
        analyzeBtn.addActionListener(e -> handleImageAnalysis());
        return analyzeBtn;
    }

    /**
     * Handles the image upload process
     */
    private void handleImageUpload() {
        JFileChooser fileSelector = new JFileChooser();
        fileSelector.setCurrentDirectory(new File("."));
        fileSelector.setDialogTitle("Select Picture");
        fileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileSelector.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        loadAndDisplayImage(fileSelector.getSelectedFile());
    }

    /**
     * Loads image from file and displays it
     */
    private void loadAndDisplayImage(File imageFile) {
        try {
            capturedImage = ImageIO.read(imageFile);
            displayScaledImage(capturedImage);
            repaint();
        } catch (IOException | NullPointerException exception) {
            showErrorDialog("Invalid image selected.");
        }
    }

    /**
     * Scales and displays the provided image
     */
    private void displayScaledImage(BufferedImage original) {
        if (original == null) {
            return;
        }

        ImageIcon icon = new ImageIcon(original);
        Image scaledImg = icon.getImage().getScaledInstance(
                DISPLAY_WIDTH,
                DISPLAY_HEIGHT,
                Image.SCALE_SMOOTH
        );
        imageDisplay.setIcon(new ImageIcon(scaledImg));
    }

    /**
     * Sends current image for cat detection analysis
     */
    private void handleImageAnalysis() {
        service.processImage(capturedImage);
    }

    /**
     * Shows error message to user
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void notify(AlarmStatus status) {
        // No action needed for alarm status changes
    }

    @Override
    public void catDetected(boolean detected) {
        String statusText = detected ?
                "DANGER - CAT DETECTED" :
                "Camera Feed - No Cats Detected";
        statusDisplay.setText(statusText);
    }

    @Override
    public void sensorStatusChanged() {
        // No action needed for sensor status changes
    }
}