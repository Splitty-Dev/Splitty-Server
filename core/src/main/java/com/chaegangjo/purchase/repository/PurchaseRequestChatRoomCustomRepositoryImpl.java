package com.chaegangjo.purchase.repository;

import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.purchase.domain.QPurchaseRequest;
import com.chaegangjo.purchase.domain.QPurchaseRequestChatRoom;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PurchaseRequestChatRoomCustomRepositoryImpl implements PurchaseRequestChatRoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseRequestChatRoom> findAllByMemberId(Long memberId) {
        QPurchaseRequestChatRoom chatRoom = QPurchaseRequestChatRoom.purchaseRequestChatRoom;
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        QMember requester = new QMember("requester");
        QMember helper = new QMember("helper");

        return queryFactory.selectFrom(chatRoom)
                .join(chatRoom.purchaseRequest, purchaseRequest).fetchJoin()
                .join(purchaseRequest.requester, requester).fetchJoin()
                .join(chatRoom.helper, helper).fetchJoin()
                .where(chatRoom.active.isTrue()
                        .and(requester.id.eq(memberId).or(helper.id.eq(memberId))))
                .orderBy(chatRoom.id.desc())
                .fetch();
    }

    @Override
    public Optional<PurchaseRequestChatRoom> findByIdWithDetails(Long chatRoomId) {
        QPurchaseRequestChatRoom chatRoom = QPurchaseRequestChatRoom.purchaseRequestChatRoom;
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        QMember requester = new QMember("requester");
        QMember helper = new QMember("helper");

        return Optional.ofNullable(queryFactory.selectFrom(chatRoom)
                .join(chatRoom.purchaseRequest, purchaseRequest).fetchJoin()
                .join(purchaseRequest.requester, requester).fetchJoin()
                .join(chatRoom.helper, helper).fetchJoin()
                .where(chatRoom.id.eq(chatRoomId))
                .fetchOne());
    }

    @Override
    public Optional<PurchaseRequestChatRoom> findByPurchaseRequestIdAndHelperId(Long purchaseRequestId, Long helperId) {
        QPurchaseRequestChatRoom chatRoom = QPurchaseRequestChatRoom.purchaseRequestChatRoom;

        return Optional.ofNullable(queryFactory.selectFrom(chatRoom)
                .where(chatRoom.purchaseRequest.id.eq(purchaseRequestId)
                        .and(chatRoom.helper.id.eq(helperId)))
                .fetchOne());
    }
}
