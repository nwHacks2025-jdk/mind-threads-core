package com.mind_threads.core.message_service.repository;

import com.mind_threads.core.message_service.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
        @Query("SELECT m FROM Message m WHERE (m.tag1 = :tagName OR m.tag2 = :tagName OR m.tag3 = :tagName OR m.tag4 = :tagName OR m.tag5 = :tagName) AND m.email = :email")
        List<Message> findByTagNameAndEmail(@Param("tagName") String tagName, @Param("email") String email);

        @Query("SELECT m.tag1, m.tag2, m.tag3, m.tag4, m.tag5 " +
                "FROM Message m " +
                "WHERE m.email = :email")
        List<Object[]> findTagsByEmail(@Param("email") String email);

        @Query("SELECT m.messageAt, COUNT(m) " +
                "FROM Message m " +
                "WHERE m.email = :email " +
                "AND m.messageAt BETWEEN :startAt AND :endAt " +
                "GROUP BY m.messageAt " +
                "ORDER BY m.messageAt ASC")
        List<Object[]> findStatsByEmail(@Param("email") String email,
                                        @Param("startAt") LocalDateTime startAt,
                                        @Param("endAt") LocalDateTime endAt);

        @Query("SELECT DATE(m.messageAt), COUNT(m) " +
                "FROM Message m " +
                "WHERE m.messageAt BETWEEN :startAt AND :endAt " +
                "GROUP BY DATE(m.messageAt), m.email " +
                "ORDER BY DATE(m.messageAt) ASC")
        List<Object[]> findAverageUsageByUser(@Param("startAt") LocalDateTime startAt,
                                              @Param("endAt") LocalDateTime endAt);









}
