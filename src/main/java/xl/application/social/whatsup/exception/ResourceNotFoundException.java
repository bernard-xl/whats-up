package xl.application.social.whatsup.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final String name;
    private final Object id;

    public ResourceNotFoundException(String name, Object id) {
        super(name + ":" + id + " cannot be found");
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Object getId() {
        return id;
    }
}
