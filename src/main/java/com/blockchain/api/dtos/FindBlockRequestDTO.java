package com.blockchain.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record FindBlockRequestDTO(
        @NotBlank
        Long blockchainID,
        @NotBlank
        String blockHash
) {
}
