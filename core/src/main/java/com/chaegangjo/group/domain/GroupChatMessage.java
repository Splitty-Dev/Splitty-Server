package com.chaegangjo.group.domain;

import com.chaegangjo.common.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupChatMessage extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_chat_member_id")
    private GroupChatMember groupChatMember;

    @Column(length = 2000)
    private String message;

    public GroupChatMessage(GroupChatMember groupChatMember, String message) {
        this.groupChatMember = groupChatMember;
        this.message = message;
    }
}
