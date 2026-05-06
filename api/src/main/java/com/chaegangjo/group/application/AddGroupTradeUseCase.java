package com.chaegangjo.group.application;

import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupTrade;
import com.chaegangjo.group.dto.GroupTradeInfo;
import com.chaegangjo.group.dto.request.AddGroupTradeRequest;
import com.chaegangjo.group.service.GroupMemberService;
import com.chaegangjo.group.service.GroupService;
import com.chaegangjo.group.service.GroupTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.NOT_GROUP_MEMBER;

@RequiredArgsConstructor
@Component
public class AddGroupTradeUseCase {

    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final GroupTradeService groupTradeService;

    @Transactional
    public GroupTradeInfo execute(Long groupId, AddGroupTradeRequest request, Long memberId) {
        if (!groupMemberService.isMember(groupId, memberId)) {
            throw new GroupException(NOT_GROUP_MEMBER);
        }

        Group group = groupService.findGroupById(groupId);
        int memberCount = groupMemberService.countMembersByGroupId(groupId);

        List<GroupTradeService.ItemData> items = request.items().stream()
                .map(item -> new GroupTradeService.ItemData(item.itemName(), item.price()))
                .toList();

        GroupTrade groupTrade = groupTradeService.saveTrade(group, request.name(), items, memberCount);

        return GroupTradeInfo.from(groupTrade);
    }
}
