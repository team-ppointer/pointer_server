DELETE FROM problem_concept;
DELETE
FROM child_problem_concept;
DELETE
FROM child_problem;
DELETE FROM problem_set_problems;
DELETE FROM problem_set;
DELETE FROM problem;

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