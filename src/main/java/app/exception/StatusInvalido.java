package app.exception;

public class StatusInvalido extends RuntimeException {
	public StatusInvalido (String message) {
		super(message);
	}
}
