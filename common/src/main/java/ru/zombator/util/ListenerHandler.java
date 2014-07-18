package ru.zombator.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * InvocationHandler прокси-слушателя, генерируемого {@link ListenerSupport} для рассылки сообщений
 * сразу многим слушателям.
 *
 * @author nvamelichev
 */
/*package*/ final class ListenerHandler<T> implements InvocationHandler {
    private static final Logger LOG = Logger.getLogger(ListenerHandler.class.getName());

    private static final Method METHOD_EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method METHOD_HASH_CODE = getMethod(Object.class, "hashCode");
    private static final Method METHOD_TO_STRING = getMethod(Object.class, "toString");

    private final ListenerSupport<T> support;

    public ListenerHandler(ListenerSupport<T> support) {
        this.support = Objects.requireNonNull(support, "support");
    }

    private static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException ignore) {
            return null;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object retVal = getReturnValue(method, args);
        if (retVal == null) {
            // вызываем метод у каждого слушателя
            invokeListeners(method, args);
        }
        return retVal;
    }

    private Object getReturnValue(Method method, Object[] args) {
        if (METHOD_EQUALS.equals(method)) {
            // boolean Object.equals(Object)
            Object o = args[0];
            return o != null && Proxy.isProxyClass(o.getClass()) && this.equals(Proxy.getInvocationHandler(o));
        } else if (METHOD_HASH_CODE.equals(method)) {
            // int Object.hashCode()
            return this.hashCode();
        } else if (METHOD_TO_STRING.equals(method)) {
            // String Object.toString()
            return this.toString();
        } else {
            Class<?> retType = method.getReturnType();
            if (retType != void.class) {
                throw new UnsupportedOperationException("invocation of method " + method + " not supported, as it doesn't return void.");
            }
            // void method(args)
            return null;
        }
    }

    private void invokeListeners(Method method, Object[] args) {
        // Вызов методов всех слушателей подряд, в обратном порядке (аналогично
        // EventListenerList в Swing'е).
        for (T listener : support.getListeners()) {
            try {
                method.invoke(listener, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                LogRecord rec = new LogRecord(Level.INFO, "[{0}] error during invocation of method {1} (args: {2})");
                rec.setThrown(e);
                rec.setParameters(new Object[]{support.getListenerClass(), method, Arrays.toString(args)});
                LOG.log(rec);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ListenerHandler)) {
            return false;
        }
        ListenerHandler<?> other = (ListenerHandler<?>) o;
        return support.equals(other.support);
    }

    @Override
    public int hashCode() {
        return support.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ListenerHandler [")
                .append("support: ")
                .append(support)
                .append(']')
                .toString();
    }
}
