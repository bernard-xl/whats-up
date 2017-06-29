package xl.application.social.whatsup.exception;

public class ArgumentNotValidException extends IllegalArgumentException {

    private final String argumentName;
    private final Object badValue;

    public ArgumentNotValidException(String argumentName, Object badValue) {
        super("invalid argument supplied for " + argumentName + " (" + badValue + ")");
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
