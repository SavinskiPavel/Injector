package exceptions;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(Throwable cause) {
        super(cause);
    }
}
