package com.udacity.catpoint.service;

import java.awt.image.BufferedImage;

/**
 * Interface for image analysis services.
 * Implementations of this interface can analyze images to detect cats.
 */
public interface ImageService {
    /**
     * Analyzes an image to determine if it contains a cat.
     *
     * @param image The image to analyze
     * @param confidenceThreshold The minimum confidence level (0-100) required to identify a cat
     * @return true if a cat is detected with confidence above the threshold, false otherwise
     */
    boolean imageContainsCat(BufferedImage image, float confidenceThreshold);
}