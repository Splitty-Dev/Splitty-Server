package com.chaegangjo.dto;

import com.chaegangjo.utils.PageProperties;
import lombok.Getter;

@Getter
public class CursorPage {

    private int size = PageProperties.DEFAULT_PAGE_SIZE;
    private final Long cursorId;

    public CursorPage(int size, Long cursorId) {
        this.size = size;
        this.cursorId = cursorId;
    }

    public CursorPage(Long cursorId) {
        this.cursorId = cursorId;
    }
}
