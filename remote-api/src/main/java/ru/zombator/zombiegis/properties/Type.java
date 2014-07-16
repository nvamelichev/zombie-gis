package ru.zombator.zombiegis.properties;

/**
 * Значение свойства "тип объекта карты".
 *
 * @author nvamelichev
 */
public enum Type {
    /**
     * Полигон или полилиния.
     */
    POLY,
    /**
     * Дуга эллипса, сегмент или сектор.
     */
    ARC,
    /**
     * Подпись.
     */
    LABEL,
    /**
     * Пометка места на карте "иголкой".
     */
    PIN,
    /**
     * Корабль.
     */
    SHIP,
}
