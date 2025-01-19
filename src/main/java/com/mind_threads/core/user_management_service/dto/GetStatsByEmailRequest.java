package com.mind_threads.core.user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetStatsByEmailRequest {
    private String email;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
