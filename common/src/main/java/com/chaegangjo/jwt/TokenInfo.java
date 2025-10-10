package com.chaegangjo.jwt;

public record TokenInfo(String email, Long id, String accessToken) {
}