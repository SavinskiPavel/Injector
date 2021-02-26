package testBindingNotFoundException;
import annotations.Inject;

public class NotFoundBindingImpl implements NotFoundBinding {

    private Found eventFound;

    @Inject
    public NotFoundBindingImpl(Found eventFound) {
        this.eventFound = eventFound;
    }
}
