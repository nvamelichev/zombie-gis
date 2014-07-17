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
 *
 * @author anv
 */
public interface ObjMap extends AutoCloseable {
    /**
     * Возвращает идентификатор карты.
     *
     * @return уникальный идентификатор карты
     */
    long getId();

    /**
     * Возвращает метаданные карты.
     *
     * @return метаданные карты
     */
    @NonNull MapMeta getMeta();

    /**
     * Возвращает "мгновенный снимок" текущего состояния карты.
     *
     * @return все объекты карты на данный момент
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
     */
    @NonNull ImmutableSet<Obj<?, ?>> objs(@NonNull Predicate<Obj<?, ?>> pred);

    /**
     * Открывает карту - начинает загрузку объектов карты и подписывается на события
     * изменения карты.
     *
     * @throws GISException не удалось начать загрузку объектов карты
     *
     * @see #isOpen()
     */
    void open() throws GISException;

    /**
     * Проверяет, открыта ли сейчас карта.
     *
     * @return <code>true</code>, если карта открыта; иначе <code>false</code>
     *
     * @see #open()
     */
    boolean isOpen();

    /**
     * Закрывает карту и отсоединяет от нее всех слушателей.
     */
    @Override
    void close();

    /**
     * Добавляет слушателя событий карты (открыта/закрыта).
     *
     * @param listener добавляемый слушатель
     */
    void addListener(MapListener listener);
    /**
     * Удаляет слушателя событий карты (открыта/закрыта).
     *
     * @param listener удаляемый слушатель
     */
    void removeListener(MapListener listener);

    /**
     * Добавляет слушателя изменений объектов карты. Если карта {@link #isOpen()}
     *
     * @param listener добавляемый слушатель
     */
    void addObjListener(ObjListener listener);
    /**
     * Удаляет слушателя изменений объектов карты.
     *
     * @param listener удаляемый слушатель
     */
    void removeObjListener(ObjListener listener);
}
