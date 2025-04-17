package org.example.springchat.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userQuestion;

    @Column(columnDefinition = "TEXT")
    private String gptResponse;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
