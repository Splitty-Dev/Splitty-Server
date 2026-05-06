package com.chaegangjo.group.domain;

import com.chaegangjo.common.entity.BaseCreatedEntity;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupChatMember extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_chat_room_id")
    private GroupChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String username;

    public GroupChatMember(GroupChatRoom chatRoom, Member member) {
        this.chatRoom = chatRoom;
        this.member = member;
        this.username = member.getUsername();
    }
}
