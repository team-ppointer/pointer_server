package com.moplus.moplus_server.domain.v0.TestResult.service;

import com.moplus.moplus_server.domain.v0.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.v0.TestResult.dto.response.IncorrectProblemGetResponse;
import com.moplus.moplus_server.domain.v0.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.v0.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.v0.TestResult.repository.IncorrectProblemRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
import com.moplus.moplus_server.domain.v0.practiceTest.service.client.ProblemService;
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
        List<ProblemForTest> problemForTests = requests.stream()
                .map(request -> problemService.getProblemByPracticeTestIdAndNumber(practiceTestId,
                        request.problemNumber()))
                .toList();

        List<IncorrectProblem> incorrectProblems = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            ProblemForTest matchedProblemForTest = problemForTests.get(i);
            IncorrectProblem tempIncorrectProblem = requests.get(i).toEntity(matchedProblemForTest);

            tempIncorrectProblem.setTestResult(testResult);
            tempIncorrectProblem.setPracticeTestId(practiceTestId);
            tempIncorrectProblem.setCorrectRate(matchedProblemForTest.getCorrectRate());
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
