package ru.zombator.zombiegis.services;

import org.openide.util.lookup.ServiceProvider;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.interfaces.MapListService;
import ru.zombator.zombiegis.interfaces.RemoteGISException;
import ru.zombator.zombiegis.transfers.MapDTO;

/**
 *
 * @author nvamelichev
 */
@ServiceProvider(service = MapListService.class)
public final class MapListServiceImpl implements MapListService {
    @Override
    public MapDTO[] getMaps() throws RemoteGISException {
        return new MapDTO[0];
    }

    @Override
    public MapDTO getMap(long id) throws RemoteGISException {
        return null;
    }

    @Override
    public MapDTO createMap(String name, Pos center, double initialZoom) throws RemoteGISException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean modifyMap(MapDTO map) throws RemoteGISException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteMap(long id) throws RemoteGISException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
