package testScopePrototypeAndSingleton;

import annotations.Inject;

public class ScopeSingletonImpl implements ScopeSingleton {

    private ScopePrototype scopePrototype;

    @Inject
    public ScopeSingletonImpl(ScopePrototype scopePrototype) {
        this.scopePrototype = scopePrototype;
    }
}
