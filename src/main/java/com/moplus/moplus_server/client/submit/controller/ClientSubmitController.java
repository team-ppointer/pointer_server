package com.moplus.moplus_server.client.submit.controller;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.service.ClientSubmitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클라이언트 제출", description = "클라이언트 제출 관련 API")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientSubmitController {

    private final ClientSubmitService clientSubmitService;

    @PostMapping("problemSubmit")
    @Operation(summary = "문항 제출 생성", description = "문항 제출을 '진행중'으로 생성합니다.")
    public ResponseEntity<Void> createProblemSubmit(
            @RequestBody ProblemSubmitCreateRequest request
            ) {
        clientSubmitService.createProblemSubmit(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("problemSubmit")
    @Operation(summary = "문항 제출 업데이트", description = "제출한 답안을 바탕으로 문항 제출의 상태를 업데이트합니다.")
    public ResponseEntity<ProblemSubmitStatus> updateProblemSubmit(
            @RequestBody ProblemSubmitUpdateRequest request
    ) {
        return ResponseEntity.ok(clientSubmitService.updateProblemSubmit(request));
    }

    @PostMapping("childProblemSubmit")
    @Operation(summary = "새끼문항 제출 생성", description = "문항에 속한 새끼문항들을 '시작전'으로 생성합니다.")
    public ResponseEntity<Void> createProblemSubmit(
            @RequestBody ChildProblemSubmitCreateRequest request
    ) {
        clientSubmitService.createChildProblemSubmit(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("childProblemSubmit")
    @Operation(summary = "새끼문항 제출 업데이트", description = "제출한 답안을 바탕으로 문항 제출의 상태를 업데이트합니다.")
    public ResponseEntity<ChildProblemSubmitStatus> updateChildProblemSubmit(
            @RequestBody ChildProblemSubmitUpdateRequest request
    ) {
        return ResponseEntity.ok(clientSubmitService.updateChildProblemSubmit(request));
    }
}
