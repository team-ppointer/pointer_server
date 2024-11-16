package com.moplus.moplus_server.domain.practiceTest.service.client;

import com.moplus.moplus_server.domain.practiceTest.dto.admin.request.ProblemCreateRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.client.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
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

            ProblemCreateRequest problem = new ProblemCreateRequest(problemNumber, answerFormat, answer, point,
                    correctRate);
            problems.add(problem);
        }
        List<Problem> problemsEntities = problems.stream()
                .map(problem -> problem.toEntity(practiceTest))
                .toList();
        problemsEntities
                .forEach(Problem::calculateProblemRating);
        problemRepository.saveAll(problemsEntities);
    }

    @Transactional
    public void updateProblems(PracticeTest practiceTest, HttpServletRequest request) {
        List<Problem> problems = problemRepository.findAllByPracticeTestId(practiceTest.getId());

        for (int i = 1; i <= practiceTest.getSubject().getProblemCount(); i++) {
            Problem problem = problems.get(i - 1);
            problem.updateAnswer(request.getParameter("answer_" + i));
            problem.updatePoint(Integer.parseInt(request.getParameter("point_" + i)));
            problem.updateCorrectRate(Double.parseDouble(request.getParameter("correctRate_" + i)));

            problem.calculateProblemRating();
            problemRepository.save(problem);
        }
    }


    public Problem getProblemByPracticeTestIdAndNumber(Long practiceId, String problemNumber) {
        return problemRepository.findByProblemNumberAndPracticeTestId(problemNumber, practiceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    @Transactional
    public Problem updateCorrectRate(Long practiceTestId, String problemNumber, double correctRate) {
        Problem problem = problemRepository.findByProblemNumberAndPracticeTestIdWithPessimisticLock(problemNumber,
                        practiceTestId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
        problem.getPracticeTest();
        problem.updateCorrectRate(correctRate);
        return problemRepository.save(problem);
    }

    public List<ProblemGetResponse> getProblemsByTestId(Long testId) {
        return problemRepository.findAllByPracticeTestId(testId).stream()
                .map(ProblemGetResponse::from)
                .toList();
    }
}
