package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.blockchain.FisheringMadeBlockChain;
import com.example.fisherybackend.entities.CountrySetting;
import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.enums.CommonResponseReason;
import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.CountrySettingRepository;
import com.example.fisherybackend.repository.FisheringMadeRepository;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.repository.TypeOfFishRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Transactional
@Component
@Configurable
public class FisheringMadeServiceImpl implements FisheringMadeService {
    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private TypeOfFishRepository typeOfFishRepository;

    @Autowired
    private CountrySettingRepository countrySettingRepository;

    @Autowired
    private FisheringMadeRepository fisheringMadeRepository;

    @Override
    public CommonResponse createFisheringMade(FisheringMadeRequest fisheringMadeRequest) {
        CountrySetting countrySetting = countrySettingRepository.searchCountrySettingByCountry(fisheringMadeRequest.getCountry());

        if (Objects.isNull(countrySetting)) {
            return new CommonResponse("Not created, country setting is missing.", false, CommonResponseReason.COUNTRY_NOTFOUND);
        }

        Members members = membersRepository.findById(fisheringMadeRequest.getMemberId()).get();
        TypeOfFish typeOfFish = typeOfFishRepository.findById(fisheringMadeRequest.getTypeOfFishId()).get();

        FisheringMade fisheringMade = new FisheringMade();

        fisheringMade.setMembers(members);
        fisheringMade.setTypeOfFish(typeOfFish);
        fisheringMade.setCountry(fisheringMadeRequest.getCountry());
        fisheringMade.setLocation(fisheringMadeRequest.getLocation());
        fisheringMade.setRegion(fisheringMadeRequest.getRegion());
        fisheringMade.setWeightKg(fisheringMadeRequest.getWeightKg());
        fisheringMade.setRegion(CalculateRegion(countrySetting, fisheringMadeRequest.getLocation()));
        fisheringMade.setTimeLog(LocalDateTime.now());

        try {
            Blob blob = new SerialBlob(fisheringMadeRequest.getPictureOfFish().getBytes());
            fisheringMade.setPictureOfFish(blob);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        IsValidAndNewBlockCreatedDTO isValidAndNewBlockCreatedDTO = isBlockChainValid(fisheringMade);

        if (!isValidAndNewBlockCreatedDTO.isValid) {
            return new CommonResponse("Blockchain has been tampered.", false, CommonResponseReason.BLOCKCHAIN_TAMPERED);
        }

        if (Objects.nonNull(isValidAndNewBlockCreatedDTO.getNewBlockFisheringMade())) {
            fisheringMadeRepository.save(isValidAndNewBlockCreatedDTO.getNewBlockFisheringMade());
        }

        return new CommonResponse("New Fish Harvested Record Created");
    }

    @SneakyThrows
    private void instantiateBlockChain() {
        if (fisheringMadeRepository.findAll().isEmpty()) {
            byte[] bytes = "GenesisBlock".getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);;

            FisheringMade.FisheringMadeBuilder genesisBlockBuilder = FisheringMade.builder()
                    .blockchainIndex(0)
                    .weightKg((double) 0)
                    .location("GenesisBlock")
                    .region(Region.NORTH_EAST)
                    .country(Country.MAURITIUS)
                    .timestamp(System.currentTimeMillis())
                    .members(new Members(1L))
                    .typeOfFish(new TypeOfFish(1L))
                    .pictureOfFish(blob)
                    .timeLog(LocalDateTime.now())
                    .previousHash("GenesisBlock");

            FisheringMade genesisBlock = genesisBlockBuilder.build();
            FisheringMade genesisBlockFollowUp = genesisBlockBuilder.build();

            genesisBlock.setHash(genesisBlock.calculateHash());

            genesisBlockFollowUp.setBlockchainIndex(genesisBlock.getBlockchainIndex() + 1);
            genesisBlockFollowUp.setPreviousHash(genesisBlock.getHash());
            genesisBlockFollowUp.setHash(genesisBlockFollowUp.calculateHash());

            fisheringMadeRepository.saveAllAndFlush(Arrays.asList(genesisBlock, genesisBlockFollowUp));
        }
    }

    private IsValidAndNewBlockCreatedDTO isBlockChainValid(FisheringMade fisheringMade) {
        FisheringMadeBlockChain fisheringMadeBlockChain = new FisheringMadeBlockChain();
        IsValidAndNewBlockCreatedDTO isValidAndNewBlockCreatedDTO = new IsValidAndNewBlockCreatedDTO();

        if (Objects.nonNull(fisheringMade)) {
            instantiateBlockChain();
        }
        List<FisheringMade> fisheringMadeFromDB = fisheringMadeRepository.findAll();

        fisheringMadeFromDB.stream().sorted(Comparator.comparing(FisheringMade::getBlockchainIndex))
                .forEach(fisheringMadeBlockChain::addExistingBlock);
        if (Objects.nonNull(fisheringMade)) {
            isValidAndNewBlockCreatedDTO.setNewBlockFisheringMade(fisheringMadeBlockChain.addBlock(fisheringMade));
        }
        isValidAndNewBlockCreatedDTO.setValid(fisheringMadeBlockChain.isChainValid());

        return isValidAndNewBlockCreatedDTO;
    }

    private Region CalculateRegion(CountrySetting countrySetting, String location) {

        BigDecimal middleLatitudes =  new BigDecimal(countrySetting.getLatitude());
        BigDecimal middleLongtitude =  new BigDecimal(countrySetting.getLongtitude());

        String[] strArray= location.split(Pattern.quote(","));
        BigDecimal requestLatitudes = new BigDecimal(Arrays.asList(strArray).get(0));
        BigDecimal requestLongtitude = new BigDecimal(Arrays.asList(strArray).get(1));
        Region region = null;

        if (requestLatitudes.compareTo(middleLatitudes) > 0) {
            if (requestLongtitude.compareTo(middleLongtitude) > 0) {
                region = Region.NORTH_EAST;
            } else {
                region = Region.SOUTH_EAST;
            }
        } else {
            if (requestLongtitude.compareTo(middleLongtitude) > 0) {
                region = Region.NORTH_WEST;
            } else {
                region = Region.SOUTH_WEST;
            }
        }
        return region;
    }

    @Override
    public List<FisheringMade> getFisheringMadeByMemberId(Long memberId) {
        List<FisheringMade> fisheringMadeListFromDb = fisheringMadeRepository.searchFisheringMadeByMembersMemberId(memberId);

        if (!isBlockChainValid(null).isValid) {
            throw new RuntimeException();
        }

        return fisheringMadeListFromDb;
    }

    @Getter
    @Setter
    class IsValidAndNewBlockCreatedDTO {
        boolean isValid;
        FisheringMade newBlockFisheringMade;
    }
}
