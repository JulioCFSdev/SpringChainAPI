package com.blockchain.api.service;

import com.blockchain.api.domain.Block;
import com.blockchain.api.domain.Blockchain;
import com.blockchain.api.repository.BlockchainRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BlockchainService {

    @Autowired
    private BlockchainRepository blockchainRepository;
    @Autowired
    private BlockchainValidationService blockchainValidationService;
    @Autowired
    private ObjectMapper objectMapper;

    public Blockchain initNewBlockchain(int difficulty) throws Exception{
        Blockchain blockchain = new Blockchain(difficulty);
        Block genesis = initGenesisBlock(difficulty);
        blockchain.getBlocks().add(genesis);
        if (blockchainValidationService.isFirstBlockValid(blockchain)) {
            blockchainRepository.save(blockchain);
        } else {
            throw new IllegalStateException("Invalid genesis block.");
        }
        return blockchain;
    }

    private Block initGenesisBlock(int difficulty) {
        Block genesis = new Block(0, System.currentTimeMillis(), null, "Block Gênesis");
        genesis.proofOfWork(difficulty);
        return genesis;
    }

    public void addBlock(Blockchain blockchain, Block block) {
        Block latestBlock = latestBlock(blockchain);
        if (block != null && blockchainValidationService.isValidNewBlock(block, latestBlock)) {
            block.proofOfWork(blockchain.getDifficulty());
            blockchain.getBlocks().add(block);
            blockchainRepository.save(blockchain);
        } else {
            throw new IllegalStateException("Attempting to add invalid block.");
        }
    }

    public Blockchain findBlockchainById(long id) {
        return blockchainRepository.findById(id);
    }

    public Block latestBlock(Blockchain blockchain) {
        return blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
    }

    public List<Block> findBlocksByDataField(String fieldName, String value, long blockchainId) {
        Blockchain blockchain = blockchainRepository.findById(blockchainId);
        if (blockchain == null) {
            throw new IllegalStateException("Blockchain not found.");
        }

        List<Block> matchingBlocks = new ArrayList<>();
        for (Block block : blockchain.getBlocks()) {
            if (block.getIndex() == 0) {
                continue;
            }
            JsonNode dataNode;
            try {
                dataNode = objectMapper.readTree(block.getData());
                if (dataNode.has(fieldName) && dataNode.get(fieldName).asText().equals(value)) {
                    matchingBlocks.add(block);
                }
            } catch (IOException e) {
                System.err.println("Error parsing JSON: " + e.getMessage());
            }
        }
        return matchingBlocks;
    }

    public Block findLastBlockCreatedByDataField(String fieldName, String value, long blockchainId) {
        Blockchain blockchain = blockchainRepository.findById(blockchainId);
        if (blockchain == null) {
            throw new IllegalStateException("Blockchain not found.");
        }

        Block matchingBlock = new Block();
        List<Block> blocks = blockchain.getBlocks();
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            JsonNode dataNode;
            try {
                dataNode = objectMapper.readTree(block.getData());
                if (dataNode.has(fieldName) && dataNode.get(fieldName).asText().equals(value)) {
                    matchingBlock = block;
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error parsing JSON: " + e.getMessage());
            }
        }
        return matchingBlock;
    }
}


