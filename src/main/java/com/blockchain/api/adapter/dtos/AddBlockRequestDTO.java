package com.blockchain.api.adapter.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddBlockRequestDTO(
        @NotBlank
        long blockchainID,
        @NotBlank
        Object blockData
) {
}
