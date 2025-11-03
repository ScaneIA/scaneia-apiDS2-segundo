package org.example.scaneia_dsii.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/vision")
@Tag(name = "Azure Vision", description = "Endpoints for analyzing images with Azure Cognitive Services")
public interface ImageAnalysisOpenAPI {

    @Operation(
            summary = "Analyze an uploaded image",
            description = "Receives an image file and extracts text using Azure Cognitive Services.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully analyzed the image",
                            content = @Content(schema = @Schema(example = "{ \"extractedText\": [ { \"lineText\": \"Hello World\" } ] }"))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(example = "{ \"error\": \"Failed to analyze image: <details>\" }"))
                    )
            }
    )
    @PostMapping(value = "/analyze", consumes = "application/json")
    ResponseEntity<Map<String, Object>> analyzeImage(
            @RequestBody Map<String, String> request
    ) throws IOException;
}
