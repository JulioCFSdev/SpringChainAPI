package com.blockchain.api.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.google.gson.Gson;

import java.util.Date;

import com.blockchain.api.domain.service.UtilsService;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Block {
    @Getter
    private int index;
    @Getter
    private long timestamp;
    @Id
    @Getter
    private String hash;
    @Getter
    private String previousHash;
    @Getter
    private String data;
    private int nonce;


    public Block(int index, long timestamp, String previousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;
        nonce = 0;
        hash = UtilsService.calculateHash(this);
    }

    public Block(int index, long timestamp, String previousHash, Object dataObject) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        Gson gson = new Gson();
        this.data = gson.toJson(dataObject);
        nonce = 0;
        hash = UtilsService.calculateHash(this);
    }

    public String str() {
        return index + timestamp + previousHash + data + nonce;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Block #").append(index).append(" [previousHash : ").append(previousHash).append(", ").
                append("timestamp : ").append(new Date(timestamp)).append(", ").append("data : ").append(data).append(", ").
                append("hash : ").append(hash).append("]");
        return builder.toString();
    }

    public void proofOfWork(int difficulty) {
        nonce = 0;

        while (!getHash().substring(0,  difficulty).equals(UtilsService.zeros(difficulty))) {
            nonce++;
            hash = UtilsService.calculateHash(this);
        }
    }
}
