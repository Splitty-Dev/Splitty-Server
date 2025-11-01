package com.chaegangjo.openfeign.api;

import com.chaegangjo.openfeign.dto.TMapReverseGeocodingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "TMapOpenFeign", url="${tmap.url}")
public interface TMapOpenFeign {

    @GetMapping("/geo/reversegeocoding")
    TMapReverseGeocodingResponse reverseGeocoding(
            @RequestParam("version") int version,
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon,
            @RequestParam("addressType") String addressType,
            @RequestHeader("appKey") String appKey
    );
}
