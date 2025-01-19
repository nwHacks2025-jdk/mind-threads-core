package com.mind_threads.core.user_management_service.controller;

import com.mind_threads.core.user_management_service.domain.Member;
import com.mind_threads.core.user_management_service.dto.GetAvgStatsOfUserRequest;
import com.mind_threads.core.user_management_service.dto.GetAvgStatsOfUsersResponse;
import com.mind_threads.core.user_management_service.dto.GetStatsByEmailRequest;
import com.mind_threads.core.user_management_service.dto.GetStatsByEmailResponse;
import com.mind_threads.core.user_management_service.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<Member> signIn(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.signIn(member));
    }

    @PostMapping("/stats")
    public ResponseEntity<List<GetStatsByEmailResponse>> getStatsByEmail(@RequestBody GetStatsByEmailRequest request) {
        return ResponseEntity.ok(memberService.getStatsByEmail(request));
    }

    @PostMapping("/all/stats")
    public ResponseEntity<List<GetAvgStatsOfUsersResponse>> getStats(@RequestBody GetAvgStatsOfUserRequest request) {
        return ResponseEntity.ok(memberService.getAverageUsageByUser(request));
    }


}
