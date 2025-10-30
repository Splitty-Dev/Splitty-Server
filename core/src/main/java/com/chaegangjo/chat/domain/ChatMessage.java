package com.chaegangjo.chat.domain;

import com.chaegangjo.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_member_id")
    private ChatMember chatMember;

    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.TEXT;

    public ChatMessage(ChatMember chatMember, String message) {
        this.message = message;
        this.chatMember = chatMember;
    }

    public ChatMessage(ChatMember chatMember, MessageType type) {
        this.message = null;
        this.chatMember = chatMember;
        this.type = type;
    }
}
