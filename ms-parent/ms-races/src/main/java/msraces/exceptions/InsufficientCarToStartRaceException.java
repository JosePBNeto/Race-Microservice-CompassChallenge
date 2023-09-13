package msraces.exceptions;

public class InsufficientCarToStartRaceException extends RuntimeException {
    public InsufficientCarToStartRaceException(String message) {
        super(message);
    }
}
