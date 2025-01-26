package com.moplus.moplus_server.domain.v0.TestResult.controller;

import com.moplus.moplus_server.domain.v0.TestResult.dto.response.RatingGetResponse;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    @GetMapping("/{practiceTestId}")
    public ResponseEntity<List<RatingGetResponse>> getRating(
            @PathVariable("practiceTestId") Long id
    ) {
        return ResponseEntity.ok(null);
    }
}
