package com.blockchain.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record FindBlocksByTopicRequestDTO(
        @NotBlank
        String fieldName,
        @NotBlank
        String value,
        @NotBlank
        long idBlockchain
) {
}
