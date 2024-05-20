package com.blockchain.api.service;

import com.blockchain.api.domain.Block;
import com.blockchain.api.domain.Blockchain;
import org.springframework.stereotype.Service;

@Service
public class BlockchainValidationService {

    public boolean isFirstBlockValid(Blockchain blockchain) {
        if (blockchain.getBlocks().isEmpty()) {
            return false;
        }

        Block firstBlock = blockchain.getBlocks().get(0);
        if (firstBlock.getIndex() != 0) {
            return false;
        }

        if (firstBlock.getPreviousHash() != null) {
            return false;
        }

        String expectedHash = UtilsService.calculateHash(firstBlock);
        if (!firstBlock.getHash().equals(expectedHash)) {
            return false;
        }

        return true;
    }

    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (newBlock.getIndex() != previousBlock.getIndex() + 1) {
            return false;
        }

        if (!newBlock.getPreviousHash().equals(previousBlock.getHash())) {
            return false;
        }

        String recalculatedHash = UtilsService.calculateHash(newBlock);
        if (!newBlock.getHash().equals(recalculatedHash)) {
            return false;
        }

        return true;
    }

    public boolean isBlockchainValid(Blockchain blockchain) {
        if (!isFirstBlockValid(blockchain)) {
            return false;
        }

        for (int i = 1; i < blockchain.getBlocks().size(); i++) {
            Block currentBlock = blockchain.getBlocks().get(i);
            Block previousBlock = blockchain.getBlocks().get(i - 1);
            if (!isValidNewBlock(currentBlock, previousBlock)) {
                return false;
            }
        }

        return true;
    }
}

