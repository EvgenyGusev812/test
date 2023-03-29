package spbpu.trkpo.library.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
        super("Клиент не найден");
    }

}
