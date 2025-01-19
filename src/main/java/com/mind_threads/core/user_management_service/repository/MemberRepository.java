package com.mind_threads.core.user_management_service.repository;

import com.mind_threads.core.user_management_service.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
