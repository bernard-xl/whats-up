package xl.application.social.whatsup.exception;

public class ArgumentNotValidException extends IllegalArgumentException {

    private final String argumentName;
    private final Object badValue;

    public ArgumentNotValidException(String argumentName, Object badValue) {
        this(argumentName, badValue, null);
    }

    public ArgumentNotValidException(String argumentName, Object badValue, Throwable cause) {
        super("invalid argument supplied for " + argumentName + " (" + badValue + ")", cause);
        this.argumentName = argumentName;
        this.badValue = badValue;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public Object getBadValue() {
        return badValue;
    }
}
