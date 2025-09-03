CREATE OR REPLACE FUNCTION process_book_return()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    next_user uuid;
    book_uuid uuid;
    total_copies int;
    free_copies int;
BEGIN
    book_uuid := OLD.book_id;

    SELECT copies, available_copies
    INTO total_copies, free_copies
    FROM book
    WHERE id = book_uuid;

    IF free_copies < total_copies THEN
        UPDATE book
        SET available_copies = available_copies + 1
        WHERE id = book_uuid;
    END IF;

    SELECT user_id INTO next_user
    FROM queue
    WHERE book_id = book_uuid
    ORDER BY created_at
    LIMIT 1;

    IF next_user IS NOT NULL THEN
		INSERT INTO reservation(id, book_id, user_id, reservation_date, cancel_date)
		VALUES (gen_random_uuid(), book_uuid, next_user, NOW(), NOW() + INTERVAL '3 days');

        DELETE FROM queue
        WHERE user_id = next_user
          AND book_id = book_uuid;
    END IF;

    RETURN OLD;
END;
$$;
