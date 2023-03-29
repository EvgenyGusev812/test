package spbpu.trkpo.library.exception;

public class BookNotFoundException extends RuntimeException {


    public BookNotFoundException() {
        super("Книга не найдена");
    }
}
