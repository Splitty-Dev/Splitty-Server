package com.chaegangjo.presentation;

import com.chaegangjo.application.SaveChatMessageUsecase;
import com.chaegangjo.dto.StompChatMessageRequest;
import com.chaegangjo.dto.StompChatMessageResponse;
import com.chaegangjo.security.jwt.utils.JwtTokenAuthenticator;
import com.chaegangjo.security.jwt.utils.JwtTokenProvider;
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
    private final SavePurchaseRequestChatMessageUsecase savePurchaseRequestChatMessageUsecase;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenAuthenticator jwtTokenAuthenticator;

    @MessageMapping("/goods.{goodsId}/chat") //client->server 주소: /pub/goods.{goodsId}/chat
    @SendTo("/sub/goods.{goodsId}/chat") //server->client 주소: /sub/goods.{goodsId}/chat
    public StompChatMessageResponse send(StompChatMessageRequest request,
                                         Principal principal,
                                         @DestinationVariable("goodsId") Long goodsId) {
        Long senderId = Long.parseLong(principal.getName());
        return saveChatMessageUsecase.execute(senderId, goodsId, request.message());
    }

    @MessageMapping("/purchase-request.{chatRoomId}/chat") //client->server 주소: /pub/purchase-request.{chatRoomId}/chat
    @SendTo("/sub/purchase-request.{chatRoomId}/chat") //server->client 주소: /sub/purchase-request.{chatRoomId}/chat
    public StompPurchaseRequestChatMessageResponse sendPurchaseRequestChat(StompChatMessageRequest request,
                                                                            Principal principal,
                                                                            @DestinationVariable("chatRoomId") Long chatRoomId) {
        Long senderId = Long.parseLong(principal.getName());
        return savePurchaseRequestChatMessageUsecase.execute(senderId, chatRoomId, request.message());
    }
}
