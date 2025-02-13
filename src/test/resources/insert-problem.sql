INSERT INTO problem (problem_id,
                     problem_admin_id,
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
                     is_confirmed)
VALUES (1, '1224052001', 1, 1, 'GICHUL_PROBLEM', '제목1', '1', 5, '기존 문제 설명 1',
        'mainProblem.png1', 'mainAnalysis.png1', 'readingTip.png1', 'seniorTip.png1',
        'prescription.png1', 'MULTIPLE_CHOICE', false),
       (2, '1224052002', 1, 1, 'GICHUL_PROBLEM', '제목2', '1', 5, '기존 문제 설명 2',
        'mainProblem.png2', 'mainAnalysis.png2', 'readingTip.png2', 'seniorTip.png2',
        'prescription.png2', 'MULTIPLE_CHOICE', false);

INSERT INTO child_problem (child_problem_id,
                           problem_id, -- Long 타입으로 변경 (부모 Problem의 id 참조)
                           image_url,
                           answer_type,
                           answer,
                           sequence)
VALUES (1, 1, 'child1.png', 'MULTIPLE_CHOICE', '1', 0),
       (2, 1, 'child2.png', 'SHORT_STRING_ANSWER', '정답2', 0);

-- 문제-컨셉 태그 연결 (기존 문제의 ConceptTag)
INSERT INTO problem_concept (problem_id, -- Long 타입으로 변경 (Problem의 id 참조)
                             concept_tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 3);

-- 자식 문제-컨셉 태그 연결
INSERT INTO child_problem_concept (child_problem_id,
                                   concept_tag_id)
VALUES (1, 3),
       (1, 4),
       (2, 5),
       (2, 6);