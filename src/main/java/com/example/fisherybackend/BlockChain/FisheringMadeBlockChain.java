package com.example.fisherybackend.BlockChain;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.enums.Country;

import java.util.ArrayList;
import java.util.List;

public class FisheringMadeBlockChain {

    private List<FisheringMade> blockChain = new ArrayList<>();

    private void createGenesisBlock() {
        FisheringMade genesisBlock = new FisheringMade(0, new FisheringMade(Country.MAURITIUS), "GenesisBlock");
        blockChain.add(genesisBlock);
        blockChain.add(new FisheringMade(1, new FisheringMade(Country.MAURITIUS), genesisBlock.getHash()));
    }

    private FisheringMade getLatestBlock() {
        if (blockChain.isEmpty()) {
            createGenesisBlock();
        }
        return blockChain.get(blockChain.size() - 1);
    }
    public void addBlock(FisheringMade data) {
        FisheringMade previousBlock = getLatestBlock();
        FisheringMade newBlock = new FisheringMade(previousBlock.getBlockchainIndex() + 1, data, previousBlock.getHash());
        blockChain.add(newBlock);
    }

    public void addExistingBlock(FisheringMade data) {
        FisheringMade newBlock = new FisheringMade(data.getBlockchainIndex(), data, data.getHash());
        blockChain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i  = 1; i < blockChain.size(); i++) {
            FisheringMade currentBlock = blockChain.get(i);
            FisheringMade previousBlock = blockChain.get(i-1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash()) ||
                    !currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public List<FisheringMade> getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(List<FisheringMade> blockChain) {
        this.blockChain = blockChain;
    }
}
