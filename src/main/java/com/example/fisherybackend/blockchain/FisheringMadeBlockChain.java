package com.example.fisherybackend.blockchain;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.repository.FisheringMadeRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import com.example.fisherybackend.service.impl.FisheringMadeServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FisheringMadeBlockChain {

    @Getter
    private List<FisheringMade> blockChain = new ArrayList<>();

    private FisheringMade getLatestBlock() {
        return blockChain.get(blockChain.size() - 1);
    }
    public FisheringMade addBlock(FisheringMade data) {
        FisheringMade previousBlock = getLatestBlock();
        FisheringMade newBlock = new FisheringMade(previousBlock.getBlockchainIndex() + 1, data, previousBlock.getHash(), false);
        blockChain.add(newBlock);
        return newBlock;
    }

    public void addExistingBlock(FisheringMade data) {
        FisheringMade newBlock = new FisheringMade(data.getBlockchainIndex(), data, data.getPreviousHash(), true);
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

    public void setBlockChain(List<FisheringMade> blockChain) {
        this.blockChain = blockChain;
    }
}
