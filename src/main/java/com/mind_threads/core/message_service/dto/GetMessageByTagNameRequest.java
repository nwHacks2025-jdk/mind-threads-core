package com.mind_threads.core.message_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetMessageByTagNameRequest {
    private String tagName;
    private String email;
}
