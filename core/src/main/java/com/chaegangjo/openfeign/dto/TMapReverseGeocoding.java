package com.chaegangjo.openfeign.dto;

public record TMapReverseGeocoding(TMapAddressInfo addressInfo) {

    public record TMapAddressInfo(String adminDong) {

    }
}
