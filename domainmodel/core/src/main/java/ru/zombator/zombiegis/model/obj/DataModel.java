package ru.zombator.zombiegis.model.obj;

import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.geo.Pos;

/**
 * Интерфейс для информационной модели объекта карты.
 *
 * @author nvamelichev
 *
 * @see BasicDataModel стандартная реализация
 */
public interface DataModel {
    /**
     * Возвращает название объекта карты.
     *
     * @return название объекта карты
     */
    @NonNull String getName();

    /**
     * Возвращает местоположение центра объекта карты.
     *
     * @return центр объекта карты
     */
    @NonNull Pos getCenter();

    /**
     * Проверяет, отображается объект на карте или нет.
     *
     * @return <code>true</code>, если объект видимый; иначе <code>false</code>
     */
    boolean isVisible();
}
