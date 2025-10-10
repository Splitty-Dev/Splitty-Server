package com.chaegangjo.pagination;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CursorPageInfo<T> {

    T data;
    boolean hasNext = false;
    Object NextCursor = null;

    @Builder
    public CursorPageInfo(T data, boolean hasNext, Object nextCursor) {
        this.data = data;
        this.hasNext = hasNext;
        NextCursor = nextCursor;
    }
}