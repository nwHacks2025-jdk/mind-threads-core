package com.mind_threads.core.extension_service.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateSummaryAndTags {

    private static final String API_URL = "https://api.meaningcloud.com/topics-2.0";
    private static final String API_KEY = "";

    public String generateSummaryAndTags(String message) {
        try {
            // Prepare the request body
            String requestBody = prepareRequestBody(Map.of(
                    "key", API_KEY,
                    "txt", message,
                    "lang", "en", // Replace with the appropriate language
                    "tt", "a"    // Extract all topic types
            ));

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();

            // Send the request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log and return the response
            log.info("Response status: {}", response.statusCode());
            log.info("Response body: {}", response.body());

            return response.body();
        } catch (IOException | InterruptedException e) {
            log.error("Error while calling MeaningCloud API", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private String prepareRequestBody(Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue().replace(" ", "%20"));
        }
        return builder.toString();
    }
}
