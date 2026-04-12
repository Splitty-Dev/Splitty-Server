package com.chaegangjo.purchase.repository;

import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.domain.QPurchaseRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PurchaseRequestCustomRepositoryImpl implements PurchaseRequestCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PurchaseRequest> findAllByCursor(CursorPage cursorPage) {
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        QMember requester = QMember.member;

        List<PurchaseRequest> content = queryFactory.selectFrom(purchaseRequest)
                .join(purchaseRequest.requester, requester).fetchJoin()
                .where(ltCursorId(cursorPage.getCursorId(), purchaseRequest))
                .orderBy(purchaseRequest.id.desc())
                .limit(cursorPage.getSize() + 1)
                .fetch();

        boolean hasNext = content.size() > cursorPage.getSize();
        if (hasNext) content.removeLast();

        return new SliceImpl<>(content, PageRequest.of(0, cursorPage.getSize()), hasNext);
    }

    @Override
    public Optional<PurchaseRequest> findByIdWithRequester(Long id) {
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        QMember requester = QMember.member;

        return Optional.ofNullable(queryFactory.selectFrom(purchaseRequest)
                .join(purchaseRequest.requester, requester).fetchJoin()
                .where(purchaseRequest.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression ltCursorId(Long cursorId, QPurchaseRequest purchaseRequest) {
        if (cursorId == null) return null;
        return purchaseRequest.id.lt(cursorId);
    }
}
