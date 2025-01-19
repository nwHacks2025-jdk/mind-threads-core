package com.mind_threads.core.extension_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateSummaryAndTags {

    private final GptConfig gptConfig;

    public Map<String, Object> callOpenApi(String prompt) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(gptConfig.getSecretKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", gptConfig.getModel());
        requestBody.put("messages", new Object[]{
                new HashMap<String, String>() {{
                    put("role", "developer");
                    put("content", "I want you to do three things with these content:\n" +
                            "1) Summarize the answer in one concise sentence.\n" +
                            "2) Select five one-word tags: one as the primary tag (most relevant) and four secondary tags (less relevant but still reflective of the answer). Avoid overly general or ambiguous words and steer clear of two-word combinations. Ensure the tags are specific and meaningful.\n" +
                            "3) Give me One sentence title");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });

        Map<String, Object> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_schema");
        Map<String, Object> jsonSchema = new HashMap<>();
        jsonSchema.put("name", "summary_tags_schema");
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("title", new HashMap<String, String>() {{
            put("type", "string");
        }});
        properties.put("summarize", new HashMap<String, String>() {{
            put("type", "string");
        }});
        properties.put("tags", new HashMap<String, Object>() {{
            put("type", "array");
            put("items", new HashMap<String, String>() {{
                put("type", "string");
            }});
        }});
        schema.put("properties", properties);
        schema.put("additionalProperties", false);
        jsonSchema.put("schema", schema);
        responseFormat.put("json_schema", jsonSchema);

        requestBody.put("response_format", responseFormat);
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 100);

        // Send the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = GptConfig.restTemplate().exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                Object.class
        );

        // Assuming the response body is a Map
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        // Extract 'choices' and 'message' from the response body
        Map<String, Object> choice = ((List<Map<String, Object>>) responseBody.get("choices")).get(0);
        Map<String, Object> message = (Map<String, Object>) choice.get("message");

        // The 'content' is a JSON string that contains the summary, title, and tags
        String contentJson = (String) message.get("content");

        // Parse the 'content' JSON string to extract the required information
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> contentMap = objectMapper.readValue(contentJson, Map.class);

        // Extract fields from the parsed content
        String summarize = (String) contentMap.get("summarize");
        String title = (String) contentMap.get("title");
        List<String> tags = (List<String>) contentMap.get("tags");

        // Log the extracted values for debugging
        log.info("Title: {}", title);
        log.info("Summarize: {}", summarize);
        log.info("Tags: {}", tags);

        // Return the extracted content in the desired format
        Map<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("summarize", summarize);
        result.put("tags", tags);

        return result;
    }
}
