package testScopePrototypeAndSingleton;

public class ScopePrototypeImpl implements ScopePrototype {

    private Alpha alpha;


    public ScopePrototypeImpl(Alpha alpha) {
        this.alpha = alpha;
    }

    public ScopePrototypeImpl() {
    }
}
