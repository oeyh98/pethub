package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.vet.request.VetUpdateRequestDto;
import ium.pethub.dto.vet.response.VetInfoResponseDto;
import ium.pethub.dto.vet.response.VetResponseDto;
import ium.pethub.service.VetService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class VetController {
    private final VetService vetService;

    @GetMapping("api/vet/vets/{page}")
    public ResponseEntity getVets(@PathVariable int page){
        Page<VetResponseDto> vets =  vetService.getAllVets(page);

        return ResponseEntity.ok().body(vets);
    }

    @GetMapping("api/vet/{vetId}")
    public ResponseEntity getVetById(@PathVariable Long vetId){
        VetInfoResponseDto responseDto = vetService.getByVetId(vetId);
        return ResponseEntity.ok().body(new ResponseDto("", responseDto));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.VET)
    @PutMapping("api/vet")
    public ResponseEntity updateVet(VetUpdateRequestDto requestDto){
        Long vetId = UserContext.userData.get().getUserId();
        vetService.vetUpdate(vetId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("수의사 정보 수정에 성공하였습니다"));
    }
}
