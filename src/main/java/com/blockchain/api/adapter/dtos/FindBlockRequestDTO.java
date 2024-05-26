package com.blockchain.api.adapter.dtos;

import jakarta.validation.constraints.NotBlank;

public record FindBlockRequestDTO(
        @NotBlank
        Long blockchainID,
        @NotBlank
        String blockHash
) {
}
