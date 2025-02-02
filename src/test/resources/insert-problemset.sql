DELETE FROM practice_test_tag;
DELETE FROM concept_tag;
DELETE FROM problem;
DELETE FROM practice_test; -- practice_test 데이터도 삭제

-- practice_test 데이터 삽입
INSERT INTO practice_test (practice_test_id, publication_year, subject, name, provider, round, average_solving_time, solves_count, view_count)
VALUES (1, 2024, '고1', '2024 5월 고1 모의고사', '교육청', '1', 60, 100, 1000);

-- practice-test-tag 데이터 삽입
INSERT INTO practice_test_tag (id, test_year, test_month, subject)
VALUES (1, 2024, 5, '고1');

-- concept-tag 데이터 삽입
INSERT INTO concept_tag (concept_tag_id, name)
VALUES (1, '미적분'),
       (2, '기하'),
       (3, '확통');

-- problem 데이터 삽입
INSERT INTO problem (problem_id, practice_test_id, number, answer, comment, main_problem_image_url,
                     main_analysis_image_url, reading_tip_image_url, senior_tip_image_url, prescription_image_url,
                     is_published, is_variation)
VALUES ('24052001001', 1, 1, '1', '기존 문제 설명',
        'mainProblem.png', 'mainAnalysis.png', 'readingTip.png', 'seniorTip.png', 'prescription.png',
        false, false),
       ('24052001002', 1, 2, '2', '문제 2 설명',
        'mainProblem2.png', 'mainAnalysis2.png', 'readingTip2.png', 'seniorTip2.png', 'prescription2.png',
        false, false),
       ('24052001003', 1, 3, '3', '문제 3 설명',
        'mainProblem3.png', 'mainAnalysis3.png', 'readingTip3.png', 'seniorTip3.png', 'prescription3.png',
        false, false);

-- 유효하지 않은 문제 데이터 삽입 (answer와 mainProblemImageUrl이 NULL)
INSERT INTO problem (problem_id, practice_test_id, number, answer, comment, main_problem_image_url,
                     main_analysis_image_url, reading_tip_image_url, senior_tip_image_url, prescription_image_url,
                     is_published, is_variation)
VALUES ('24052001004', 1, 4, '', '유효하지 않은 문제 설명',
        '', 'mainAnalysis4.png', 'readingTip4.png', 'seniorTip4.png', 'prescription4.png',
        false, false);