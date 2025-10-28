package org.example.scaneia_dsii.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.scaneia_dsii.openapi.ImageAnalysisOpenAPI;
import org.example.scaneia_dsii.service.ImageAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class ImageAnalysisController implements ImageAnalysisOpenAPI {

    @Autowired
    private ImageAnalysisService imageAnalysisService;

    @Override
    public ResponseEntity<Map<String, Object>> analyzeImage(
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        System.out.println("Received file: " + file.getOriginalFilename() + ", size: " + file.getSize());
        Map<String, Object> result = imageAnalysisService.analyzeImage(file);
            return ResponseEntity.ok(result);
    }
}