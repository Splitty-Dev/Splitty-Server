package com.chaegangjo.group.service;

import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.repository.GroupRepository;
import com.chaegangjo.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.GROUP_NOT_FOUND;
import static com.chaegangjo.exception.errorcode.GroupErrorCode.INVALID_JOIN_CODE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GROUP_NOT_FOUND));
    }

    public Group findGroupByJoinCode(String joinCode) {
        return groupRepository.findByJoinCode(joinCode)
                .orElseThrow(() -> new GroupException(INVALID_JOIN_CODE));
    }

    public Slice<Group> findAllByCursor(CursorPage page) {
        return groupRepository.findAllByCursor(page);
    }
}
