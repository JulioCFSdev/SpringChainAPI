package com.blockchain.api.domain.service;

import com.blockchain.api.domain.entity.Block;
import com.blockchain.api.domain.entity.Blockchain;
import com.blockchain.api.adapter.exceptions.InvalidBlockHashException;
import com.blockchain.api.adapter.exceptions.InvalidBlockIndexException;
import com.blockchain.api.adapter.exceptions.InvalidGenesisBlockException;
import com.blockchain.api.adapter.exceptions.InvalidPreviousHashException;
import org.springframework.stereotype.Service;

@Service
public class BlockchainValidationService {

    public boolean isFirstBlockValid(Blockchain blockchain) throws Exception {
        if (blockchain.getBlocks().isEmpty()) {
            throw new InvalidGenesisBlockException();
        }

        Block firstBlock = blockchain.getBlocks().get(0);
        if (firstBlock.getIndex() != 0) {
            throw new InvalidBlockIndexException();
        }

        if (firstBlock.getPreviousHash() != null) {
            throw new InvalidPreviousHashException();
        }

        String expectedHash = UtilsService.calculateHash(firstBlock);
        if (!firstBlock.getHash().equals(expectedHash)) {
            throw new InvalidBlockHashException();
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

    public boolean isBlockchainValid(Blockchain blockchain) throws Exception{
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

