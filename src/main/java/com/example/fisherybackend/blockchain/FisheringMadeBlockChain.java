package com.example.fisherybackend.blockchain;

import com.example.fisherybackend.entities.HarvestedFishRecords;

import java.util.ArrayList;
import java.util.List;

public class FisheringMadeBlockChain {
    private List<HarvestedFishRecords> blockChain = new ArrayList<>();

    public HarvestedFishRecords addBlock(HarvestedFishRecords data) {
        HarvestedFishRecords previousBlock = getLatestBlock();
        HarvestedFishRecords newBlock = new HarvestedFishRecords(previousBlock.getBlockchainIndex() + 1, data,
                previousBlock.getHash(), false);
        blockChain.add(newBlock);
        return newBlock;
    }

    public void addExistingBlock(HarvestedFishRecords data) {
        HarvestedFishRecords newBlock = new HarvestedFishRecords(data.getBlockchainIndex(), data, data.getPreviousHash(), true);
        blockChain.add(newBlock);
    }

    private HarvestedFishRecords getLatestBlock() {
        return blockChain.get(blockChain.size() - 1);
    }

    public boolean isChainValid() {
        for (int i  = 1; i < blockChain.size(); i++) {
            HarvestedFishRecords currentBlock = blockChain.get(i);
            HarvestedFishRecords previousBlock = blockChain.get(i-1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash()) ||
                    !currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}
