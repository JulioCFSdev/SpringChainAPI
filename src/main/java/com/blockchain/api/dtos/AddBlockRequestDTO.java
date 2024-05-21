package com.blockchain.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddBlockRequestDTO(
        @NotBlank
        long blockchainID,
        @NotBlank
        String data
) {
}
