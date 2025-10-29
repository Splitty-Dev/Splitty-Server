package com.chaegangjo.presentation;

import com.chaegangjo.application.SaveChatMessageUsecase;
import com.chaegangjo.dto.StompChatMessageRequest;
import com.chaegangjo.dto.StompChatMessageResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final SaveChatMessageUsecase saveChatMessageUsecase;

    @MessageMapping("/trade.{tradeId}/chat") //client->server 주소: /pub/trade.{tradeId}/chat
    @SendTo("/sub/trade.{tradeId}/chat") //server->client 주소: /sub/trade.{tradeId}/chat
    public StompChatMessageResponse send(StompChatMessageRequest request,
                                         Principal principal,
                                         @DestinationVariable("tradeId") Long tradeId) {

        Long senderId = Long.parseLong(principal.getName());
        return saveChatMessageUsecase.execute(senderId, tradeId, request.message());
    }
}
