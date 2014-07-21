package ru.zombator.zombiegis.impl;

import java.util.Objects;
import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.transfers.MapDTO;

/**
 * Метаданные карты.
 *
 * @author nvamelichev
 */
public final class MapMetaImpl implements MapMeta {
    private final String name;
    private final Pos center;
    private final double zoom;

    private final String description;

    private final String creator;
    private final String publisher;
    private final String copyright;

    public MapMetaImpl(MapDTO mapdto) {
        this.name = Objects.requireNonNull(mapdto.name, "mapdto.name");
        this.center = Objects.requireNonNull(mapdto.center, "mapdto.center");
        this.zoom = Objects.requireNonNull(mapdto.initialZoom, "mapdto.initialZoom");

        this.description = mapdto.description;
        this.creator = mapdto.creator;
        this.publisher = mapdto.publisher;
        this.copyright = mapdto.copyright;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public String getCopyright() {
        return copyright;
    }

    @Override
    public Pos getCenter() {
        return center;
    }

    @Override
    public double getZoom() {
        return zoom;
    }
}
