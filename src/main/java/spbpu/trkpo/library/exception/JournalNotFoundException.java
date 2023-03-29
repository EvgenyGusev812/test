package spbpu.trkpo.library.exception;

public class JournalNotFoundException extends RuntimeException {


    public JournalNotFoundException() {
        super("Журнал не найден");
    }
}
