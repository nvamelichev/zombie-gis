package ru.zombator.zombiegis.impl;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import com.google.common.collect.ImmutableMap;

import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.Maps;
import ru.zombator.zombiegis.NoSuchMapException;
import ru.zombator.zombiegis.interfaces.MapListService;
import ru.zombator.zombiegis.interfaces.RemoteGISException;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.transfers.MapDTO;

/**
 * Реализация {@link Maps сервиса по работе с картами}.
 *
 * @author anv
 */
@ServiceProvider(service = Maps.class)
public final class MapsImpl implements Maps {

    private static final Logger LOG = Logger.getLogger(MapsImpl.class.getName());

    @Override
    public Map<Long, MapMeta> getList() throws GISException {
        ImmutableMap.Builder<Long, MapMeta> maps = ImmutableMap.builder();

        try {
            for (MapDTO mapdto : mapListService().getMaps()) {
                try {
                    maps.put(mapdto.id, new MapMetaImpl(mapdto));
                } catch (NullPointerException e) {
                    LOG.log(malformedDTO(mapdto, e));
                }
            }

            return maps.build();
        } catch (RemoteGISException e) {
            throw new GISException(e);
        }
    }

    private static LogRecord malformedDTO(MapDTO mapdto, Exception e) {
        LogRecord rec = new LogRecord(Level.INFO, "got malformed MapDTO (id: {0}); ignored");
        rec.setParameters(new Object[]{mapdto.id});
        rec.setThrown(e);
        return rec;
    }

    @Override
    public ObjMap newMap(long mapId) throws GISException {
        try {
            MapDTO mapdto = mapListService().getMap(mapId);
            if (mapdto == null) {
                throw new NoSuchMapException(mapId);
            }

            return new ObjMapImpl(mapdto);
        } catch (RemoteGISException e) {
            throw new GISException("remote error", e);
        } catch (NullPointerException e) {
            throw new GISException("got malformed MapDTO", e);
        }
    }

    private static MapListService mapListService() {
        return Lookup.getDefault().lookup(MapListService.class);
    }
}
