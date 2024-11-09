INSERT INTO practice_test (
    practice_test_id,
    name,
    round,
    provider,
    view_count,
    solves_count,
    publication_year,
    subject,
    average_solving_time,
    created_at,
    update_at,
    deleted
) VALUES (
             1L,
             'Sample Practice Test',
             'Round 1',
             'Sample Provider',
             0,
             0,
             '2024',
             '미적분',
             0,
             NOW(),
             NOW(),
             false
         );