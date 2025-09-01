CREATE OR REPLACE FUNCTION get_queue_number(p_book_id UUID, p_user_id UUID)
RETURNS INT 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN (
        SELECT position
        FROM (
            SELECT user_id,
                   ROW_NUMBER() OVER (PARTITION BY book_id ORDER BY created_at) AS position
            FROM queue
            WHERE book_id = p_book_id
        ) q
        WHERE q.user_id = p_user_id
    );
END;
$$;
