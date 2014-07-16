package ru.zombator.zombiegis.model;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.geo.Pos;

/**
 * Метаданные карты.
 *
 * @author nvamelichev
 */
public interface MapMeta {
    /**
     * @return название карты
     */
    @NonNull String getName();
    /**
     * @return описание карты (необязательно)
     */
    @CheckForNull String getDescription();
    /**
     * @return создатель карты
     */
    @CheckForNull String getCreator();
    /**
     * @return издатель карты
     */
    @CheckForNull String getPublisher();
    /**
     * @return информация об авторских правах
     */
    @CheckForNull String getCopyright();

    /**
     * @return центр карты
     */
    @NonNull Pos getCenter();
    /**
     * @return масштаб карты в единицах OpenMap
     */
    double getZoom();
}
