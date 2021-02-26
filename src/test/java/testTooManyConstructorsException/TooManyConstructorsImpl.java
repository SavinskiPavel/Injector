package testTooManyConstructorsException;
import annotations.Inject;


public class TooManyConstructorsImpl implements TooManyConstructors {

    private ManyConstructors eventManyConstructors;

    @Inject
    public TooManyConstructorsImpl(ManyConstructors eventManyConstructors) {
        this.eventManyConstructors = eventManyConstructors;
    }

    @Inject
    public TooManyConstructorsImpl() {
    }
}
