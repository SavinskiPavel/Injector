package testConstructorNotFoundException;

import annotations.Inject;

public class ConstructorNotFoundImpl implements ConstructorNotFound {

    private NotDefaultConstructor notDefaultConstructor;
    private Service service;


    public ConstructorNotFoundImpl(NotDefaultConstructor notDefaultConstructor, Service service) {
        this.notDefaultConstructor = notDefaultConstructor;
        this.service = service;
    }

    public ConstructorNotFoundImpl(NotDefaultConstructor notDefaultConstructor) {
        this.notDefaultConstructor = notDefaultConstructor;
    }

}
