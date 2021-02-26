import annotations.Inject;
import exceptions.BindingNotFoundException;
import exceptions.ConstructorNotFoundException;
import exceptions.TooManyBeansException;
import exceptions.TooManyConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InjectorImpl<T> implements Injector, Provider<T> {

    private static final Map<Class<?>, Provider<?>> providerMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Class<?>> classMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Class<?>> classSingletonMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Object> singletonBeanMap = new ConcurrentHashMap<>();

    public InjectorImpl() {
    }

    public  <T> Provider<T> getProvider(Class<T> type) {

        synchronized (this) {
            if (providerMap.containsKey(type)) {
                return (Provider<T>) providerMap.get(type);
            } else {
                return null;
            }
        }
    }

    public class ProviderClass implements Provider <T> {

        private final Class<?> interfaceClass;
        private final Class <?> implementationClass;


        public ProviderClass(Class<?> interfaceClass, Class<?> implementationClass) {
            this.interfaceClass = interfaceClass;
            this.implementationClass = implementationClass;
        }

        @Override
        public T getInstance()  {

            Class<?> clazzIntf = interfaceClass;
            Class<?> clazzImpl = implementationClass;

            T bean = null;
            Constructor<T> providerConstructor;
            int countAnnotation = 0;
            int countDefaultConstructor = 0;
            Constructor<?>[] constructors = clazzImpl.getDeclaredConstructors();
            List<Constructor<?>> providerConstructors = new ArrayList<>();

            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    countAnnotation++;
                    providerConstructors.add(constructor);
                }
                if (constructor.getParameterCount() == 0){
                    countDefaultConstructor++;
                }
            }
            try {
                if (countAnnotation == 1) {
                    providerConstructor = (Constructor<T>) providerConstructors.get(0);
                    Class<?>[] params = providerConstructor.getParameterTypes();
                    Object[] args = new Object[params.length];

                    for (int i = 0; i < params.length; i++) {
                        if (classMap.containsKey(params[i])) {
                            args[i] = classMap.get(params[i]).getDeclaredConstructor().newInstance();
                        } else {
                            throw new BindingNotFoundException();
                        }
                    }

                    bean = (T) init(providerConstructor, args);

                } else if (countAnnotation == 0) {
                    if (countDefaultConstructor == 0) {
                        throw new ConstructorNotFoundException();
                    } else {
                        bean = (T) clazzImpl.getDeclaredConstructor().newInstance();
                    }
                } else {
                    throw new TooManyConstructorsException();
                }
            } catch (ClassCastException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                throw new BindingNotFoundException(e);
            }

            if(bean != null && classSingletonMap.containsKey(clazzIntf) &&!singletonBeanMap.containsKey(clazzIntf)) {
                singletonBeanMap.put(clazzIntf, bean);
            } else if (bean != null && classSingletonMap.containsKey(clazzIntf) && singletonBeanMap.containsKey(clazzIntf)) {
                bean = (T) singletonBeanMap.get(clazzIntf);
            }

            return bean;
        }

        public Object init(Constructor<T> constructor, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
            return constructor.newInstance(args);
        }
    }

    @Override
    public <T> void bind (Class <T> intf, Class <? extends T> impl) {
        if (classSingletonMap.containsKey(intf) || classMap.containsKey(intf)) {
            throw new TooManyBeansException();
        }
        Provider providerClass = new ProviderClass(intf, impl);
        classMap.put(intf, impl);
        providerMap.put(intf, providerClass);
    }

    @Override
    public <T> void bindSingleton (Class<T> intf, Class<? extends T> impl) {
        if (classSingletonMap.containsKey(intf) || classMap.containsKey(intf)) {
            throw new TooManyBeansException();
        }
        Provider providerClass = new ProviderClass(intf, impl);
        classSingletonMap.put(intf, impl);
        providerMap.put(intf, providerClass);
    }

    @Override
    public T getInstance() {
        return null;
    }
}