package testUsingDefaultConstructor;

public class DefaultConstructorFoundImpl implements DefaultConstructorFound {

    private DefaultConstructor defaultConstructor;
    private ServiceOk serviceOk;

    public DefaultConstructorFoundImpl(DefaultConstructor defaultConstructor, ServiceOk serviceOk) {
        this.defaultConstructor = defaultConstructor;
        this.serviceOk = serviceOk;
    }

    public DefaultConstructorFoundImpl(DefaultConstructor defaultConstructor) {
        this.defaultConstructor = defaultConstructor;
    }

    public DefaultConstructorFoundImpl() {
    }
}
