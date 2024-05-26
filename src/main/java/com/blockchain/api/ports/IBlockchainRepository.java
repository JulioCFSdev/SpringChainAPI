package com.blockchain.api.ports;

import com.blockchain.api.domain.entity.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlockchainRepository extends JpaRepository<Blockchain, String> {
    Blockchain findById(long address);
}
