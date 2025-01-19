package com.mind_threads.core.message_service.service;

import com.mind_threads.core.message_service.domain.Message;
import com.mind_threads.core.message_service.dto.GetMessageByTagNameRequest;
import com.mind_threads.core.message_service.dto.GetMessageByTopicRequest;
import com.mind_threads.core.message_service.dto.GetMessageByTopicResponse;
import com.mind_threads.core.message_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getMessageByTagName(GetMessageByTagNameRequest getMessageByTagNameRequest) {
        return messageRepository.findByTagNameAndEmail(getMessageByTagNameRequest.getTagName(), getMessageByTagNameRequest.getEmail());
    }

    public List<Message> getMessageByEmail(GetMessageByTagNameRequest getMessageByTagNameRequest) {
        return messageRepository.findByEmail(getMessageByTagNameRequest.getEmail());
    }

    public GetMessageByTopicResponse getMessageByTopic(GetMessageByTopicRequest getMessageByTopicRequest) {

        List<Object[]> tagsList = messageRepository.findTagsByEmail(getMessageByTopicRequest.getEmail());

        List<Map<String, Object>> proccessedTags = processTags(tagsList);

        List<GetMessageByTopicResponse.Node> nodes = new ArrayList<>();
        List<GetMessageByTopicResponse.Link> links = new ArrayList<>();

        for (Map<String, Object> tagMap : proccessedTags) {
            // Extract data for Node
            String category = (String) tagMap.get("tag1");
            int count = (int) tagMap.getOrDefault("tag1Count", 0);

            // Collect subcategories and handle comma separation
            List<String> subcategory = new ArrayList<>();
            if (tagMap.containsKey("tag2")) {
                String tag2 = (String) tagMap.get("tag2");
                subcategory.addAll(Arrays.asList(tag2.split(","))); // Split comma-separated values
            }
            if (tagMap.containsKey("tag3")) {
                String tag3 = (String) tagMap.get("tag3");
                subcategory.addAll(Arrays.asList(tag3.split(","))); // Split comma-separated values
            }
            if (tagMap.containsKey("tag4")) {
                String tag4 = (String) tagMap.get("tag4");
                subcategory.addAll(Arrays.asList(tag4.split(","))); // Split comma-separated values
            }
            if (tagMap.containsKey("tag5")) {
                String tag5 = (String) tagMap.get("tag5");
                subcategory.addAll(Arrays.asList(tag5.split(","))); // Split comma-separated values
            }

            // Create and add the Node
            nodes.add(new GetMessageByTopicResponse.Node(category, subcategory, count));

            // Create Links for each subcategory
            for (String target : subcategory) {
                links.add(new GetMessageByTopicResponse.Link(category, target));
            }
        }

// Build the response
        GetMessageByTopicResponse response = new GetMessageByTopicResponse(nodes, links);

// Log or return the response
        log.info("Response created: {}", response);
        return response;


    }

    private List<Map<String, Object>> processTags(List<Object[]> rawResults) {
        Map<String, Map<String, Object>> groupedData = new HashMap<>();

        for (Object[] row : rawResults) {
            String tag1 = (String) row[0];
            String tag2 = (String) row[1];
            String tag3 = (String) row[2];
            String tag4 = (String) row[3];
            String tag5 = (String) row[4];

            groupedData.computeIfAbsent(tag1, k -> {
                Map<String, Object> entry = new HashMap<>();
                entry.put("tag1", k);
                entry.put("tag1Count", 0);
                entry.put("tag2", new HashSet<>());
                entry.put("tag3", new HashSet<>());
                entry.put("tag4", new HashSet<>());
                entry.put("tag5", new HashSet<>());
                return entry;
            });

            Map<String, Object> entry = groupedData.get(tag1);
            entry.put("tag1Count", (int) entry.get("tag1Count") + 1);
            ((Set<String>) entry.get("tag2")).add(tag2);
            ((Set<String>) entry.get("tag3")).add(tag3);
            ((Set<String>) entry.get("tag4")).add(tag4);
            ((Set<String>) entry.get("tag5")).add(tag5);
        }

        return groupedData.values().stream()
                .map(entry -> {
                    entry.put("tag2", String.join(",", (Set<String>) entry.get("tag2")));
                    entry.put("tag3", String.join(",", (Set<String>) entry.get("tag3")));
                    entry.put("tag4", String.join(",", (Set<String>) entry.get("tag4")));
                    entry.put("tag5", String.join(",", (Set<String>) entry.get("tag5")));
                    return entry;
                })
                .collect(Collectors.toList());
    }

}
