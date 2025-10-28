package org.example.scaneia_dsii.service;

import com.azure.ai.vision.imageanalysis.*;
import com.azure.ai.vision.imageanalysis.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ImageAnalysisService {

    @Value("${azure.vision.endpoint}")
    private String endpoint;

    @Value("${azure.vision.key}")
    private String subscriptionKey;

    public Map<String, Object> analyzeImage(String url) throws IOException {
        ImageAnalysisClient client = new ImageAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(subscriptionKey))
                .buildClient();

        ImageAnalysisOptions options = new ImageAnalysisOptions();

        // Here we use the URL directly
        ImageAnalysisResult result = client.analyzeFromUrl(
                url,  // passing the URL directly
                Arrays.asList(VisualFeatures.READ),
                options
        );

        return Map.of("fullResult", result);
    }
}
