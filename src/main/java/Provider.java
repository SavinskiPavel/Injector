import exceptions.ConstructorNotFoundException;

import java.lang.reflect.InvocationTargetException;

public interface Provider<T> {
    T getInstance();
}
