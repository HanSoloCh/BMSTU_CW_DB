
CREATE TABLE language (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);


INSERT INTO language (name)
SELECT DISTINCT language FROM book WHERE language IS NOT NULL;


ALTER TABLE book ADD COLUMN language_id UUID;
ALTER TABLE book ADD COLUMN secondary_language_id UUID;


UPDATE book
SET language_id = language.id
FROM language
WHERE book.language = language.name;


ALTER TABLE book DROP COLUMN language;


ALTER TABLE book ALTER COLUMN language_id SET NOT NULL;


ALTER TABLE book
ADD CONSTRAINT fk_book_language
FOREIGN KEY (language_id) REFERENCES language(id);

ALTER TABLE book
ADD CONSTRAINT fk_book_secondary_language
FOREIGN KEY (secondary_language_id) REFERENCES language(id);
