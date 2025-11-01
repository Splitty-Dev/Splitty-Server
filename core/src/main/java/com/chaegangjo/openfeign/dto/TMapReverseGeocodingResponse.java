package com.chaegangjo.openfeign.dto;

public record TMapReverseGeocodingResponse(TMapAddressInfo addressInfo) {

    public record TMapAddressInfo(String adminDong) {

    }
}
