CREATE TRIGGER trg_return_book_from_issuance
AFTER DELETE ON issuance
FOR EACH ROW
EXECUTE FUNCTION process_book_return();

CREATE TRIGGER trg_return_book_from_reservation
AFTER DELETE ON reservation
FOR EACH ROW
EXECUTE FUNCTION process_book_return();

CREATE TRIGGER trg_loan_book_to_issuance
BEFORE INSERT ON issuance
FOR EACH ROW
EXECUTE FUNCTION process_book_loan();

CREATE TRIGGER trg_loan_book_to_reservation
BEFORE INSERT ON reservation
FOR EACH ROW
EXECUTE FUNCTION process_book_loan();