package ru.zombator.zombiegis.model;

import org.netbeans.api.annotations.common.NonNull;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

import ru.zombator.zombiegis.model.obj.Obj;

/**
 * Интерфейс карты.
 *
 * @author anv
 */
public interface ObjMap {
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
     */
    @NonNull ImmutableSet<Obj<?, ?>> objs(@NonNull Predicate<Obj<?, ?>> pred);
}
