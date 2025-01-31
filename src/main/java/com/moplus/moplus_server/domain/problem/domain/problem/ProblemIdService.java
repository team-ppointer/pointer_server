package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemIdService {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1); // XXX 값 증가를 위한 카운터
    private final ProblemRepository problemRepository;

    /*
        문제 ID 생성 로직
        YY: 년도 (두 자리)
        MM: 월 (두 자리)
        NN : 번호 (01~99)
        AA : 영역 ( 01: 수학, 02: 영어, 03: 국어, 04: 사회, 05: 과학 )
        S : ( 1: 고1, 2: 고2, 3: 미적분, 4: 기하, 5: 확률과 통계, 6: 가형, 7: 나형 )
        C : 변형 여부 ( 0: 기본, 1: 변형 )
        XXX : 3자리 구분 숫자
     */
    public ProblemId nextId(int number, PracticeTestTag practiceTestTag) {

        int DEFAULT_AREA = 1; //현재 영역은 수학밖에 없음
        int subject = practiceTestTag.getSubject().getIdCode(); // AA (과목)
        int year = practiceTestTag.getYear() % 100; // YY (두 자리 연도)
        int month = practiceTestTag.getMonth(); // MM (두 자리 월)
        int DEFAULT_MODIFIED = 0; // 변형 여부 (0: 기본, 1: 변형)

        String generatedId;
        int sequence;

        // 중복되지 않는 ID 찾을 때까지 반복
        do {
            sequence = SEQUENCE.getAndIncrement() % 1000; // 000~999 순환
            generatedId = String.format("%02d%02d%02d%02d%d%d%03d",
                    year, month, number, DEFAULT_AREA,
                    subject, DEFAULT_MODIFIED, sequence);
        } while (problemRepository.existsById(new ProblemId(generatedId))); // ID가 이미 존재하면 재생성

        return new ProblemId(generatedId);
    }
}
