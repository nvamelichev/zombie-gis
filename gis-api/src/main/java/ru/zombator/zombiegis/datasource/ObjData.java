package ru.zombator.zombiegis.datasource;

/**
 * Данные объекта карты.
 *
 * @author nvamelichev
 */
public interface ObjData {
    /**
     * @return идентификатор объекта
     */
    long getId();

    /**
     * @return штамп времени
     */
    long getTimestamp();

    /**
     * @return значения свойств объекта
     */
    PropValues getProps();
}
