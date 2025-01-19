package com.mind_threads.core.extension_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageRequest {
    private String email;
    private String gptresponse;
    private Date messageAt;

}
