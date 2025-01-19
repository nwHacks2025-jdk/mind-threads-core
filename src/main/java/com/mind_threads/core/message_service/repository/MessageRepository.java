package com.mind_threads.core.message_service.repository;

import com.mind_threads.core.message_service.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
