package com.mind_threads.core.user_management_service.service;

import com.mind_threads.core.message_service.repository.MessageRepository;
import com.mind_threads.core.user_management_service.domain.Member;
import com.mind_threads.core.user_management_service.dto.GetAvgStatsOfUserRequest;
import com.mind_threads.core.user_management_service.dto.GetAvgStatsOfUsersResponse;
import com.mind_threads.core.user_management_service.dto.GetStatsByEmailRequest;
import com.mind_threads.core.user_management_service.dto.GetStatsByEmailResponse;
import com.mind_threads.core.user_management_service.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    public Member signIn(Member member) {
        return memberRepository.findByEmail(member.getEmail());
    }

    public List<GetStatsByEmailResponse> getStatsByEmail(GetStatsByEmailRequest request) {
        List<Object[]> statsList = messageRepository.findStatsByEmail(request.getEmail(), request.getStartAt(), request.getEndAt());

        return statsList.stream()
                .map(stat -> {
                    LocalDate messageAt = ((Timestamp) stat[0]).toLocalDateTime().toLocalDate(); // Convert to LocalDate
                    return new GetStatsByEmailResponse(messageAt, ((Long) stat[1]).intValue());
                })
                .collect(Collectors.toList());
    }

    public List<GetAvgStatsOfUsersResponse> getAverageUsageByUser(GetAvgStatsOfUserRequest request) {
        List<Object[]> groupedResults = messageRepository.findAverageUsageByUser(request.getStartAt(), request.getEndAt());

        groupedResults.forEach(row -> {
            log.info("Row: {}", row);
        });

        Map<LocalDate, List<Long>> dailyCounts = groupedResults.stream()
                .collect(Collectors.groupingBy(
                        row -> ((java.sql.Date) row[0]).toLocalDate(),
                        Collectors.mapping(row -> {
                            Object countObj = row[1];
                            if (countObj instanceof String) {
                                return Long.valueOf((String) countObj);
                            }
                            return (Long) countObj;
                        }, Collectors.toList())
                ));

        return dailyCounts.entrySet().stream()
                .map(entry -> new GetAvgStatsOfUsersResponse(
                        entry.getKey(),
                        entry.getValue().stream().mapToDouble(Long::doubleValue).average().orElse(0.0) // Calculate average
                ))
                .sorted(Comparator.comparing(GetAvgStatsOfUsersResponse::getDate))
                .collect(Collectors.toList());
    }





}
