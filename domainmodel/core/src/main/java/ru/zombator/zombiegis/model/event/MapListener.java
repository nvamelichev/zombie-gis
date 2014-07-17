package ru.zombator.zombiegis.model.event;

import ru.zombator.zombiegis.model.ObjMap;

/**
 *
 * @author nvamelichev
 */
public interface MapListener {
    void mapOpened(ObjMap map);
    void mapClosed(ObjMap map);
}
