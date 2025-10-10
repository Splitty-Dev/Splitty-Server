package com.chaegangjo.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SetNeighborhood(
        @Schema(example = "37.57446155379673")
        double latitude,
        @Schema(example = "127.03442476646347")
        double longitude) {
}