package com.mind_threads.core.extension_service.repository;

import com.mind_threads.core.user_management_service.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionMemberRepository extends JpaRepository<Member, Long> {

}
