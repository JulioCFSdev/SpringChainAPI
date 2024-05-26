package com.blockchain.api.adapter.dtos;

import jakarta.validation.constraints.NotBlank;

public record FindBlockByTopicRequestDTO(
        @NotBlank
        String fieldName,
        @NotBlank
        String value,
        @NotBlank
        long idBlockchain
) {
}
