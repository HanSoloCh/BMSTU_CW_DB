CREATE ROLE guest
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  NOINHERIT
  NOREPLICATION
  NOBYPASSRLS
  LOGIN
  PASSWORD 'guest';

GRANT SELECT ON book, author, bbk, apu, book_author TO guest;
GRANT SELECT (id, name) ON publisher TO guest;
GRANT SELECT, INSERT ON "user" TO guest;

CREATE ROLE reader
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  NOINHERIT
  NOREPLICATION
  NOBYPASSRLS
  LOGIN
  PASSWORD 'reader';

GRANT SELECT ON book, author, bbk, apu, book_author TO reader;
GRANT SELECT (id, name) ON publisher TO reader;
GRANT SELECT, UPDATE ON "user" TO reader;
GRANT SELECT ON favorite, queue, issuance, reservation TO reader;
GRANT INSERT, DELETE ON favorite, reservation, queue TO reader;


CREATE ROLE librarian
  LOGIN
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  NOINHERIT
  NOREPLICATION
  NOBYPASSRLS;

GRANT SELECT ON book, author, bbk, apu, book_author, publisher TO librarian;
GRANT SELECT, DELETE ON reservation, queue TO librarian;
GRANT SELECT, INSERT, DELETE ON issuance TO librarian;

CREATE ROLE moderator
  LOGIN
  SUPERUSER
  CREATEDB
  CREATEROLE
  INHERIT
  REPLICATION
  BYPASSRLS;


