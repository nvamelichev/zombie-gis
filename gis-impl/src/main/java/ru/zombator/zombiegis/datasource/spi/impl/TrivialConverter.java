package ru.zombator.zombiegis.datasource.spi.impl;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.Collection;

import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.spi.ValueConverter;

/**
 * Конвертер для значений, клиентский тип данных которых совпадает с типом "сырых" данных, полученных от сервера.
 *
 * @author nvamelichev
 */
@ValueConverter.Registration
public final class TrivialConverter implements ValueConverter<Serializable, Serializable> {
    @Override
    public boolean canConvert(TypedProp<?> prop) {
        return !prop.isEnum() &&
                prop.type().equals(prop.primitive().getType());
    }

    @Override
    public Collection<Serializable> toClient(TypedProp<Serializable> prop, Collection<Serializable> serverValues) {
        return ImmutableList.copyOf(serverValues);
    }
}
