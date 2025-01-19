package com.mind_threads.core.message_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetMessageByTopicResponse {
    private List<Node> nodes;
    private List<Link> links;

    // Inner class for Node
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Node {
        private String category;
        private List<String> subcategory;
        private int count;
    }

    // Inner class for Link
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Link {
        private String source;
        private String target;
    }
}
