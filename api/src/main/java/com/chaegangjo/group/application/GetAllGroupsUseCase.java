package com.chaegangjo.group.application;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.dto.GroupListInfo;
import com.chaegangjo.group.service.GroupService;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.NextCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllGroupsUseCase {

    private static final int GROUP_PAGE_SIZE = 20;

    private final GroupService groupService;

    public CursorPageResponse<List<GroupListInfo>> execute(Long cursorId) {
        Slice<Group> groups = groupService.findAllByCursor(new CursorPage(GROUP_PAGE_SIZE, cursorId));

        List<Group> content = groups.getContent();

        NextCursor nextCursor = null;
        if (groups.hasNext()) {
            nextCursor = new NextCursor(content.getLast().getId());
        }

        List<GroupListInfo> data = content.stream()
                .map(GroupListInfo::from)
                .toList();

        return CursorPageResponse.<List<GroupListInfo>>builder()
                .data(data)
                .hasNext(groups.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
