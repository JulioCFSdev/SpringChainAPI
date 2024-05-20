package com.blockchain.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Blockchain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blockchain")
    private List<Block> blocks = new ArrayList<>();

    private int difficulty;

    public Blockchain() {}

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
    }

}


