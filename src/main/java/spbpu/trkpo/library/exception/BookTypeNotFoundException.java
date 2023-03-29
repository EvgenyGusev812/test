package spbpu.trkpo.library.exception;

public class BookTypeNotFoundException extends RuntimeException {

    public BookTypeNotFoundException() {
        super("Тип книги не найден");
    }

}
