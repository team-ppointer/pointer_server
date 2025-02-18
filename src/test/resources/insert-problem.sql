DELETE FROM child_problem_concept;
DELETE FROM problem_concept;
DELETE FROM child_problem;
DELETE FROM problem;

-- 데이터 삽입
INSERT INTO problem (problem_id,
                     problem_custom_id,
                     practice_test_id,
                     number,
                     problem_type,
                     title,
                     answer,
                     difficulty,
                     memo,
                     main_problem_image_url,
                     main_analysis_image_url,
                     reading_tip_image_url,
                     senior_tip_image_url,
                     prescription_image_urls,
                     answer_type,
                     is_confirmed,
                     recommended_minute,
                     recommended_second)
VALUES (1, '1224052001', 1, 1, 'GICHUL_PROBLEM', '제목1', '1', 5, '기존 문제 설명 1',
        'mainProblem.png1', 'mainAnalysis.png1', 'readingTip.png1', 'seniorTip.png1',
        'prescription.png1', 'MULTIPLE_CHOICE', false, 30, 45),
       (2, '1224052002', 1, 1, 'GICHUL_PROBLEM', '제목2', '1', 5, '기존 문제 설명 2',
        'mainProblem.png2', 'mainAnalysis.png2', 'readingTip.png2', 'seniorTip.png2',
        'prescription.png2', 'MULTIPLE_CHOICE', false, 25, 30);

-- 자식 문제 테이블 생성
CREATE TABLE IF NOT EXISTS child_problem (
    child_problem_id BIGINT PRIMARY KEY,
    problem_id BIGINT,
    image_url VARCHAR(255),
    answer_type VARCHAR(50),
    answer VARCHAR(255),
    sequence INT
);

-- 자식 문제 데이터 삽입
INSERT INTO child_problem (child_problem_id,
                           problem_id,
                           image_url,
                           answer_type,
                           answer,
                           sequence)
VALUES (1, 1, 'child1.png', 'MULTIPLE_CHOICE', '1', 0),
       (2, 1, 'child2.png', 'SHORT_STRING_ANSWER', '정답2', 1);

-- 문제-컨셉 태그 연결 테이블 생성
CREATE TABLE IF NOT EXISTS problem_concept (
    problem_id BIGINT,
    concept_tag_id BIGINT,
    PRIMARY KEY (problem_id, concept_tag_id)
);

-- 문제-컨셉 태그 데이터 삽입
INSERT INTO problem_concept (problem_id, concept_tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 3);

-- 자식 문제-컨셉 태그 연결 테이블 생성
CREATE TABLE IF NOT EXISTS child_problem_concept (
    child_problem_id BIGINT,
    concept_tag_id BIGINT,
    PRIMARY KEY (child_problem_id, concept_tag_id)
);

-- 자식 문제-컨셉 태그 데이터 삽입
INSERT INTO child_problem_concept (child_problem_id, concept_tag_id)
VALUES (1, 3),
       (1, 4),
       (2, 5),
       (2, 6);