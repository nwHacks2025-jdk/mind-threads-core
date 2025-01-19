package com.mind_threads.core.extension_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mind_threads.core.extension_service.dto.MessageRequest;
import com.mind_threads.core.extension_service.repository.ExtensionMemberRepository;
import com.mind_threads.core.extension_service.repository.ExtensionMessageRepository;
import com.mind_threads.core.extension_service.util.GenerateSummaryAndTags;
import com.mind_threads.core.message_service.domain.Message;
import com.mind_threads.core.user_management_service.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExtensionService {

    private final ExtensionMessageRepository extensionMessageRepository;
    private final ExtensionMemberRepository extensionMemberRepository;
    private final GenerateSummaryAndTags generateSummaryAndTags;

    public void registerMember(Member member) {
        extensionMemberRepository.save(member);
    }

    public void registerThread(MessageRequest messageRequest) throws Exception {
        /**
         * @TODO
         * 1. ThreadRequest를 Threads로 변환
         */


        String title = generateSummaryAndTags.callOpenApi(messageRequest.getGptresponse()).get("title").toString();
        String summary = generateSummaryAndTags.callOpenApi(messageRequest.getGptresponse()).get("summarize").toString();
        ArrayList<String> tags = (ArrayList<String>) generateSummaryAndTags.callOpenApi(messageRequest.getGptresponse()).get("tags");


        Message message = Message.builder()
                .email(messageRequest.getEmail())
                .gptResponse(messageRequest.getGptresponse())
                .messageAt(messageRequest.getMessageAt())
                .summary(summary)
                .tag1(tags.get(0).toUpperCase())
                .tag2(tags.get(1).toUpperCase())
                .tag3(tags.get(2).toUpperCase())
                .tag4(tags.get(3).toUpperCase())
                .tag5(tags.get(4).toUpperCase())
                .title(title)
                .build();

        extensionMessageRepository.save(message);
    }

}
