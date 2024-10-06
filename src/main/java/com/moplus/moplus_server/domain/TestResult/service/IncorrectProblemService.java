package com.moplus.moplus_server.domain.TestResult.service;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.response.IncorrectProblemGetResponse;
import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.repository.IncorrectProblemRepository;
import com.moplus.moplus_server.domain.practiceTest.entity.Problem;
import com.moplus.moplus_server.domain.practiceTest.service.ProblemService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncorrectProblemService {

    private final IncorrectProblemRepository incorrectProblemRepository;
    private final ProblemService problemService;

    public List<IncorrectProblem> saveIncorrectProblems(
        List<IncorrectProblemPostRequest> requests,
        Long practiceTestId,
        TestResult testResult) {
        List<Problem> problems = requests.stream()
            .map(request -> problemService.getProblemByPracticeTestIdAndNumber(practiceTestId, request.problemNumber()))
            .toList();

        List<IncorrectProblem> incorrectProblems = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            Problem matchedProblem = problems.get(i);
            IncorrectProblem tempIncorrectProblem = requests.get(i).toEntity(matchedProblem);

            tempIncorrectProblem.setTestResult(testResult);
            tempIncorrectProblem.setPracticeTestId(practiceTestId);
            IncorrectProblem save = incorrectProblemRepository.save(tempIncorrectProblem);
            incorrectProblems.add(save);
        }
        return incorrectProblems;
    }

    public List<IncorrectProblemGetResponse> getResponsesByTestResultId(Long testResultId) {
        return incorrectProblemRepository.findAllByTestResultId(testResultId).stream()
            .map(IncorrectProblemGetResponse::from)
            .toList();
    }
}
