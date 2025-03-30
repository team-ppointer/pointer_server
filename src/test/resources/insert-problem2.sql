DELETE
FROM child_problem_concept;
DELETE
FROM problem_concept;
DELETE
FROM child_problem;
DELETE
FROM problem;

-- problem 데이터 삽입
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
                     main_handwriting_explanation_image_url,
                     reading_tip_image_url,
                     senior_tip_image_url,
                     prescription_image_urls,
                     answer_type,
                     is_confirmed,
                     recommended_minute,
                     recommended_second)
VALUES (1, '24052001001', 1, 1, 'GICHUL_PROBLEM', '제목1', '1', 5, '기존 문제 설명',
        'mainProblem.png', 'mainAnalysis.png', 'mainHandwriting1.png', 'readingTip.png', 'seniorTip.png',
        'prescription1.png, prescription2.png', 'MULTIPLE_CHOICE', false,
        30, 0),

       (2, '24052001002', 1, 2, 'GICHUL_PROBLEM', '제목2', '2', 4, '문제 2 설명',
        'mainProblem2.png', 'mainAnalysis2.png', 'mainHandwriting2.png', 'readingTip2.png', 'seniorTip2.png',
        'prescription3.png, prescription4.png', 'MULTIPLE_CHOICE', false,
        20, 30),

       (3, '24052001003', 1, 3, 'GICHUL_PROBLEM', '제목3', '3', 3, '문제 3 설명',
        'mainProblem3.png', 'mainAnalysis3.png', 'mainHandwriting3.png', 'readingTip3.png', 'seniorTip3.png',
        'prescription5.png, prescription6.png', 'SHORT_ANSWER', true,
        15, 45);

-- 자식 문제 데이터 삽입
INSERT INTO child_problem (child_problem_id,
                           problem_id,
                           image_url,
                           answer_type,
                           answer,
                           sequence,
                           prescription_image_urls)
VALUES (1, 1, 'child1.png', 'MULTIPLE_CHOICE', '1', 0, 'child1_prescription1.png, child1_prescription2.png'),
       (2, 1, 'child2.png', 'SHORT_ANSWER', '정답2', 1, 'child2_prescription1.png, child2_prescription2.png'),
       (3, 2, 'child3.png', 'MULTIPLE_CHOICE', '2', 0, 'child3_prescription1.png, child3_prescription2.png'),
       (4, 3, 'child4.png', 'SHORT_ANSWER', '3', 0, 'child4_prescription1.png, child4_prescription2.png');

-- 문제-컨셉 태그 연결
INSERT INTO problem_concept (problem_id, concept_tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 4);

-- 자식 문제-컨셉 태그 연결
INSERT INTO child_problem_concept (child_problem_id, concept_tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 4);

-- 유효하지 않은 문제 데이터를 유효한 데이터로 수정
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
                     main_handwriting_explanation_image_url,
                     reading_tip_image_url,
                     senior_tip_image_url,
                     prescription_image_urls,
                     answer_type,
                     is_confirmed,
                     recommended_minute,
                     recommended_second)
VALUES (4, '24052001004', 1, 4, 'GICHUL_PROBLEM', '제목4', '4', 1, '유효한 문제로 수정',
        'mainProblem4.png', 'mainAnalysis4.png', 'mainHandwriting4.png', 'readingTip4.png', 'seniorTip4.png',
        'prescription7.png, prescription8.png', 'MULTIPLE_CHOICE', false,
        20, 0);

-- problem 4에 대한 자식 문제 추가
INSERT INTO child_problem (child_problem_id,
                           problem_id,
                           image_url,
                           answer_type,
                           answer,
                           sequence)
VALUES (5, 4, 'child5.png', 'MULTIPLE_CHOICE', '4', 0);

-- problem 4와 자식 문제에 대한 컨셉 태그 추가
INSERT INTO problem_concept (problem_id, concept_tag_id)
VALUES (4, 1),
       (4, 3);

INSERT INTO child_problem_concept (child_problem_id, concept_tag_id)
VALUES (5, 2),
       (5, 3);