CREATE OR REPLACE FUNCTION process_book_return()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    next_user uuid;
    book_uuid uuid;
BEGIN
    book_uuid := OLD.book_id;

    UPDATE book
    SET available_count = available_count + 1
    WHERE id = book_uuid;

    SELECT user_id INTO next_user
    FROM book_queue
    WHERE book_id = book_uuid
    ORDER BY created_at
    LIMIT 1;

    IF next_user IS NOT NULL THEN
        INSERT INTO reservation(book_id, user_id, created_at)
        VALUES (book_uuid, next_user, NOW());

        UPDATE book
        SET available_count = available_count - 1
        WHERE id = book_uuid;

        DELETE FROM book_queue
        WHERE user_id = next_user
          AND book_id = book_uuid;
    END IF;

    RETURN OLD;
END;
$$;
