package com.varchar6.petcast.domain.company.command.application.controller;

import com.varchar6.petcast.common.response.ResponseMessage;
import com.varchar6.petcast.domain.company.command.application.controller.vo.request.EnrollRequestVO;
import com.varchar6.petcast.domain.company.command.application.controller.vo.response.CompanyResponseVO;
import com.varchar6.petcast.domain.company.command.application.dto.request.CompanyEnrollRequestDTO;
import com.varchar6.petcast.domain.company.command.application.dto.response.CompanyResponseDTO;
import com.varchar6.petcast.domain.company.command.application.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("request")
    public ResponseEntity<ResponseMessage> requestCompanyEnroll(
            @RequestAttribute("memberId") int memberId,
            @RequestBody EnrollRequestVO enrollRequestVO
    ) {
        CompanyEnrollRequestDTO companyEnrollRequestDTO = new CompanyEnrollRequestDTO();
        CompanyResponseVO companyResponseVO = new CompanyResponseVO();

        companyEnrollRequestDTO.setName(enrollRequestVO.getName());
        companyEnrollRequestDTO.setAddress(enrollRequestVO.getAddress());
        companyEnrollRequestDTO.setEmployeeNumber(enrollRequestVO.getEmployeeNumber());
        companyEnrollRequestDTO.setCareer(enrollRequestVO.getCareer());
        companyEnrollRequestDTO.setLicense(enrollRequestVO.getLicense());
        companyEnrollRequestDTO.setIntroduction(enrollRequestVO.getIntroduction());
        companyEnrollRequestDTO.setContactableTime(enrollRequestVO.getContactableTime());
        companyEnrollRequestDTO.setMemberId(memberId);

        CompanyResponseDTO companyResponseDTO = companyService.applyEnroll(companyEnrollRequestDTO);
        companyResponseVO.setId(companyResponseDTO.getId());
        companyResponseVO.setName(companyResponseDTO.getName());
        companyResponseVO.setAddress(companyResponseDTO.getAddress());
        companyResponseVO.setEmployeeNumber(companyResponseDTO.getEmployeeNumber());
        companyResponseVO.setCareer(companyResponseDTO.getCareer());
        companyResponseVO.setLicense(companyResponseDTO.getLicense());
        companyResponseVO.setIntroduction(companyResponseDTO.getIntroduction());
        companyResponseVO.setContactableTime(companyResponseDTO.getContactableTime());
        companyResponseVO.setCreatedAt(companyResponseDTO.getCreatedAt());
        companyResponseVO.setUpdatedAt(companyResponseDTO.getUpdatedAt());
        companyResponseVO.setActive(companyResponseDTO.isActive());
        companyResponseVO.setApproved(companyResponseDTO.isApproved());

        return ResponseEntity
                .ok()
                .body(
                        ResponseMessage.builder()
                                .httpStatus(HttpStatus.CREATED.value())
                                .message("Enrollment Requested Successfully")
                                .result(companyResponseVO)
                                .build()
                );
    }
}
