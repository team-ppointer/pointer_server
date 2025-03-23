package com.moplus.moplus_server.admin.publish.service;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.admin.publish.dto.response.PublishMonthGetResponse;
import com.moplus.moplus_server.admin.publish.dto.response.PublishProblemSetResponse;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

        // 한 번의 쿼리로 모든 ProblemSet 조회
        Map<Long, ProblemSet> problemSetMap = getProblemSetMap(publishes);

        return publishes.stream()
                .map(publish -> convertToResponse(publish, problemSetMap))
                .collect(Collectors.toList());
    }


    private Map<Long, ProblemSet> getProblemSetMap(List<Publish> publishes) {
        List<Long> problemSetIds = publishes.stream()
                .map(Publish::getProblemSetId)
                .distinct()
                .collect(Collectors.toList());

        return problemSetRepository.findAllById(problemSetIds).stream()
                .collect(Collectors.toMap(ProblemSet::getId, problemSet -> problemSet));
    }

    private PublishMonthGetResponse convertToResponse(Publish publish, Map<Long, ProblemSet> problemSetMap) {
        ProblemSet problemSet = problemSetMap.get(publish.getProblemSetId());
        if (problemSet == null) {
            throw new InvalidValueException(ErrorCode.PROBLEM_SET_NOT_FOUND);
        }
        return PublishMonthGetResponse.of(
                publish,
                PublishProblemSetResponse.of(problemSet)
        );
    }

    @Transactional(readOnly = true)
    public List<Publish> getCurrentWeekPublishes() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.FRIDAY);

        return getWeeklyPublishes(startOfWeek, endOfWeek);
    }

    private List<Publish> getWeeklyPublishes(LocalDate startDate, LocalDate endDate) {
        return publishRepository.findByPublishedDateBetween(startDate, endDate);
    }
}
