package com.chaegangjo.member.appllication;


import com.chaegangjo.member.dto.MemberInfo;
import com.chaegangjo.member.dto.request.SetNeighborhoodRequest;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.openfeign.api.TMapOpenFeign;
import com.chaegangjo.openfeign.dto.TMapReverseGeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SetNeighborhoodUsecase {

    private final MemberService memberService;
    private final TMapOpenFeign tMapOpenFeign;

    @Value("${tmap.app-key}")
    private String appKey;
    @Value("${tmap.reverse-geocoding.version}")
    private int version;
    @Value("${tmap.reverse-geocoding.address-type}")
    private String addressType;

    public MemberInfo execute(Long memberId, SetNeighborhoodRequest request) {

        //T Map Reverse Geocoding API 호출
        TMapReverseGeocodingResponse reverseGeocoding = tMapOpenFeign.reverseGeocoding(
                version,
                String.valueOf(request.latitude()), String.valueOf(request.longitude()),
                addressType,
                appKey);
        String adminDong = reverseGeocoding.addressInfo().adminDong();

        return MemberInfo.from(
                memberService.saveMemberLocation(memberId, request.latitude(), request.longitude(), adminDong));
    }

}
