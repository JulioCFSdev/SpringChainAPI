package com.blockchain.api.service;

import com.blockchain.api.domain.Block;
import com.blockchain.api.domain.Blockchain;
import com.blockchain.api.repository.BlockRepository;
import com.blockchain.api.repository.BlockchainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BlockchainService {

    @Autowired
    private BlockchainRepository blockchainRepository;
    @Autowired
    private BlockRepository blockRepository;

    public Blockchain initNewBlockchain(int difficulty) {
        Blockchain blockchain = new Blockchain(difficulty);
        Block genesis = initGenesisBlock(difficulty);
        blockchain.getBlocks().add(genesis);
        blockRepository.save(genesis);
        blockchainRepository.save(blockchain);
        return blockchain;
    }

    private Block initGenesisBlock(int difficulty) {
        Block genesis = new Block(0, System.currentTimeMillis(), null, "Block Gênesis");
        genesis.proofOfWork(difficulty);
        return genesis;
    }

    public void addBlock(Blockchain blockchain, Block block) {
        if (block != null) {
            block.proofOfWork(blockchain.getDifficulty());
            blockchain.getBlocks().add(block);
            blockRepository.save(block);
            blockchainRepository.save(blockchain);
        }
    }

    public Block latestBlock(Blockchain blockchain) {
        return blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
    }
}



