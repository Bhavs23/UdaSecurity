package com.udacity.catpoint.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * AWS Rekognition implementation of ImageService.
 * Uses Amazon's image recognition service to detect cats in images.
 *
 * To use this service, you need to:
 * 1. Create an AWS account
 * 2. Create an IAM user with Rekognition permissions
 * 3. Create a file: src/main/resources/config.properties with:
 *    aws.id=YOUR_ACCESS_KEY_ID
 *    aws.secret=YOUR_SECRET_ACCESS_KEY
 *    aws.region=us-east-2
 */
public class AwsImageService implements ImageService {

    private static final Logger log = LoggerFactory.getLogger(AwsImageService.class);
    private static final String CREDENTIAL_FILE = "/config.properties";

    private RekognitionClient rekognitionClient;

    public AwsImageService() {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream(CREDENTIAL_FILE)) {
            if (is == null) {
                log.warn("Config file not found: {}. AWS Rekognition will not work.", CREDENTIAL_FILE);
                log.warn("To use AWS Rekognition, create src/main/resources/config.properties with:");
                log.warn("aws.id=YOUR_ACCESS_KEY_ID");
                log.warn("aws.secret=YOUR_SECRET_ACCESS_KEY");
                log.warn("aws.region=us-east-2");
                return;
            }
            props.load(is);
        } catch (IOException e) {
            log.error("Unable to load configuration file: {}", CREDENTIAL_FILE, e);
            return;
        }

        String awsId = props.getProperty("aws.id");
        String awsSecret = props.getProperty("aws.secret");
        String awsRegion = props.getProperty("aws.region");

        AwsCredentials awsCredentials = AwsBasicCredentials.create(awsId, awsSecret);
        this.rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(awsRegion))
                .build();
    }

    /**
     * Uses AWS Rekognition to analyze the image and detect if it contains a cat.
     *
     * @param image The image to analyze
     * @param confidenceThreshold Minimum confidence level (0-100) to identify a cat
     * @return true if a cat is detected with sufficient confidence, false otherwise
     */
    @Override
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshold) {
        if (rekognitionClient == null) {
            log.warn("Rekognition client not initialized. Returning false.");
            return false;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            log.error("Error writing image to byte stream", e);
            return false;
        }

        Image awsImage = Image.builder()
                .bytes(SdkBytes.fromByteArray(os.toByteArray()))
                .build();

        DetectLabelsRequest request = DetectLabelsRequest.builder()
                .image(awsImage)
                .minConfidence(confidenceThreshold)
                .build();

        DetectLabelsResponse result = rekognitionClient.detectLabels(request);

        boolean catDetected = result.labels().stream()
                .anyMatch(label -> label.name().equalsIgnoreCase("cat"));

        if (log.isDebugEnabled()) {
            log.debug("Detected labels: {}",
                    result.labels().stream()
                            .map(label -> label.name() + "(" + label.confidence() + ")")
                            .collect(Collectors.joining(", ")));
        }

        return catDetected;
    }
}