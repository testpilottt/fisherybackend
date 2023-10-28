package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.CountrySetting;
import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.CountrySettingRepository;
import com.example.fisherybackend.repository.FisheringMadeRepository;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.repository.TypeOfFishRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
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
            return new CommonResponse("Not created, country setting is missing.", false);
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
            Blob blob = new SerialBlob(fisheringMadeRequest.getPictureOfFish());
            fisheringMade.setPictureOfFish(blob);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        fisheringMade.setFisheringHash(fisheringMadeRequest.getFisheringHash());

        fisheringMadeRepository.save(fisheringMade);

        return new CommonResponse("New Member Created");
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
        return fisheringMadeRepository.searchFisheringMadeByMembersMemberId(memberId);
    }
}
