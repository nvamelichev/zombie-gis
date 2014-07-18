package ru.zombator.zombiegis.datasource;

import ru.zombator.zombiegis.transfers.ObjDTO.Operation;

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

    /**
     * @return операция, производимая над объектом
     */
    Operation getOperation();
}
