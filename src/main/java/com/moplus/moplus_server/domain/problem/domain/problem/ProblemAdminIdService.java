package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemAdminIdService {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1); // XXX 값 증가를 위한 카운터
    private final ProblemRepository problemRepository;

    /*
        문제 ID 생성 로직
        C : 문제 타입 ( 1: 기출문제, 2: 변형문제, 3: 창작문제 )
        S : ( 1: 고1, 2: 고2, 3: 미적분, 4: 기하, 5: 확률과 통계, 6: 가형, 7: 나형 )
        YY: 년도 (두 자리)
        MM: 월 (두 자리)
        NN : 번호 (01~99)
        XX : 2자리 sequence 숫자
     */
    public ProblemCustomId nextId(int number, PracticeTestTag practiceTestTag, ProblemType problemType) {

        int problemTypeCode = problemType.getCode(); // C (문제 타입)
        int subject = practiceTestTag.getSubject().getCode(); // S (과목)
        int year = practiceTestTag.getYear() % 100; // YY (두 자리 연도)
        int month = practiceTestTag.getMonth(); // MM (두 자리 월)

        String generatedId;
        int sequence;

        // 중복되지 않는 ID 찾을 때까지 반복
        do {
            sequence = SEQUENCE.getAndIncrement() % 100; // 000~999 순환
            generatedId = String.format("%d%d%02d%02d%02d%02d",
                    problemTypeCode, subject, year, month, number, sequence);
        } while (problemRepository.existsByProblemCustomId(new ProblemCustomId(generatedId))); // ID가 이미 존재하면 재생성

        return new ProblemCustomId(generatedId);
    }

    public ProblemCustomId nextId(ProblemType problemType) {

        int problemTypeCode = problemType.getCode(); // C (문제 타입)

        String generatedId;
        int sequence;

        // 중복되지 않는 ID 찾을 때까지 반복
        do {
            sequence = SEQUENCE.getAndIncrement() % 100; // 000~999 순환
            generatedId = String.format("%d%09d",
                    problemTypeCode, sequence);
        } while (problemRepository.existsByProblemCustomId(new ProblemCustomId(generatedId))); // ID가 이미 존재하면 재생성

        return new ProblemCustomId(generatedId);
    }
}
