package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.enums.CommonResponseReason;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import com.example.fisherybackend.service.MembersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/fishery")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @Autowired
    private FisheringMadeService fisheringMadeService;

    @Autowired
    private MembersRepository membersRepository;

    @PostMapping("/membersLogin")
    public ResponseEntity<Members> memberLoginByUsernamePassword(@RequestBody MembersRequest membersRequest) {

        Optional<Members> member = membersService.memberLoginByUsernamePassword(membersRequest.getUsername(), membersRequest.getPassword());

        Members reponseMember = new Members();

        if (!member.isPresent()) {
            reponseMember.setMemberId(-1L);
            return new ResponseEntity<>(reponseMember, HttpStatus.UNAUTHORIZED);
        } else {
            List<HarvestedFishRecords> harvestedFishRecordsList = fisheringMadeService.getFisheringMadeByMemberId(member.get().getMemberId());
            if (!harvestedFishRecordsList.isEmpty()) {
                reponseMember.setCountry(
                        harvestedFishRecordsList.stream()
                                .max(Comparator.comparing(HarvestedFishRecords::getTimeLog))
                                .get().getCountry());
            }
            reponseMember.setMemberId(member.get().getMemberId());
            reponseMember.setAccessLevel(member.get().getAccessLevel());
            return new ResponseEntity<>(reponseMember, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/createMember")
    public ResponseEntity<CommonResponse> createMember(@RequestBody MembersRequest membersRequest) {
        CommonResponse commonResponse = membersService.createMember(membersRequest);
        if (commonResponse.getCommonResponseReason() == CommonResponseReason.MEMBER_USERNAME_NOTUNIQUE) {
            return new ResponseEntity<>(commonResponse,HttpStatus.ALREADY_REPORTED);
        } else {
            return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/deleteMember/{memberId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("memberId") Long memberId) {
        membersService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateMember")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody MembersRequest membersRequest) {
        CommonResponse commonResponse = membersService.updateMember(membersRequest);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/getMembers")
    public ResponseEntity<List<Members>> getMembers() {
        List<Members> membersList = membersRepository.findAll();

        membersList.forEach(member -> {
            if (Objects.nonNull(member.getProfilePicture())) {
                try {
                    int blobLength = (int) member.getProfilePicture().length();
                    byte[] blobAsBytes = member.getProfilePicture().getBytes(1, blobLength);
                    String encodedString = Base64.getEncoder().encodeToString(blobAsBytes);

                    member.setProfilePictureBase64(encodedString);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return new ResponseEntity<>(membersList, HttpStatus.CREATED);
    }
}
