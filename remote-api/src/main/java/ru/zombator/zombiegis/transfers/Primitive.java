package ru.zombator.zombiegis.transfers;

import java.io.Serializable;
import java.util.Objects;

import ru.zombator.zombiegis.geo.Pos;

/**
 * Тип данных, с помощью которого передается значение свойства.
 *
 * @author nvamelichev
 */
public enum Primitive {
    /**
     * Строка.
     */
    STR(String.class),
    /**
     * Целое число.
     */
    NUM(Long.class),
    /**
     * Вещественное число.
     */
    REAL(Double.class),
    /**
     * Логическое значение.
     */
    BOOL(Boolean.class),
    /**
     * Географическое положение.
     */
    POS(Pos.class);

    private final Class<? extends Serializable> clazz;

    private Primitive(Class<? extends Serializable> clazz) {
        this.clazz = Objects.requireNonNull(clazz, "clazz");
    }

    /**
     * @return тип Java, соответствующий этому серверному типу данных
     */
    public Class<? extends Serializable> getType() {
        return clazz;
    }
}
