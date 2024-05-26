package com.blockchain.api.ports;

import com.blockchain.api.domain.entity.Block;
import com.blockchain.api.domain.entity.Blockchain;
import java.util.List;

public interface IBlockchainService {
    Blockchain initNewBlockchain(int difficulty) throws Exception;

    Block initGenesisBlock(int difficulty);

    void addBlock(Blockchain blockchain, Block block) throws Exception;

    List<Block> findBlocksByDataField(String fieldName, String value, long blockchainId);

    Blockchain findBlockchainById(long id);

    Block latestBlock(Blockchain blockchain);

    Block findLastBlockCreatedByDataField(String fieldName, String value, long blockchainId);

    void saveBlockchainToJson(Blockchain blockchain) throws Exception;

    List<Blockchain> loadAllBlockchainsFromJsonToJpa() throws Exception;

    int extractIdFromFilename(String filename);
}
