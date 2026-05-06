package com.chaegangjo.group.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.group.application.AddGroupTradeUseCase;
import com.chaegangjo.group.application.CreateGroupUseCase;
import com.chaegangjo.group.application.GetAllGroupsUseCase;
import com.chaegangjo.group.application.GetGroupListUseCase;
import com.chaegangjo.group.application.GetGroupTradeListUseCase;
import com.chaegangjo.group.application.JoinGroupUseCase;
import com.chaegangjo.group.dto.GroupInfo;
import com.chaegangjo.group.dto.GroupListInfo;
import com.chaegangjo.group.dto.GroupTradeInfo;
import com.chaegangjo.group.dto.request.AddGroupTradeRequest;
import com.chaegangjo.group.dto.request.CreateGroupRequest;
import com.chaegangjo.group.dto.request.JoinGroupRequest;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "소그룹", description = "소그룹 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final JoinGroupUseCase joinGroupUseCase;
    private final GetAllGroupsUseCase getAllGroupsUseCase;
    private final GetGroupListUseCase getGroupListUseCase;
    private final GetGroupTradeListUseCase getGroupTradeListUseCase;
    private final AddGroupTradeUseCase addGroupTradeUseCase;

    @Operation(summary = "그룹 생성", description = "소그룹을 생성하고 참여 코드를 발급합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<GroupInfo>> createGroup(
            @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(createGroupUseCase.execute(request, user.getId())));
    }

    @Operation(summary = "그룹 참여", description = "참여 코드를 입력해 소그룹에 입장합니다.")
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<GroupInfo>> joinGroup(
            @RequestBody JoinGroupRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(joinGroupUseCase.execute(request, user.getId())));
    }

    @Operation(summary = "전체 그룹 탐색", description = "현재 존재하는 모든 소그룹을 커서 기반으로 조회합니다.")
    @GetMapping("/explore")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GroupListInfo>>>> getAllGroups(
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId
    ) {
        return ResponseEntity.ok(ApiResponse.success(getAllGroupsUseCase.execute(cursorId)));
    }

    @Operation(summary = "내 그룹 목록 조회", description = "참여 중인 소그룹 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupInfo>>> getGroupList(
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(getGroupListUseCase.execute(user.getId())));
    }

    @Operation(summary = "그룹 거래 목록 조회", description = "그룹에서 등록된 거래 목록을 조회합니다. (등록 날짜, 거래명, 인당 가격)")
    @GetMapping("/{groupId}/trades")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GroupTradeInfo>>>> getGroupTradeList(
            @Parameter(example = "1")
            @PathVariable Long groupId,
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(getGroupTradeListUseCase.execute(groupId, user.getId(), cursorId)));
    }

    @Operation(summary = "거래 추가", description = "그룹에 새 거래를 등록합니다. 물품 목록과 가격을 입력하면 인당 가격이 자동 계산됩니다.")
    @PostMapping("/{groupId}/trades")
    public ResponseEntity<ApiResponse<GroupTradeInfo>> addGroupTrade(
            @Parameter(example = "1")
            @PathVariable Long groupId,
            @RequestBody AddGroupTradeRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(addGroupTradeUseCase.execute(groupId, request, user.getId())));
    }
}
