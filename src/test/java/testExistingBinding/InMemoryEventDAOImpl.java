package testExistingBinding;


import annotations.Inject;

public class InMemoryEventDAOImpl implements EventDAO {

    private EventService eventService;
    private Server server;

    @Inject
    public InMemoryEventDAOImpl(EventService eventService, Server server) {
        this.eventService = eventService;
        this.server = server;
    }
}
