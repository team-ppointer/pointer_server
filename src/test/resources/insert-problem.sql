DELETE
FROM child_problem_concept;
DELETE
FROM child_problem;

INSERT INTO problem (problem_id, practice_test_id, number, answer, comment, main_problem_image_url,
                     main_analysis_image_url, reading_tip_image_url, senior_tip_image_url, prescription_image_url,
                     is_published, is_variation)
VALUES ('240520012001', 1, 1, '1', '기존 문제 코멘트1',
        'mainProblem.png1', 'mainAnalysis.png1', 'readingTip.png1', 'seniorTip.png1', 'prescription.png1',
        false, false),
       ('240520012002', 1, 1, '1', '기존 문제 코멘트2',
        'mainProblem.png2', 'mainAnalysis.png2', 'readingTip.png2', 'seniorTip.png2', 'prescription.png2',
        false, false);
-- 기존 자식 문제(ChildProblem) 삽입
INSERT INTO child_problem (child_problem_id, problem_id, image_url, problem_type, answer, sequence)
VALUES (1, '240520012001', 'child1.png', 'MULTIPLE_CHOICE', '1', 0),
       (2, '240520012001', 'child2.png', 'SHORT_STRING_ANSWER', '정답2', 0);

-- 문제-컨셉 태그 연결 (기존 문제의 ConceptTag)
INSERT INTO problem_concept (problem_id, concept_tag_id)
VALUES ('240520012001', 1),
       ('240520012001', 2),
       ('240520012001', 3),
       ('240520012002', 1),
       ('240520012002', 3);

-- 자식 문제-컨셉 태그 연결
INSERT INTO child_problem_concept (child_problem_id, concept_tag_id)
VALUES (1, 3),
       (1, 4),
       (2, 5),
       (2, 6);