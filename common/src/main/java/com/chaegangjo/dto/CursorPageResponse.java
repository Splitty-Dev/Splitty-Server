package com.chaegangjo.dto;

import com.chaegangjo.paging.NextCursor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CursorPageResponse<T> {

    T data;
    boolean hasNext = false;
    NextCursor nextCursor;

    @Builder
    public CursorPageResponse(T data, boolean hasNext, NextCursor nextCursor) {
        this.data = data;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }
}