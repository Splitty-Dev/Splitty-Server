package com.chaegangjo.chat.presentation;

import com.chaegangjo.chat.application.SaveChatMessageUsecase;
import com.chaegangjo.chat.dto.ChatMessageRequest;
import com.chaegangjo.chat.dto.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final SaveChatMessageUsecase saveChatMessageUsecase;

    @MessageMapping("/trade.{tradeId}/chat") //client->server 주소: /pub/trade.{tradeId}/chat
    @SendTo("/sub/trade.{tradeId}/chat") //server->client 주소: /sub/trade.{tradeId}/chat
    public ChatMessageResponse send(ChatMessageRequest request,
                                    Principal principal,
                                    @DestinationVariable Long tradeId) {

        Long memberId = Long.parseLong(principal.getName());
        return saveChatMessageUsecase.execute(memberId, tradeId, request.message());
    }
}
