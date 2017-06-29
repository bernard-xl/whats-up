package xl.application.social.whatsup.exception;

public class BadArgumentException extends RuntimeException {

    private final String name;
    private final Object arg;

    public BadArgumentException(String name, Object arg) {
        super("bad argument " + name + " = " + arg);
        this.name = name;
        this.arg = arg;
    }

    public String getName() {
        return name;
    }

    public Object getArg() {
        return arg;
    }
}
