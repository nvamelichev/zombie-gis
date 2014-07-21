package ru.zombator.zombiegis.model;

import org.netbeans.api.annotations.common.NonNull;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.model.event.MapListener;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.event.ObjListener;

/**
 * Интерфейс карты.
 * <p>
 * Карта может находиться в трех состояниях:
 * <ul>
 * <li>
 * <em>не открыта</em> (<code>!{@link #isOpen() isOpen()} && !{@link #isClosed() isClosed()}</code>):
 * можно делать что угодно, кроме {@link #objs(Predicate) получения объектов карты};
 * </li>
 * <li>
 * <em>открыта</em> (<code>{@link #isOpen() isOpen()} && !{@link #isClosed() isClosed()}</code>):
 * можно делать что угодно;
 * </li>
 * <li>
 * <em>закрыта</em> (<code>!{@link #isOpen() isOpen()} && {@link #isClosed() isClosed()}</code>):
 * можно вызвать только {@link #isClosed() isClosed()}, {@link #isOpen() isOpen()} и {@link #close() close()}.
 * </ul>
 *
 * @author anv
 */
public interface ObjMap extends AutoCloseable {
    /**
     * Возвращает идентификатор карты.
     *
     * @return уникальный идентификатор карты
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    long getId();

    /**
     * Возвращает название карты. Вызов аналогичен вызову
     * <code>{@link #getMeta() getMeta()}.{@link MapMeta#getName() getName()}</code>.
     *
     * @return название карты
     */
    String getName();

    /**
     * Возвращает метаданные карты.
     *
     * @return метаданные карты
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    @NonNull MapMeta getMeta();

    /**
     * Возвращает "мгновенный снимок" текущего состояния карты.
     * <p>
     * Является краткой формой вызова
     * <code>
     * {@link #objs(Predicate) objs}({@link ru.zombator.zombiegis.model.obj.util.Objs#all() all()}).
     * </code>
     *
     * @return все объекты карты на данный момент
     *
     * @throws IllegalStateException <code>!{@link #isOpen() isOpen()}</code>
     */
    @NonNull ImmutableSet<Obj<?, ?>> objs();

    /**
     * Возвращает "мгновенный снимок", содержащий только те объекты, которые удовлетворяют
     * указанному условию.
     *
     * @param pred условие фильтрации объектов карты
     *
     * @return все объекты карты, удовлетворяющие условию в данный момент
     *
     * @see ru.zombator.zombiegis.model.obj.util.Objs Objs
     *
     * @throws IllegalStateException <code>!{@link #isOpen() isOpen()}</code>
     */
    @NonNull ImmutableSet<Obj<?, ?>> objs(@NonNull Predicate<Obj<?, ?>> pred);

    /**
     * Открывает карту - начинает загрузку объектов карты и подписывается на события
     * изменения карты. Если карта уже открыта, ничего не делает.
     *
     * @throws GISException не удалось начать загрузку объектов карты
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     *
     * @see #isOpen()
     * @see #isClosed()
     */
    void open() throws GISException;

    /**
     * Проверяет, открыта ли сейчас карта.
     *
     * @return <code>true</code>, если карта открыта; иначе <code>false</code>
     *
     * @see #open()
     * @see #isClosed()
     */
    boolean isOpen();

    /**
     * Закрывает карту и отсоединяет от нее всех слушателей. Если карта уже закрыта, ничего не делает.
     *
     * @see #isClosed()
     * @see #isOpen()
     */
    @Override
    void close();

    /**
     * Проверяет, закрыта ли карта. Любые операции над закрытой картой вызывают {@link IllegalStateException},
     * кроме {@link #isClosed()}, {@link #isOpen()} и {@link #close()}.
     *
     * @return <code>true</code>, если карта закрыта; иначе <code>false</code>
     *
     * @see #close()
     */
    boolean isClosed();

    /**
     * Добавляет слушателя событий карты (открыта/закрыта).
     *
     * @param listener добавляемый слушатель
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    void addListener(MapListener listener);
    /**
     * Удаляет слушателя событий карты (открыта/закрыта).
     *
     * @param listener удаляемый слушатель
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    void removeListener(MapListener listener);

    /**
     * Добавляет слушателя изменений объектов карты. Если карта {@link #isOpen()}
     *
     * @param listener добавляемый слушатель
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    void addObjListener(ObjListener listener);
    /**
     * Удаляет слушателя изменений объектов карты.
     *
     * @param listener удаляемый слушатель
     *
     * @throws IllegalStateException {@link #isClosed() isClosed()}
     */
    void removeObjListener(ObjListener listener);
}
