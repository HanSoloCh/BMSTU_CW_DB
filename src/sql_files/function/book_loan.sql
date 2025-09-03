CREATE OR REPLACE FUNCTION process_book_loan()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    available integer;
BEGIN
    SELECT available_copies
    INTO available
    FROM book
    WHERE id = NEW.book_id
    FOR UPDATE;

    IF available <= 0 THEN
        RAISE EXCEPTION 'Нет доступных экземпляров книги с id=%', NEW.book_id;
    END IF;

    UPDATE book
    SET available_copies = available_copies - 1
    WHERE id = NEW.book_id;

    RETURN NEW;
END;
$$;
