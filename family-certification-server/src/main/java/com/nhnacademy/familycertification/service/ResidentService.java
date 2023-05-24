package com.nhnacademy.familycertification.service;

import com.nhnacademy.familycertification.domain.ResidentModifyDTO;
import com.nhnacademy.familycertification.domain.ResidentRegisterDTO;
import com.nhnacademy.familycertification.entity.Resident;
import com.nhnacademy.familycertification.repository.ResidentRepository;
import com.nhnacademy.familycertification.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResidentService {
    private final ResidentRepository residentRepository;

    private final PasswordEncoder passwordEncoder;
    public Resident register(ResidentRegisterDTO residentRegisterDTO){

        Resident resident = new Resident().builder()
                .name(residentRegisterDTO.getName())
                .residentRegistrationNumber(residentRegisterDTO.getResidentRegistrationNumber())
                .loginId(residentRegisterDTO.getLoginId())
                .loginPwd(passwordEncoder.encode(residentRegisterDTO.getLoginPwd()))
                .birthDate(residentRegisterDTO.getBirthDate())
                .birthPlaceCode(residentRegisterDTO.getBirthPlaceCode())
                .registrationBaseAddress(residentRegisterDTO.getRegistrationBaseAddress())
                .deathPlaceCode(residentRegisterDTO.getDeathPlaceCode())
                .deathDate(residentRegisterDTO.getDeathDate())
                .genderCode(residentRegisterDTO.getGenderCode())
                .deathPlaceAddress(residentRegisterDTO.getDeathPlaceAddress())
                .build();

        return residentRepository.saveAndFlush(resident);
    }

    public Resident modify(Long serialNum, ResidentModifyDTO residentModifyDTO) {

        Resident resident = residentRepository.findById(serialNum).orElseThrow(NotFoundResidentException::new);

        resident.modifyResidentInfo(
            residentModifyDTO.getName(),
            residentModifyDTO.getRegistrationBaseAddress(),
            residentModifyDTO.getDeathDate(),
            residentModifyDTO.getDeathPlaceCode(),
            residentModifyDTO.getDeathPlaceAddress()
        );

        return residentRepository.saveAndFlush(resident);
    }

    public Page<Resident> findAll(Pageable pageable) {
        return residentRepository.findAll(pageable);
    }

    public Resident findBySerialId(Long serialNumber) {
        return residentRepository.findById(serialNumber).orElseThrow(NotFoundResidentException::new);
    }


    public Resident findByResidentId(String residentId) {
        return residentRepository.findByLoginId(residentId).orElseThrow(NotFoundResidentException::new);
    }
}
