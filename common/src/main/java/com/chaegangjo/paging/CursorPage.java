package com.chaegangjo.paging;

import lombok.Getter;

@Getter
public class CursorPage {

    private int size;
    private final Long cursorId;

    public CursorPage(int size, Long cursorId) {
        this.size = size;
        this.cursorId = cursorId;
    }
}
