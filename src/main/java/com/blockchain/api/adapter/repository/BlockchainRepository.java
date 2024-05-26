package com.blockchain.api.adapter.repository;

import com.blockchain.api.domain.entity.Blockchain;
import com.blockchain.api.ports.IBlockchainRepository;

public interface BlockchainRepository extends IBlockchainRepository{
    public Blockchain findById(long address);
}
