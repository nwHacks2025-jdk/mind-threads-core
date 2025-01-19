package com.mind_threads.core.message_service.domain;

import com.mind_threads.core.user_management_service.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="gptResponse", nullable = false)
    private String gptResponse;

    @Column(name = "message_at", nullable = false)
    private Date messageAt;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name="tag1" , nullable = false)
    private String tag1;

    @Column(name="tag2" , nullable = false)
    private String tag2;

    @Column(name="tag3" , nullable = false)
    private String tag3;

    @Column(name="tag4" , nullable = false)
    private String tag4;

    @Column(name="tag5" , nullable = false)
    private String tag5;


    @Builder
    public Message(String title, String email, String gptResponse, Date messageAt, String summary, String tag1, String tag2, String tag3, String tag4, String tag5) {
        this.title = title;
        this.email = email;
        this.gptResponse = gptResponse;
        this.messageAt = messageAt;
        this.summary = summary;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
    }

}
