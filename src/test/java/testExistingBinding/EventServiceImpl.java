package testExistingBinding;

import annotations.Inject;

public class EventServiceImpl implements EventService {

    private Server server;

    @Inject
    public EventServiceImpl(Server server) {
        this.server = server;
    }

    public EventServiceImpl() {
    }
}
