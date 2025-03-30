DELETE FROM problem_set_problems;
DELETE FROM problem_set;

-- 문제 세트 추가
INSERT INTO problem_set (problem_set_id, title, deleted, confirm_status)
VALUES (1, '2025년 5월 고2 모의고사 문제 세트', false, 'CONFIRMED');

-- 문제 세트에 포함된 문제 추가
INSERT INTO problem_set_problems (problem_set_id, problem_id, sequence)
VALUES (1, '240520012001', 0),
       (1, '240520012002', 1);