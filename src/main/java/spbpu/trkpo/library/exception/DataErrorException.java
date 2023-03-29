package spbpu.trkpo.library.exception;

public class DataErrorException extends RuntimeException {

    public DataErrorException() {
        super("Ошибка в неправильно заполненных данных");
    }


}
