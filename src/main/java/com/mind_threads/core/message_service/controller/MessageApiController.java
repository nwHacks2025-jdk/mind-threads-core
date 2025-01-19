package com.mind_threads.core.message_service.controller;

import com.mind_threads.core.message_service.domain.Message;
import com.mind_threads.core.message_service.dto.GetMessageByTagNameRequest;
import com.mind_threads.core.message_service.dto.GetMessageByTopicRequest;
import com.mind_threads.core.message_service.dto.GetMessageByTopicResponse;
import com.mind_threads.core.message_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/thread")
public class MessageApiController {
    private final MessageService messageService;

    @PostMapping("/tag")
    public ResponseEntity<List<Message>> registerMessage(@RequestBody GetMessageByTagNameRequest getMessageByTagNameRequest) {
        if(getMessageByTagNameRequest.getTagName() == null) {
            return ResponseEntity.ok(messageService.getMessageByEmail(getMessageByTagNameRequest));
        }
        List<Message> messageList = messageService.getMessageByTagName(getMessageByTagNameRequest);
        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/topic")
    public ResponseEntity<?> registerMessage(@RequestBody GetMessageByTopicRequest getMessageByTopicRequest) {
        GetMessageByTopicResponse response = messageService.getMessageByTopic(getMessageByTopicRequest);
        return ResponseEntity.ok(response);
    }
}
