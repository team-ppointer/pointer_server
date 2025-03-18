package com.moplus.moplus_server.client.submit.service;


import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.response.AllProblemGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.DayProgress;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientGetService {

    private final PublishRepository publishRepository;
    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSetRepository problemSetRepository;

    @Transactional(readOnly = true)
    public List<AllProblemGetResponse> getAllProblem(int year, int month) {

        Long memberId = 1L;

        if (month < 1 || month > 12) {
            throw new InvalidValueException(ErrorCode.INVALID_MONTH_ERROR);
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 해당 월 모든 Publish 조회
        List<Publish> publishes = publishRepository.findByPublishedDateBetween(startDate, endDate);

        List<AllProblemGetResponse> result = new ArrayList<>();

        for (Publish publish : publishes) {
            Long publishId = publish.getId();
            LocalDate date = publish.getPublishedDate();

            // 날짜별 사용자 제출 정보 조회
            List<ProblemSubmit> submissions = problemSubmitRepository.findByMemberIdAndPublishId(memberId, publishId);
            List<ProblemSubmitStatus> problemStatuses = submissions.stream()
                    .map(ProblemSubmit::getStatus)
                    .toList();

            // 사용자 제출 정보 바탕으로 진행도 결정
            DayProgress progress = DayProgress.determineDayProgress(problemStatuses);
            String mainProblemImageUrl = getMainProblemImageUrl(publish.getProblemSetId());

            result.add(AllProblemGetResponse.of(publishId, date, progress, problemStatuses, mainProblemImageUrl));
        }
        return result;
    }

    private String getMainProblemImageUrl(Long problemSetId) {
        return problemSetRepository.findById(problemSetId)
                .flatMap(problemSet -> problemSet.getProblemIds().stream().findFirst())
                .flatMap(problemRepository::findById)
                .map(Problem::getMainProblemImageUrl)
                .orElse(null);
    }
}
