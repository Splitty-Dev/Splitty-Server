package com.chaegangjo.wishlist.dto;


import com.chaegangjo.dto.CursorPage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WishListCursorPage extends CursorPage {

    private final LocalDateTime cursorCreatedAt;
    private final Long memberId;

    public WishListCursorPage(Long cursorId, LocalDateTime cursorCreatedAt, Long memberId) {
        super(cursorId);
        this.cursorCreatedAt = cursorCreatedAt;
        this.memberId = memberId;
    }
}
