package com.moplus.moplus_server.client.submit.service;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProgressStatus;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSubmitGetService {

    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemSetRepository problemSetRepository;

    @Transactional(readOnly = true)
    public ProgressStatus getProgressStatus(Long memberId, Long publishId) {
        List<ProblemSubmit> submits = problemSubmitRepository.findByMemberIdAndPublishId(memberId, publishId);

        if (submits.isEmpty()) {
            return ProgressStatus.NOT_STARTED;
        }

        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(submits.get(0).getProblemId());

        int totalProblems = problemSet.getProblemIds().size();

        if (submits.size() == totalProblems) {
            return ProgressStatus.COMPLETED;
        }
        return ProgressStatus.IN_PROGRESS;
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, ProgressStatus> getProgressStatuses(Long memberId, List<Publish> publishes) {
        return publishes.stream()
                .collect(Collectors.toMap(
                    Publish::getPublishedDate,
                    publish -> getProgressStatus(memberId, publish.getId())
                ));
    }
} 