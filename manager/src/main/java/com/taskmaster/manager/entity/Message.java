package com.taskmaster.manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    @NotNull(message = "Sender user can't be null")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    @NotNull(message = "Receiver user can't be null")
    private User receiverUser;

    @NotBlank(message = "Content is required")
    @Column(name = "content")
    private String content;

    @NotNull(message = "Timestamp can't be null")
    @Column(name = "timestamp")
    private Timestamp timestamp;

}
