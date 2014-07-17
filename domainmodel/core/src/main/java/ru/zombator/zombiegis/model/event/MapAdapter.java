package ru.zombator.zombiegis.model.event;

import ru.zombator.zombiegis.model.ObjMap;

/**
 * Адаптер для {@link MapListener слушателей событий карты}.
 *
 * @author nvamelichev
 */
public abstract class MapAdapter implements MapListener {
    @Override
    public void mapClosed(ObjMap map) {
    }

    @Override
    public void mapOpened(ObjMap map) {
    }
}
