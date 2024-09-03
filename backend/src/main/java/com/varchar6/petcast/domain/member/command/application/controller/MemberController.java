package com.varchar6.petcast.domain.member.command.application.controller;

import com.varchar6.petcast.common.response.ResponseMessage;
import com.varchar6.petcast.domain.member.command.application.dto.request.MemberDeleteRequestDTO;
import com.varchar6.petcast.domain.member.command.application.dto.request.MemberRequestDTO;
import com.varchar6.petcast.domain.member.command.application.dto.request.MemberUpdateRequestDTO;
import com.varchar6.petcast.domain.member.command.application.dto.response.MemberResponseDTO;
import com.varchar6.petcast.domain.member.command.application.service.MemberService;
import com.varchar6.petcast.domain.member.command.application.vo.request.MemberDeleteRequestVO;
import com.varchar6.petcast.domain.member.command.application.vo.request.MemberUpdateRequestVO;
import com.varchar6.petcast.domain.member.command.application.vo.request.RequestRegistUserVO;
import com.varchar6.petcast.domain.member.command.application.vo.response.MemberDeleteResponseVO;
import com.varchar6.petcast.domain.member.command.application.vo.response.MemberUpdateResponseVO;
import com.varchar6.petcast.domain.member.command.application.vo.response.ResponseRegistUserVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "commandMemberController")
@RequestMapping("/api/v1/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/test")
    public String getTest(
            @RequestAttribute("memberId") int memberId,
            @RequestAttribute("memberLoginId") String memberLoginId,
            @RequestAttribute("memberPhone") String memberPhone,
            @RequestAttribute("memberNickname") String memberNickname,
            @RequestAttribute("image") String image,
            @RequestAttribute("created") String created,
            @RequestAttribute("updated") String updated,
            @RequestAttribute("active") boolean active,
            @RequestAttribute("introduction") String introduction,
            @RequestAttribute("authorities") List<String> authorities) {
        log.debug("memberId: {}", memberId);
        log.debug("memberLoginId: {}", memberLoginId);
        log.debug("created: {}", created);
        log.debug("active: {}", active);
        log.debug("authorities: {}", authorities);

        return "GET working";
    }

    @PostMapping("/test")
    public String postTest() {
        return "POST working";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody RequestRegistUserVO newUser){

        MemberRequestDTO memberRequestDTO = modelMapper.map(newUser, MemberRequestDTO.class);

        MemberResponseDTO memberResponseDTO = memberService.registerMember(memberRequestDTO);

        ResponseRegistUserVO responseMember = modelMapper.map(memberResponseDTO, ResponseRegistUserVO.class);

        return ResponseEntity
                .ok()
                .body(
                        ResponseMessage.builder()
                                .httpStatus(HttpStatus.CREATED.value())
                                .message("Login completed")
                                .result(responseMember)
                                .build()
                );
    }

    @PutMapping("/update-member-status")
    public ResponseEntity<MemberUpdateResponseVO> updateMemberStatus(@RequestBody MemberUpdateRequestVO updateStatus){

        MemberUpdateRequestDTO memberUpdateRequestDTO
                = modelMapper.map(updateStatus, MemberUpdateRequestDTO.class);

        MemberResponseDTO memberResponseDTO = memberService.updateMemberStatus(memberUpdateRequestDTO);

        MemberUpdateResponseVO responseMember = modelMapper.map(memberResponseDTO, MemberUpdateResponseVO.class);

        return ResponseEntity.ok().body(responseMember);
    }

    @PutMapping("/update-password")
    public ResponseEntity<MemberUpdateResponseVO> updateMemberPassword(@RequestBody MemberUpdateRequestVO updateMember){

        MemberUpdateRequestDTO memberUpdateRequestDTO = modelMapper.map(updateMember, MemberUpdateRequestDTO.class);

        MemberResponseDTO memberResponseDTO = memberService.updateMemberPwd(memberUpdateRequestDTO);

        MemberUpdateResponseVO responseMember = modelMapper.map(memberResponseDTO, MemberUpdateResponseVO.class);

        return ResponseEntity.ok().body(responseMember);
    }

    @PutMapping("/delete")
    public ResponseEntity<MemberDeleteResponseVO> deleteMember(@RequestBody MemberDeleteRequestVO deleteMember){

        MemberDeleteRequestDTO memberDeleteRequestDTO = modelMapper.map(deleteMember, MemberDeleteRequestDTO.class);

        MemberResponseDTO memberResponseDTO = memberService.deleteMember(memberDeleteRequestDTO);

        MemberDeleteResponseVO responseMember = modelMapper.map(memberResponseDTO, MemberDeleteResponseVO.class);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMember);
    }

}
