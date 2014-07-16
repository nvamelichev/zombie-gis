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
}
