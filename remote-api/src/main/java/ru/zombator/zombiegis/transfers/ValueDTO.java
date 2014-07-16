package ru.zombator.zombiegis.transfers;

import java.io.Serializable;

/**
 * DTO для передачи значений свойства объекта карты.
 *
 * @author nvamelichev
 *
 * @see GisObjDTO
 */
public final class ValueDTO implements Serializable {
    /**
     * Свойство объекта карты.
     */
    public Prop prop;
    /**
     * Значения свойства.
     */
    public Serializable[] values;
}
