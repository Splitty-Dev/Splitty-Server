package com.chaegangjo.trade.dto;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record GetTradeQuantitiesResponse(
        @Schema(example = "10")
        int totalQuantity,
        List<GetTradeQuantityInfo> quantities
) {

    public static GetTradeQuantitiesResponse of(Goods goods, List<ChatMember> chatMembers) {
        List<GetTradeQuantityInfo> quantities = chatMembers.stream()
                .map(GetTradeQuantityInfo::from)
                .toList();
        return new GetTradeQuantitiesResponse(goods.getTotalQuantity(), quantities);
    }

    public record GetTradeQuantityInfo(@Schema(example = "1")
                                       Long memberId,
                                       @Schema(example = "귀여운고양이123")
                                       String username,
                                       @Schema(example = "profile-image-url")
                                       String profileImageUrl,
                                       @Schema(example = "5")
                                       int quantity) {

        public static GetTradeQuantityInfo from(ChatMember chatMember) {
            Member member = chatMember.getMember();

            return new GetTradeQuantityInfo(
                    member.getId(),
                    member.getUsername(),
                    member.getProfileImageUrl(),
                    chatMember.getQuantity()
            );
        }
    }
}
