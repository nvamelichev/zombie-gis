package ru.zombator.zombiegis.transfers;

import java.io.Serializable;

/**
 * Data Transfer Object для передачи информации об объекте карты.
 *
 * @author nvamelichev
 */
public final class ObjDTO implements Serializable {
    /**
     * Уникальный идентификатор объекта карты.
     */
    public Long id;
    /**
     * Значения свойств объекта карты.
     */
    public ValueDTO[] values;
    /**
     * Штамп времени.
     */
    public Long timestamp;
    /**
     * Операция, информация о которой передана в DTO.
     */
    public Operation operation;

    /**
     * Операция над объектом.
     */
    public static enum Operation {
        /**
         * Объект был создан. В массиве <code>values</code> переданы значения всех свойств объекта.
         */
        CREATE,
        /**
         * Объект был изменен. В массиве <code>values</code> переданы все добавленные и измененные значения свойств объекта.
         */
        UPDATE,
        /**
         * Объект был удален. Массив <code>values</code> пуст.
         */
        DELETE
    }
}
