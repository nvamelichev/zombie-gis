package ru.zombator.zombiegis.datasource.impl;

import static ru.zombator.zombiegis.datasource.TypedProp.TYPE;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;

import com.google.common.collect.ImmutableList;

import ru.zombator.zombiegis.datasource.DTOConverter;
import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.ObjFactory;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.properties.Type;
import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Стандартная реализация {@link ObjFactory фабрики объектов предметной области}.
 *
 * @author nvamelichev
 */
@ServiceProvider(service = ObjFactory.class)
public final class ObjFactoryImpl implements ObjFactory {
    private static final Logger LOG = Logger.getLogger(ObjFactoryImpl.class.getName());

    @Override
    public Collection<Obj<?, ?>> toDomain(Collection<ObjDTO> dtos) {
        ImmutableList.Builder<Obj<?, ?>> objs = ImmutableList.builder();

        DTOConverter dtoConv = Lookup.getDefault().lookup(DTOConverter.class);
        for (ObjData data: dtoConv.toObjData(dtos)) {
            Type type = data.getProps().get(TYPE);
            if (type == null) {
                LOG.log(Level.WARNING,
                        "received ObjData (id: {0}, timestamp: {1}) without value for Prop.TYPE; ignoring",
                        new Object[]{data.getId(), data.getTimestamp()});
                continue;
            }

            ObjCreator creator = getCreator(data, type);
            if (creator == null) {
                LOG.log(Level.WARNING,
                        "could not find creator for ObjData (id: {0}, type: {1}, timestamp: {2}; ignoring",
                        new Object[]{data.getId(), type, data.getTimestamp()});
                continue;
            }

            objs.add(creator.toDomain(data));
        }

        return objs.build();
    }

    private ObjCreator getCreator(ObjData data, Type type) {
        for (ObjCreator creator: Lookups.forPath(ObjCreator.PATH + "/" + type.name()).lookupAll(ObjCreator.class)) {
            if (creator.canConvert(data)) {
                return creator;
            }
        }

        for (Type parent: type.getParents()) {
            ObjCreator creator = getCreator(data, parent);
            if (creator != null) {
                return creator;
            }
        }

        return null;
    }
}
