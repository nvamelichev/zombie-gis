package ru.zombator.util;

import com.google.common.base.Preconditions;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.Lists;

/**
 * Вспомогательный класс для работы с набором слушателей одного и того же типа как с одним {@link #getFireable()
 * слушателем этого типа}.
 * <p>
 * Регистрация, удаление слушателей и рассылка событий потокобезопасны и никогда не вызовут
 * {@link java.util.ConcurrentModificationException ConcurrentModificationException}. Однако, если во время рассылки
 * событий был добавлен или удален слушатель, то он <em>может</em> попасть, а может и не попасть в список тех,
 * кому будут разосланы события.
 * <p>
 * Рассылка событий производится в обратном порядке (первым - слушателю, добавленному последним), аналогично
 * {@link javax.swing.event.EventListenerList EventListenerList}'у из Swing.
 *
 * @param <T> тип слушателя
 *
 * @author Fedor Bobin
 * @author nvamelichev
 *
 * @see http://fuud-java.blogspot.ru/2011/07/listeners-support-propertychangesupport.html
 */
public final class ListenerSupport<T> implements AutoCloseable {
    private final Class<T> clazz;
    private final List<T> listeners = new CopyOnWriteArrayList<>();
    private final T fireable;

    private final AtomicBoolean closed = new AtomicBoolean();

    public ListenerSupport(Class<T> clazz) {
        this.clazz = clazz;

        @SuppressWarnings("unchecked") T proxy = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new ListenerHandler<>(this));
        this.fireable = proxy;
    }

    /**
     * Добавляет слушателя.
     *
     * @param listener добавляемый слушатель
     *
     * @throws IllegalStateException
     */
    public void addListener(T listener) {
        checkState();
        listeners.add(listener);
    }

    /**
     * Удаляет слушателя.
     *
     * @param listener удаляемый слушатель
     */
    public void removeListener(T listener) {
        checkState();
        listeners.remove(listener);
    }

    /**
     * Возвращает слушатель-прокси, действующий следующим образом:
     * <ul>
     * <li>
     * метод {@link Object#equals(Object) equals()} проверяет совпадение слушателей-прокси друг с другом. Слушатели-прокси
     * равны, если они возвращены методом {@link #getFireable()} одного и того же {@link ListenerSupport}. Метод
     * {@link Object#hashCode() hashCode()} согласован с <code>equals()</code>;
     * </li>
     * <li>
     * метод {@link Object#toString() toString()} выдает информацию о {@link ListenerSupport}, возвратившем данный
     * слушатель-прокси;
     * </li>
     * <li>
     * методы, возвращающие <code>void</code>, вызываются для каждого зарегистрированного слушателя в обратном порядке
     * (последний зарегистрированный слушатель - первым). Неудачные вызовы метода протоколируются, после чего продолжается
     * вызов метода у оставшихся слушателей;
     * </li>
     * <li>
     * методы, не возвращающие <code>void</code>, вызывают {@link UnsupportedOperationException}. Если требуется их вызвать
     * и обработать результат, воспользуйтесь методом {@link #getListeners() getListeners()}.
     * </li>
     * </ul>
     *
     * @return слушатель-посредник
     *
     * @see #getListeners()
     */
    public T getFireable() {
        checkState();
        return fireable;
    }

    /**
     * Возвращает список слушателей в обратном порядке (последний добавленный первым).
     * <p>
     * Если вас не устраивает вызов <code>{@link #getFireable() getFireable().метод(...)}</code> -
     * например, необходимо вызвать метод слушателя, возвращающий не <code>void</code>,
     * используйте цикл по <code>getListeners()</code>:
     * <blockquote>
     * <pre>
     * ListenerSupport&lt;T> support = ...;
     * // ...
     * for (T listener: support.getListeners()) {
     *     try {
     *        boolean result = listener.booleanMethod();
     *        // do smth with result...
     *     } catch (...) {
     *        // handle listener failure...
     *     }
     * }
     * </pre>
     * </blockquote>
     *
     * @return слушатели в обратном порядке
     *
     * @see #getFireable()
     */
    public List<T> getListeners() {
        checkState();
        return Lists.reverse(listeners);
    }

    /**
     * @return класс слушателей, который поддерживает данный {@link ListenerSupport}
     */
    public Class<T> getListenerClass() {
        checkState();
        return clazz;
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            this.listeners.clear();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                 .append("ListenerSupport [")
                 .append("clazz: ").append(clazz).append(',').append(' ')
                 .append("listeners: ").append(listeners)
                 .append(']')
                 .toString();
    }

    private void checkState() throws IllegalStateException {
        Preconditions.checkState(!closed.get(), "ListenerSupport is closed");
    }
}
