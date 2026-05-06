package com.chaegangjo.group.service;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupTrade;
import com.chaegangjo.group.domain.GroupTradeItem;
import com.chaegangjo.group.repository.GroupTradeItemRepository;
import com.chaegangjo.group.repository.GroupTradeRepository;
import com.chaegangjo.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupTradeService {

    private final GroupTradeRepository groupTradeRepository;
    private final GroupTradeItemRepository groupTradeItemRepository;

    public record ItemData(String itemName, int price) {}

    @Transactional
    public GroupTrade saveTrade(Group group, String name, List<ItemData> items, int memberCount) {
        int totalPrice = items.stream().mapToInt(ItemData::price).sum();
        int pricePerPerson = memberCount > 0 ? (int) Math.ceil((double) totalPrice / memberCount) : totalPrice;

        GroupTrade groupTrade = groupTradeRepository.save(
                GroupTrade.builder()
                        .group(group)
                        .name(name)
                        .pricePerPerson(pricePerPerson)
                        .build()
        );

        items.forEach(item -> groupTradeItemRepository.save(new GroupTradeItem(groupTrade, item.itemName(), item.price())));

        return groupTrade;
    }

    public Slice<GroupTrade> findTradesByGroupId(Long groupId, CursorPage page) {
        return groupTradeRepository.findAllByGroupIdAndCursor(groupId, page);
    }
}
