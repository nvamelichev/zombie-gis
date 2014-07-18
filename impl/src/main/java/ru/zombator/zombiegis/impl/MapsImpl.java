package ru.zombator.zombiegis.impl;

import java.util.Map;

import org.openide.util.lookup.ServiceProvider;

import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.Maps;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;

/**
 * Реализация {@link Maps сервиса по работе с картами}.
 *
 * @author anv
 */
@ServiceProvider(service = Maps.class)
public final class MapsImpl implements Maps {

    @Override
    public Map<Long, MapMeta> getList() throws GISException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObjMap newMap(long mapId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
