package ru.zombator.zombiegis.datasource.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

import ru.zombator.zombiegis.datasource.DTOConverter;
import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.spi.ValueConverter;
import ru.zombator.zombiegis.transfers.ObjDTO;
import ru.zombator.zombiegis.transfers.ObjDTO.Operation;
import ru.zombator.zombiegis.transfers.ValueDTO;

/**
 * Стандартная реализация {@link DTOConverter}.
 *
 * @author nvamelichev
 */
@ServiceProvider(service = DTOConverter.class)
public final class DTOConverterImpl implements DTOConverter {
    private static final Logger LOG = Logger.getLogger(DTOConverterImpl.class.getName());

    @Override
    public Collection<ObjData> toObjData(Collection<ObjDTO> dtos) {
        ImmutableList.Builder<ObjData> datas = ImmutableList.builder();

        for (ObjDTO dto: dtos) {
            ObjData objData;
            try {
                objData = new DefObjData(dto);
            } catch (Exception e) {
                LOG.log(Level.INFO, "error converting ObjDTO to ObjData", e);
                continue;
            }
            datas.add(objData);
        }

        return datas.build();
    }

    private static final class DefObjData implements ObjData {
        private final long id;
        private final long timestamp;
        private final PropValues props;

        public DefObjData(ObjDTO odto) {
            this.id = odto.id;
            this.timestamp = odto.timestamp;
            this.props = new DefProps(odto.values);
        }

        @Override
        public long getId() {
            return id;
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public PropValues getProps() {
            return props;
        }
    }

    private static final class DefProps implements PropValues {
        private final Multimap<TypedProp<?>, Object> values;

        @SuppressWarnings("unchecked")
        public DefProps(ValueDTO[] vdtos) {
            Collection<? extends ValueConverter> converters = Lookups
                    .forPath(ValueConverter.PATH)
                    .lookupAll(ValueConverter.class);

            ImmutableMultimap.Builder<TypedProp<?>, Object> bldr = ImmutableMultimap.builder();
            for (ValueDTO vdto: vdtos) {
                TypedProp<?> prop = TypedProp.valueOf(vdto.prop.name());

                for (ValueConverter conv: converters) {
                    if (conv.canConvert(prop)) {
                        bldr.putAll(prop, conv.toClient(prop, Arrays.asList(vdto.values)));
                    }
                }
            }
            this.values = bldr.build();
        }

        @Override
        public <T extends Serializable> T get(TypedProp<T> prop) {
            return get0(prop, null);
        }

        @Override
        public <T extends Serializable> T get(TypedProp<T> prop, T def) {
            return get0(prop, Objects.requireNonNull(def, "def"));
        }

        private <T extends Serializable> T get0(TypedProp<T> prop, T def) {
            return prop.type().cast(Iterables.getFirst(values.get(prop), def));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Serializable> List<T> getAll(TypedProp<T> prop) {
            return (List<T>) (List<?>) values.get(prop);
        }

        @Override
        public boolean hasValue(TypedProp<?> prop) {
            return values.containsKey(prop);
        }
    }
}
