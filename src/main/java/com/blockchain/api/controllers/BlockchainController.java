package com.blockchain.api.controllers;

import com.blockchain.api.domain.Block;
import com.blockchain.api.domain.Blockchain;
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
    public ResponseEntity<Blockchain> initBlockchain(@RequestParam int difficulty) {
        Blockchain blockchain = blockchainService.initNewBlockchain(difficulty);
        return new ResponseEntity<>(blockchain, HttpStatus.CREATED);
    }

    @PostMapping("/blocks")
    public ResponseEntity<Block> addBlock(@RequestBody Block block, @RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blockchainService.addBlock(blockchain, block);
        return new ResponseEntity<>(block, HttpStatus.CREATED);
    }

    @GetMapping("/blocks/latest")
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
    public ResponseEntity<String> validateBlockchain(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>("Blockchain not found", HttpStatus.NOT_FOUND);
        }
        boolean isValid = blockchainValidationService.isBlockchainValid(blockchain);
        return new ResponseEntity<>(isValid ? "Blockchain is valid" : "Blockchain is invalid", HttpStatus.OK);
    }

    @GetMapping("/blocks/find")
    public ResponseEntity<Block> getBlockByHash(@RequestParam Long blockchainId, @RequestParam String hash) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Block> block = blockchain.getBlocks().stream()
                .filter(b -> hash.equals(b.getHash()))
                .findFirst();

        return block.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

