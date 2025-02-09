package com.moplus.moplus_server.domain.publish.service;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.domain.publish.dto.response.PublishMonthGetResponse;
import com.moplus.moplus_server.domain.publish.dto.response.PublishProblemSetResponse;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishGetService {

    private final PublishRepository publishRepository;
    private final ProblemSetRepository problemSetRepository;

    @Transactional(readOnly = true)
    public List<PublishMonthGetResponse> getPublishMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new InvalidValueException(ErrorCode.INVALID_MONTH_ERROR);
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 주어진 월에 해당하는 모든 Publish 조회
        List<Publish> publishes = publishRepository.findByPublishedDateBetween(startDate, endDate);

        // 데이터를 day 기준으로 매핑
        return publishes.stream()
                .map(publish -> {
                    ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
                    PublishProblemSetResponse problemSetResponse = PublishProblemSetResponse.of(problemSet);

                    return PublishMonthGetResponse.of(
                            publish.getPublishedDate().getDayOfMonth(),
                            problemSetResponse
                    );
                })
                .collect(Collectors.toList());
    }
}
