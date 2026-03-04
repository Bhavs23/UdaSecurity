package com.udacity.catpoint.service;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Fake implementation of ImageService that returns random results.
 * Used for testing and development when AWS Rekognition is not available.
 */
public class FakeImageService implements ImageService {

    private final Random random = new Random();

    /**
     * Returns a random true/false result instead of actually analyzing the image.
     *
     * @param image The image to "analyze" (not actually used)
     * @param confidenceThreshold The confidence threshold (not actually used)
     * @return A random boolean value
     */
    @Override
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshold) {
        return random.nextBoolean();
    }
}