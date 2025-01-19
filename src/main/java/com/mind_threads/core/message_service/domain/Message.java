package com.mind_threads.core.message_service.domain;

import com.mind_threads.core.user_management_service.domain.Member;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Member member;

    @Column(name="gptanswer", nullable = false)
    private String gptResponse;

    @Column(name = "message_at", nullable = false)
    private String messageAt;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Type(ListArrayType.class)
    @Column(name = "tags", nullable=false, columnDefinition = "text[]")
    private List<String> tags;
}
