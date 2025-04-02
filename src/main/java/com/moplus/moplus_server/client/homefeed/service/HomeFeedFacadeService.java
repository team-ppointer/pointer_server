package com.moplus.moplus_server.client.homefeed.service;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.admin.publish.service.PublishGetService;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse.DailyProgressResponse;
import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse.ProblemSetHomeFeedResponse;
import com.moplus.moplus_server.client.submit.domain.ProgressStatus;
import com.moplus.moplus_server.client.submit.service.ProblemSubmitGetService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeFeedFacadeService {

    private static final LocalDate today = LocalDate.now();
    private static final LocalDate monday = today.with(DayOfWeek.MONDAY);
    private static final LocalDate friday = today.with(DayOfWeek.FRIDAY);
    private final ProblemSetStatisticRepository problemSetStatisticRepository;
    private final PublishGetService publishGetService;
    private final ProblemSetGetService problemSetGetService;
    private final ProblemSubmitGetService problemSubmitGetService;

    @Transactional(readOnly = true)
    public HomeFeedResponse getHomeFeed(Member member) {
        log.info("홈피드 조회 시작 - memberId: {}, 조회 기간: {} ~ {}", member.getId(), monday, friday);
        Long memberId = member.getId();

        List<Publish> publishes = publishGetService.getPublishesBetweenDates(monday, friday);
        log.info("조회된 발행 개수: {}", publishes.size());

        List<DailyProgressResponse> dailyProgresses = getDailyProgresses(memberId, publishes);
        log.info("일일 진행 현황 생성 완료 - 개수: {}", dailyProgresses.size());

        List<ProblemSetHomeFeedResponse> problemSets = getWeekdayProblemSets(publishes);
        log.info("문제 세트 응답 생성 완료 - 개수: {}", problemSets.size());

        return HomeFeedResponse.of(dailyProgresses, problemSets);
    }

    private List<DailyProgressResponse> getDailyProgresses(Long memberId, List<Publish> publishes) {
        log.info("일일 진행 현황 조회 시작 - memberId: {}", memberId);
        
        Map<LocalDate, ProgressStatus> progressStatuses = problemSubmitGetService.getProgressStatuses(memberId, publishes);
        log.info("진행 상태 조회 완료 - 상태 개수: {}", progressStatuses.size());
        progressStatuses.forEach((date, status) -> 
            log.info("진행 상태 - 날짜: {}, 상태: {}", date, status));

        List<DailyProgressResponse> responses = new ArrayList<>();
        for (LocalDate date = monday; !date.isAfter(friday); date = date.plusDays(1)) {
            ProgressStatus status = progressStatuses.getOrDefault(date, ProgressStatus.NOT_STARTED);
            log.info("일일 진행 현황 생성 - 날짜: {}, 상태: {}", date, status);
            responses.add(DailyProgressResponse.of(date, status));
        }

        return responses;
    }

    private List<ProblemSetHomeFeedResponse> getWeekdayProblemSets(List<Publish> publishes) {
        log.info("주간 문제 세트 조회 시작 - 발행 개수: {}", publishes.size());

        Map<LocalDate, Publish> publishByDate = publishes.stream()
                .collect(Collectors.toMap(Publish::getPublishedDate, publish -> publish));
        log.info("날짜별 발행 매핑 완료 - 매핑 개수: {}", publishByDate.size());

        publishByDate.forEach((date, publish) -> 
            log.info("발행 정보 - 날짜: {}, 발행 ID: {}, 문제 세트 ID: {}", 
                    date, publish.getId(), publish.getProblemSetId()));

        List<Long> problemSetIds = publishes.stream()
                .map(Publish::getProblemSetId)
                .toList();
        log.info("문제 세트 ID 추출 완료 - ID 목록: {}", problemSetIds);

        Map<Long, ProblemSetGetResponse> problemSetMap = problemSetGetService.getProblemSets(problemSetIds).stream()
                .collect(Collectors.toMap(ProblemSetGetResponse::id, response -> response));
        log.info("문제 세트 정보 조회 완료 - 조회된 세트 개수: {}", problemSetMap.size());

        List<ProblemSetHomeFeedResponse> responses = new ArrayList<>();
        for (LocalDate date = monday; !date.isAfter(friday); date = date.plusDays(1)) {
            log.info("날짜별 응답 생성 시작 - 날짜: {}", date);
            
            Publish publish = publishByDate.get(date);
            if (publish != null) {
                ProblemSetGetResponse problemSet = problemSetMap.get(publish.getProblemSetId());
                Long submitCount = problemSetStatisticRepository.findById(problemSet.id())
                        .map(ProblemSetStatistic::getSubmitCount)
                        .orElse(0L);
                
                log.info("응답 생성 - 날짜: {}, 발행 ID: {}, 문제 세트 ID: {}, 제출 수: {}", 
                        date, publish.getId(), problemSet.id(), submitCount);
                responses.add(ProblemSetHomeFeedResponse.of(date, publish.getId(), problemSet, submitCount));
            } else {
                log.info("발행 없음 - 날짜: {}", date);
                responses.add(ProblemSetHomeFeedResponse.of(date));
            }
        }

        log.info("주간 문제 세트 응답 생성 완료 - 총 응답 개수: {}", responses.size());
        return responses;
    }
} 