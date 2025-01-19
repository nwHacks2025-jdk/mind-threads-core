package com.mind_threads.core.user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetStatsByEmailResponse {
    private LocalDate date;
    private int count;
}
