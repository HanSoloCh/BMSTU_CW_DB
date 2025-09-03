CREATE TABLE IF NOT EXISTS bbk (
    id UUID PRIMARY KEY,
    code VARCHAR(16) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS apu (
    id UUID PRIMARY KEY,
    term VARCHAR(100) NOT NULL,
    bbk_id UUID REFERENCES bbk(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS author (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS publisher (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    foundation_year INT,
    email VARCHAR(50),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS book (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    annotation TEXT,
    publisher_id UUID REFERENCES publisher(id) ON DELETE CASCADE,
    publication_year INT,
    ISBN VARCHAR(50),
    bbk_id UUID REFERENCES bbk(id) ON DELETE RESTRICT NOT NULL,
    media_type VARCHAR(100),
    volume TEXT,
    language VARCHAR(100),
    original_language VARCHAR(100),
    copies INT DEFAULT 0 NOT NULL,
    available_copies INT DEFAULT 0 NOT NULL CHECK (available_copies <= copies)
);

CREATE TABLE IF NOT EXISTS book_author (
    book_id UUID REFERENCES book(id) ON DELETE CASCADE,
    author_id UUID REFERENCES author(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    second_name VARCHAR(50),
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    role VARCHAR(20) DEFAULT 'READER' NOT NULL
);

CREATE TABLE IF NOT EXISTS issuance (
    id UUID PRIMARY KEY,
    book_id UUID REFERENCES book(id) ON DELETE CASCADE NOT NULL,
    user_id UUID REFERENCES "user"(id) ON DELETE CASCADE NOT NULL,
    issuance_date DATE NOT NULL,
    return_date DATE NOT NULL CHECK (return_date > issuance_date)
);

CREATE TABLE IF NOT EXISTS reservation (
    id UUID PRIMARY KEY,
    book_id UUID REFERENCES book(id) ON DELETE CASCADE NOT NULL,
    user_id UUID REFERENCES "user"(id) ON DELETE CASCADE NOT NULL,
    reservation_date DATE NOT NULL,
    cancel_date DATE NOT NULL CHECK (cancel_date > reservation_date)
);

CREATE TABLE IF NOT EXISTS user_book (
    user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    book_id UUID REFERENCES book(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id)
);

CREATE TABLE IF NOT EXISTS queue (
    id UUID PRIMARY KEY,
    book_id UUID REFERENCES book(id) ON DELETE CASCADE NOT NULL,
    user_id UUID REFERENCES "user"(id) ON DELETE CASCADE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);


