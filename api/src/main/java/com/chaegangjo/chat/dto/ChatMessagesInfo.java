package com.chaegangjo.chat.dto;


import java.util.List;

public record ChatMessagesInfo(
        List<ChatMemberInfo> users,
        List<com.chaegangjo.chat.dto.ChatMessageInfo> messages
) {
}
