package ru.zombator.zombiegis.model.obj;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.properties.Type;

/**
 * Объект карты.
 *
 * @param <D> тип информационной модели
 * @param <V> тип модели отображения
 *
 * @author nvamelichev
 */
public interface Obj<D extends DataModel, V extends ViewModel> {
    /**
     * @return уникальный идентификатор объекта карты;
     *         <code>null</code>, если объект еще не сохранен на сервере
     */
    @CheckForNull Long getId();

    /**
     * Возвращает название объекта карты. Вызов аналогичен вызову
     * <code>{@link #getData() getData()}.{@link DataModel#getName() getName()}</code>.
     *
     * @return название объекта карты
     */
    @NonNull String getName();

    /**
     * Возвращает тип объекта карты.
     *
     * @return
     */
    @NonNull Type getType();

    /**
     * Проверяет, является ли объект подвижным (имеет ли он курс и скорость).
     *
     * @return <code>true</code>, если объект подвижный; иначе <code>false</code>
     *
     * @see MobDataModel
     */
    boolean isMobile();

    /**
     * Проверяет, отображается объект на карте или нет. Вызов аналогичен вызову
     * <code>{@link #getData() getData()}.{@link DataModel#isVisible() isVisible()}</code>.
     *
     * @return <code>true</code>, если объект видимый; иначе <code>false</code>
     */
    boolean isVisible();

    /**
     * @return информационная модель
     */
    @NonNull D getData();

    /**
     * @return модель отображения
     */
    @NonNull V getView();

    /**
     * @return карта, которой принадлежит объект
     */
    @NonNull ObjMap getMap();
}
