package com.chaegangjo.trade.domain;

import com.chaegangjo.entity.BaseCreatedEntity;
import com.chaegangjo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_member_id")
    private TradeMember tradeMember;

    @Enumerated(EnumType.STRING)
    private ChatType chatType = ChatType.MESSAGE;

    public ChatMessage(TradeMember tradeMember, String message) {
        this.message = message;
        this.tradeMember = tradeMember;
    }

    public ChatMessage(TradeMember tradeMember, String message, ChatType chatType) {
        this.message = message;
        this.tradeMember = tradeMember;
        this.chatType = chatType;
    }
}
