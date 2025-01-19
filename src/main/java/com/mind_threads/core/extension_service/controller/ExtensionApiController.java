package com.mind_threads.core.extension_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mind_threads.core.extension_service.dto.MessageRequest;
import com.mind_threads.core.extension_service.service.ExtensionService;
import com.mind_threads.core.user_management_service.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/extension")
public class ExtensionApiController {
    private final ExtensionService extensionService;

    @PostMapping("/member")
    public ResponseEntity<String> registerMember(@RequestBody Member member) {
        extensionService.registerMember(member);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/thread")
    public ResponseEntity<String> registerThread(@RequestBody MessageRequest messageRequest) throws Exception {
        extensionService.registerThread(messageRequest);
        return ResponseEntity.ok("Success");
    }
}
