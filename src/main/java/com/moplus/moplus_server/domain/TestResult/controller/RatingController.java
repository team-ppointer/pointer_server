package com.moplus.moplus_server.domain.TestResult.controller;

import com.moplus.moplus_server.domain.TestResult.dto.response.RatingGetResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
