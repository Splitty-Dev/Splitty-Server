package com.chaegangjo.paging;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IdCreatedAtCursorPage extends CursorPage {

    private final LocalDateTime cursorCreatedAt;

    public IdCreatedAtCursorPage(int size, Long cursorId, LocalDateTime cursorCreatedAt) {
        super(size, cursorId);
        this.cursorCreatedAt = cursorCreatedAt;
    }
}
