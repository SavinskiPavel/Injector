import exceptions.BindingNotFoundException;
import exceptions.ConstructorNotFoundException;
import exceptions.TooManyConstructorsException;
import org.junit.Assert;
import org.junit.Test;
import testBindingNotFoundException.NotFoundBinding;
import testBindingNotFoundException.NotFoundBindingImpl;
import testCallProviderWithoutClassBinding.CallProviderWithoutClassBinding;
import testConstructorNotFoundException.*;
import testExistingBinding.*;
import testScopePrototypeAndSingleton.*;
import testTooManyConstructorsException.TooManyConstructors;
import testTooManyConstructorsException.TooManyConstructorsImpl;
import testUsingDefaultConstructor.DefaultConstructor;
import testUsingDefaultConstructor.DefaultConstructorFound;
import testUsingDefaultConstructor.DefaultConstructorFoundImpl;
import testUsingDefaultConstructor.DefaultConstructorImpl;

public class TestInjectorImpl {

    public TestInjectorImpl() {
    }

    @Test()
    public void testExistingBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class);
        injector.bind(Server.class, LocaleServer.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        Assert.assertNotNull(daoProvider);
        Assert.assertNotNull(daoProvider.getInstance());
        Assert.assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
        Assert.assertNotNull(serviceProvider);
        Assert.assertNotNull(serviceProvider.getInstance());
        Assert.assertSame(EventServiceImpl.class, serviceProvider.getInstance().getClass());
    }

    @Test(expected = ConstructorNotFoundException.class)
    public void testConstructorNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(ConstructorNotFound.class, ConstructorNotFoundImpl.class);
        Provider<ConstructorNotFound> daoProvider = injector.getProvider(ConstructorNotFound.class);
        Assert.assertNull(daoProvider.getInstance());
     }

    @Test
    public void testUsingDefaultConstructor() {
        Injector injector = new InjectorImpl();
        injector.bind(DefaultConstructorFound.class, DefaultConstructorFoundImpl.class);
        injector.bind(DefaultConstructor.class, DefaultConstructorImpl.class);
        Provider<DefaultConstructorFound> daoProvider = injector.getProvider(DefaultConstructorFound.class);
        Assert.assertNotNull(daoProvider);
        Assert.assertNotNull(daoProvider.getInstance());
        Assert.assertSame(DefaultConstructorFoundImpl.class, daoProvider.getInstance().getClass());
    }

    @Test(expected = TooManyConstructorsException.class)
    public void testTooManyConstructorsExceptions() {
        Injector injector = new InjectorImpl();
        injector.bind(TooManyConstructors.class, TooManyConstructorsImpl.class);
        Provider<TooManyConstructors> daoProvider = injector.getProvider(TooManyConstructors.class);
        Assert.assertNull(daoProvider.getInstance());
    }

    @Test(expected = BindingNotFoundException.class)
    public void testBindingNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(NotFoundBinding.class, NotFoundBindingImpl.class);
        Provider<NotFoundBinding> daoProvider = injector.getProvider(NotFoundBinding.class);
        daoProvider.getInstance();
    }

    @Test
    public void testCallProviderWithoutClassBinding() {
        Injector injector = new InjectorImpl();
        Provider <CallProviderWithoutClassBinding> daoProvider = injector.getProvider(CallProviderWithoutClassBinding.class);
        Assert.assertNull(daoProvider);
    }

    @Test
    public void testScopePrototypeAndSingleton() {

        Injector injector = new InjectorImpl();
        injector.bind(Alpha.class, AlphaImpl.class);
        injector.bind(ScopePrototype.class, ScopePrototypeImpl.class);
        injector.bindSingleton(ScopeSingleton.class, ScopeSingletonImpl.class);

        Provider<ScopePrototype> prototypeProviderOne = injector.getProvider(ScopePrototype.class);
        Provider<ScopePrototype> prototypeProviderTwo = injector.getProvider(ScopePrototype.class);
        Provider<ScopePrototype> prototypeProviderThree = injector.getProvider(ScopePrototype.class);

        Assert.assertNotNull(prototypeProviderOne);
        Assert.assertNotNull(prototypeProviderTwo);
        Assert.assertNotNull(prototypeProviderThree);

        Assert.assertNotNull(prototypeProviderOne.getInstance());
        Assert.assertNotNull(prototypeProviderTwo.getInstance());
        Assert.assertNotNull(prototypeProviderThree.getInstance());

        Assert.assertSame(ScopePrototypeImpl.class, prototypeProviderOne.getInstance().getClass());
        Assert.assertSame(ScopePrototypeImpl.class, prototypeProviderTwo.getInstance().getClass());
        Assert.assertSame(ScopePrototypeImpl.class, prototypeProviderThree.getInstance().getClass());

        Assert.assertNotEquals(prototypeProviderOne.getInstance(), prototypeProviderTwo.getInstance());
        Assert.assertNotEquals(prototypeProviderOne.getInstance(), prototypeProviderThree.getInstance());
        Assert.assertNotEquals(prototypeProviderTwo.getInstance(), prototypeProviderThree.getInstance());


        Provider<ScopeSingleton> singletonProviderOne = injector.getProvider(ScopeSingleton.class);
        Provider<ScopeSingleton> singletonProviderTwo = injector.getProvider(ScopeSingleton.class);
        Provider<ScopeSingleton> singletonProviderThree = injector.getProvider(ScopeSingleton.class);

        Assert.assertNotNull(singletonProviderOne);
        Assert.assertNotNull(singletonProviderTwo);
        Assert.assertNotNull(singletonProviderThree);

        Assert.assertNotNull(singletonProviderOne.getInstance());
        Assert.assertNotNull(singletonProviderTwo.getInstance());
        Assert.assertNotNull(singletonProviderThree.getInstance());

        Assert.assertSame(ScopeSingletonImpl.class, singletonProviderOne.getInstance().getClass());
        Assert.assertSame(ScopeSingletonImpl.class, singletonProviderTwo.getInstance().getClass());
        Assert.assertSame(ScopeSingletonImpl.class, singletonProviderThree.getInstance().getClass());

        Assert.assertEquals(singletonProviderOne.getInstance(), singletonProviderTwo.getInstance());
        Assert.assertEquals(singletonProviderOne.getInstance(), singletonProviderThree.getInstance());
        Assert.assertEquals(singletonProviderTwo.getInstance(), singletonProviderThree.getInstance());
    }
}
