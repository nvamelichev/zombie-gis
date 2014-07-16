package ru.zombator.zombiegis.transfers;

import java.io.Serializable;

import ru.zombator.zombiegis.geo.Pos;

/**
 * DTO для передачи информации о карте.
 *
 * @author nvamelichev
 */
public final class MapDTO implements Serializable {
    /**
     * Уникальный идентификатор карты.
     */
    public Long id;
    /**
     * Название карты.
     */
    public String name;
    /**
     * Центр карты.
     */
    public Pos center;
    /**
     * Изначальный масштаб карты в единицах OpenMap.
     */
    public Double initialZoom;

    /**
     * Описание карты.
     */
    public String description;
    /**
     * Создатель карты.
     */
    public String creator;
    /**
     * Издатель карты.
     */
    public String publisher;
    /**
     * Информация об авторских правах.
     */
    public String copyright;
}
