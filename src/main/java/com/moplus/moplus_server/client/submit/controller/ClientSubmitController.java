package com.moplus.moplus_server.client.submit.controller;

import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.service.ClientSubmitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("childProblemSubmit")
    @Operation(summary = "새끼문항 제출 생성", description = "문항에 속한 새끼문항들을 '시작전'으로 생성합니다.")
    public ResponseEntity<Void> createProblemSubmit(
            @RequestBody ChildProblemSubmitCreateRequest request
    ) {
        clientSubmitService.createChildProblemSubmit(request);
        return ResponseEntity.ok(null);
    }
}
