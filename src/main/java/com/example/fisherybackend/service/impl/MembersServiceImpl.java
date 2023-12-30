package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.enums.AccessLevel;
import com.example.fisherybackend.enums.CommonResponseReason;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Component
public class MembersServiceImpl implements MembersService {

    @Autowired
    private MembersRepository membersRepository;

    @Override
    public Optional<Members> memberLoginByUsernamePassword(String username, String password) {
        return membersRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public CommonResponse createMember(MembersRequest membersRequest) {

        Optional<Members> optionalMember = membersRepository.findByUsername(membersRequest.getUsername());
        if (optionalMember.isPresent()) {
            return new CommonResponse("Member already exist, try with another username.", false, CommonResponseReason.MEMBER_USERNAME_NOTUNIQUE);
        }
        Members member = new Members();

        member.setUsername(membersRequest.getUsername());
        member.setPassword(membersRequest.getPassword());
        member.setFirstName(membersRequest.getFirstName());
        member.setLastName(membersRequest.getLastName());
        member.setAccessLevel(Objects.isNull(membersRequest.getAccessLevel()) ? AccessLevel.MEMBER : membersRequest.getAccessLevel());
        if (Objects.nonNull(membersRequest.getHoursLogged())) {
            member.setHoursLogged(membersRequest.getHoursLogged());
        }
        if (Objects.nonNull(membersRequest.getMembersHash())) {
            member.setMembersHash(membersRequest.getMembersHash());
        }

        if (Objects.nonNull(membersRequest.getProfilePicture())) {
            try {
                Blob blob = new SerialBlob(membersRequest.getProfilePicture());
                member.setProfilePicture(blob);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        membersRepository.save(member);
        return new CommonResponse("New Member Created");
    }

    @Override
    public CommonResponse updateMember(MembersRequest membersRequest) {

        CommonResponse commonResponse = new CommonResponse("New Member Updated");

        membersRepository.findById(membersRequest.getUserId()).ifPresentOrElse(
                member -> {
                    member.setUsername(membersRequest.getUsername());
                    member.setPassword(membersRequest.getPassword());
                    if (Objects.nonNull(membersRequest.getProfilePicture())) {
                        try {
                            Blob blob = new SerialBlob(membersRequest.getProfilePicture());
                            member.setProfilePicture(blob);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    member.setFirstName(membersRequest.getFirstName());
                    member.setLastName(membersRequest.getLastName());
                    member.setHoursLogged(membersRequest.getHoursLogged());
                    member.setMembersHash(membersRequest.getMembersHash());
                    membersRepository.save(member);
                    },
                () -> { commonResponse.setMessage("Member Not Found to Update"); });

        return commonResponse;
    }

    @Override
    public void deleteMember(Long memberId) {
        Optional<Members> memberToDelete = membersRepository.findById(memberId);
        if (memberToDelete.isPresent() && memberToDelete.get().getMemberId().equals(memberId)){
            membersRepository.deleteById(memberId);
        }
    }

}
