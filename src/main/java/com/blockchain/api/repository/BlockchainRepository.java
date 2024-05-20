package com.blockchain.api.repository;

import com.blockchain.api.domain.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<Blockchain, String> {
    public Blockchain findById(long address);
}
