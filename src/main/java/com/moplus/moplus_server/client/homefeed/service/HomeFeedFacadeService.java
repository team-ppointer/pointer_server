package com.moplus.moplus_server.client.homefeed.service;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.admin.publish.service.PublishGetService;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse.DailyProgressResponse;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse.ProblemSetHomeFeedResponse;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetGetService;
import com.moplus.moplus_server.member.domain.Member;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemSetStatistic;
import com.moplus.moplus_server.statistic.Problem.repository.ProblemSetStatisticRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeFeedFacadeService {

    private final ProblemSetStatisticRepository problemSetStatisticRepository;
    private final PublishGetService publishGetService;
    private final ProblemSetGetService problemSetGetService;

    @Transactional(readOnly = true)
    public HomeFeedResponse getHomeFeed(Member member) {
        Long memberId = member.getId();

        List<DailyProgressResponse> dailyProgresses = new ArrayList<>(); // 다음 PR에서 구현
        List<ProblemSetHomeFeedResponse> problemSets = getWeekdayProblemSets();

        return HomeFeedResponse.of(dailyProgresses, problemSets);
    }

    private List<ProblemSetHomeFeedResponse> getWeekdayProblemSets() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate friday = today.with(DayOfWeek.FRIDAY);

        // 월요일부터 금요일까지의 발행된 문제 세트 조회
        List<Publish> publishes = publishGetService.getPublishesBetweenDates(monday, friday);
        Map<LocalDate, Publish> publishByDate = publishes.stream()
                .collect(Collectors.toMap(Publish::getPublishedDate, publish -> publish));

        // 문제 세트 정보 조회
        List<Long> problemSetIds = publishes.stream()
                .map(Publish::getProblemSetId)
                .toList();
        Map<Long, ProblemSetGetResponse> problemSetMap = problemSetGetService.getProblemSets(problemSetIds).stream()
                .collect(Collectors.toMap(ProblemSetGetResponse::id, response -> response));

        // 월요일부터 금요일까지의 모든 날짜에 대한 응답 생성
        List<ProblemSetHomeFeedResponse> responses = new ArrayList<>();
        for (LocalDate date = monday; !date.isAfter(friday); date = date.plusDays(1)) {
            Publish publish = publishByDate.get(date);
            if (publish != null) {
                ProblemSetGetResponse problemSet = problemSetMap.get(publish.getProblemSetId());
                Long submitCount = problemSetStatisticRepository.findById(problemSet.id())
                        .map(ProblemSetStatistic::getSubmitCount)
                        .orElse(0L);
                responses.add(ProblemSetHomeFeedResponse.of(date, problemSet, submitCount));
            } else {
                responses.add(ProblemSetHomeFeedResponse.of(date));
            }
        }

        return responses;
    }
} 