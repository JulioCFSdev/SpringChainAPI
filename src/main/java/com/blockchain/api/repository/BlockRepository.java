package com.blockchain.api.repository;

import com.blockchain.api.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
}
