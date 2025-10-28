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

    public Map<String, Object> analyzeImage(MultipartFile file) throws IOException {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            System.out.println("Endpoint: " + endpoint);
            System.out.println("Key: " + subscriptionKey.substring(0, 4) + "...");
            
            ImageAnalysisClient client = new ImageAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(subscriptionKey))
                .buildClient();

        ImageAnalysisOptions options = new ImageAnalysisOptions();
            options.setLanguage("pt");

            ImageAnalysisResult result = client.analyze(
                    BinaryData.fromStream(file.getInputStream()),
                    Arrays.asList(VisualFeatures.READ),
                    options
            );
            response.put("fullResult", result);
        } catch (IllegalArgumentException e) {
            System.err.println("Azure SDK rejected the image: " + e.getMessage());
            throw e;
        }

        return response;
    }
}
