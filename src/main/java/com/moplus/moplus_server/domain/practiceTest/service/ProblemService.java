package com.moplus.moplus_server.domain.practiceTest.service;

import com.moplus.moplus_server.domain.practiceTest.dto.request.ProblemCreateRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Problem;
import com.moplus.moplus_server.domain.practiceTest.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public void saveProblems(PracticeTest practiceTest, HttpServletRequest request) {

        List<ProblemCreateRequest> problems = new ArrayList<>();

        for (int i = 1; i <= practiceTest.getSubject().getProblemCount(); i++) {
            String problemNumber = String.valueOf(i);
            String answerFormat = request.getParameter("answerFormat_" + i);
            String answer = request.getParameter("answer_" + i);
            int point = Integer.parseInt(request.getParameter("point_" + i));
            double correctRate = Double.parseDouble(request.getParameter("correctRate_" + i));

            ProblemCreateRequest problem = new ProblemCreateRequest(problemNumber, answerFormat, answer, point, correctRate);
            problems.add(problem);
        }
        problems.forEach( problem -> problemRepository.save(problem.toEntity(practiceTest)));
    }

    @Transactional
    public void updateProblems(PracticeTest practiceTest, HttpServletRequest request) {
        List<Problem> problems = problemRepository.findAllByPracticeTestId(practiceTest.getId());

        for (int i = 1; i <= practiceTest.getSubject().getProblemCount(); i++) {
            Problem problem = problems.get(i - 1);
            problem.updateAnswer(request.getParameter("answer_" + i));
            problem.updatePoint(Integer.parseInt(request.getParameter("point_" + i)));
            problem.updateCorrectRate(Double.parseDouble(request.getParameter("correctRate_" + i)));

            problemRepository.save(problem);
        }
    }

    public Problem getProblemByPracticeTestIdAndNumber(Long practiceId, String problemNumber) {
        return problemRepository.findByProblemNumberAndPracticeTestId(problemNumber, practiceId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    public List<ProblemGetResponse> getProblemsByTestId(Long testId) {
        return problemRepository.findAllByPracticeTestId(testId).stream()
            .map(ProblemGetResponse::from)
            .toList();
    }
}
