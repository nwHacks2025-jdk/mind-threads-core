package com.mind_threads.core.extension_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageRequest {
    private String email;
    private String gptrespond;
    private String messageAt;
}
