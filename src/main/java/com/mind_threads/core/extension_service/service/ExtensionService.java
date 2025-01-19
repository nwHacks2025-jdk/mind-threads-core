package com.mind_threads.core.extension_service.service;

import com.mind_threads.core.extension_service.dto.MessageRequest;
import com.mind_threads.core.extension_service.repository.ExtensionMemberRepository;
import com.mind_threads.core.extension_service.repository.ExtensionMessageRepository;
import com.mind_threads.core.message_service.domain.Message;
import com.mind_threads.core.user_management_service.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExtensionService {

    private final ExtensionMessageRepository extensionMessageRepository;
    private final ExtensionMemberRepository extensionMemberRepository;

    public void registerMember(Member member) {
        extensionMemberRepository.save(member);
    }

    public void registerThread(MessageRequest messageRequest) {
        /**
         * @TODO
         * 1. ThreadRequest를 Threads로 변환
         */
        Message message = Message.builder()
                .email(messageRequest.getEmail())
                .gptResponse(messageRequest.getGptresponse())
                .messageAt(messageRequest.getMessageAt())
                .summary("SUMMARY")
                .tag1("TAG" + (int)(Math.random() * 100)) // Random number after TAG1
                .tag2("TAG" + (int)(Math.random() * 100)) // Random number after TAG2
                .tag3("TAG" + (int)(Math.random() * 100)) // Random number after TAG3
                .tag4("TAG" + (int)(Math.random() * 100)) // Random number after TAG4
                .tag5("TAG" + (int)(Math.random() * 100)) // Random number after TAG5
                .title("TITLE" + (int)(Math.random() * 100)) // Random number after TITLE
                .build();

        extensionMessageRepository.save(message);
    }

}
