package com.blockchain.api.controllers;

import com.blockchain.api.domain.Block;
import com.blockchain.api.domain.Blockchain;
import com.blockchain.api.dtos.AddBlockRequestDTO;
import com.blockchain.api.dtos.FindBlockRequestDTO;
import com.blockchain.api.dtos.FindBlockByTopicRequestDTO;
import com.blockchain.api.service.BlockchainService;
import com.blockchain.api.service.BlockchainValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;
    @Autowired
    private BlockchainValidationService blockchainValidationService;

    @PostMapping("/init")
    public ResponseEntity<Blockchain> initBlockchain(@RequestParam int difficulty) throws Exception{
        Blockchain blockchain = blockchainService.initNewBlockchain(difficulty);
        return new ResponseEntity<>(blockchain, HttpStatus.CREATED);
    }

    @PostMapping("/block")
    public ResponseEntity<Block> addBlock(@RequestBody AddBlockRequestDTO data) throws Exception {
        Blockchain blockchain = blockchainService.findBlockchainById(data.blockchainID());
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Block PreviousBlock = blockchainService.latestBlock(blockchain);
        int indexPreviousBlock = PreviousBlock.getIndex();
        String hashPreviousBlock = PreviousBlock.getHash();
        Block newBlock = new Block(indexPreviousBlock + 1, System.currentTimeMillis(), hashPreviousBlock, data.blockData());
        blockchainService.addBlock(blockchain, newBlock);
        return new ResponseEntity<>(newBlock, HttpStatus.CREATED);
    }

    @GetMapping("/block/latest")
    public ResponseEntity<Block> getLatestBlock(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Block latestBlock = blockchainService.latestBlock(blockchain);
        return new ResponseEntity<>(latestBlock, HttpStatus.OK);
    }

    @GetMapping("/blocks")
    public ResponseEntity<List<Block>> getAllBlocks(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Block> blocks = blockchain.getBlocks();
        return new ResponseEntity<>(blocks, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateBlockchain(@RequestParam Long blockchainId) throws Exception{
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>("Blockchain not found", HttpStatus.NOT_FOUND);
        }
        boolean isValid = blockchainValidationService.isBlockchainValid(blockchain);
        return new ResponseEntity<>(isValid ? "Blockchain is valid" : "Blockchain is invalid", HttpStatus.OK);
    }

    @GetMapping("/block/find")
    public ResponseEntity<Block> getBlockByHash(@RequestBody FindBlockRequestDTO data) {
        Blockchain blockchain = blockchainService.findBlockchainById(data.blockchainID());
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Block> block = blockchain.getBlocks().stream()
                .filter(b -> data.blockHash().equals(b.getHash()))
                .findFirst();

        return block.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("blocks/find/topic")
    public ResponseEntity<List<Block>> getBlocksByTopic(@RequestBody FindBlockByTopicRequestDTO data) {
        try {
            List<Block> blocks = blockchainService.findBlocksByDataField(
                    data.fieldName(),
                    data.value(),
                    data.idBlockchain()
            );

            if (blocks.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(blocks);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("block/latest/find/topic")
    public ResponseEntity<Block> getLastBlocksByTopic(@RequestBody FindBlockByTopicRequestDTO data) {
        try {
            Block block = blockchainService.findLastBlockCreatedByDataField(
                    data.fieldName(),
                    data.value(),
                    data.idBlockchain()
            );

            if (block == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(block);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

