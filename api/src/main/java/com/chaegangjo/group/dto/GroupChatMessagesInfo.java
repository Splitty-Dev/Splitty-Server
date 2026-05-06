package com.chaegangjo.group.dto;

import java.util.List;

public record GroupChatMessagesInfo(
        List<GroupChatMemberInfo> members,
        List<GroupChatMessageInfo> messages
) {
}
