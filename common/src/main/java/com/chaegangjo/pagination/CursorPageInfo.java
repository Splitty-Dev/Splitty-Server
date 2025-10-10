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

    public CursorPageInfo(T data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }
}