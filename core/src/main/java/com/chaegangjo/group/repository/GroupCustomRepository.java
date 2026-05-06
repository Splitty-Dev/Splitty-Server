package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.paging.CursorPage;
import org.springframework.data.domain.Slice;

public interface GroupCustomRepository {

    Slice<Group> findAllByCursor(CursorPage page);
}
