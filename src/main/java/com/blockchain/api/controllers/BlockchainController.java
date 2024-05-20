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

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;
    @Autowired
    private BlockchainValidationService blockchainValidationService;

    // Iniciar uma nova Blockchain
    @PostMapping("/init")
    public ResponseEntity<Blockchain> initBlockchain(@RequestParam int difficulty) {
        Blockchain blockchain = blockchainService.initNewBlockchain(difficulty);
        return new ResponseEntity<>(blockchain, HttpStatus.CREATED);
    }

    // Adicionar um bloco à Blockchain
    @PostMapping("/blocks")
    public ResponseEntity<Block> addBlock(@RequestBody Block block, @RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blockchainService.addBlock(blockchain, block);
        return new ResponseEntity<>(block, HttpStatus.CREATED);
    }

    // Recuperar o último bloco da Blockchain
    @GetMapping("/blocks/latest")
    public ResponseEntity<Block> getLatestBlock(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Block latestBlock = blockchainService.latestBlock(blockchain);
        return new ResponseEntity<>(latestBlock, HttpStatus.OK);
    }

    // Recuperar todos os blocos da Blockchain
    @GetMapping("/blocks")
    public ResponseEntity<List<Block>> getAllBlocks(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Block> blocks = blockchain.getBlocks();
        return new ResponseEntity<>(blocks, HttpStatus.OK);
    }

    // Validar a integridade da Blockchain completa
    @GetMapping("/validate")
    public ResponseEntity<String> validateBlockchain(@RequestParam Long blockchainId) {
        Blockchain blockchain = blockchainService.findBlockchainById(blockchainId);
        if (blockchain == null) {
            return new ResponseEntity<>("Blockchain not found", HttpStatus.NOT_FOUND);
        }
        boolean isValid = blockchainValidationService.isBlockchainValid(blockchain);
        return new ResponseEntity<>(isValid ? "Blockchain is valid" : "Blockchain is invalid", HttpStatus.OK);
    }
}

