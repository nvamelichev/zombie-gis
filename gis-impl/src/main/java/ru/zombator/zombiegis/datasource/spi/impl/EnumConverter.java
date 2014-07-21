package ru.zombator.zombiegis.datasource.spi.impl;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.ImmutableList;

import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.spi.ValueConverter;

/**
 * Конвертер из серверного представления перечислений в клиентское.
 *
 * @param <E> тип перечисления. <br>
 * Этот параметр используется только чтобы помочь системе типов.
 * На самом деле всегда используется единственный экземпляр EnumConverter, создаваемый при первом
 * обращении к <code>Lookups.forPaths({@link ValueConverter#PATH ValueConverter.PATH})</code>.
 *
 * @author nvamelichev
 */
@ValueConverter.Registration
public final class EnumConverter<E extends Enum<E>> implements ValueConverter<String, E> {
    private static final Logger LOG = Logger.getLogger(EnumConverter.class.getName());

    @Override
    public boolean canConvert(TypedProp<?> prop) {
        return prop.isEnum();
    }

    @Override
    public Collection<E> toClient(TypedProp<E> prop, Collection<String> serverValues) {
        ImmutableList.Builder<E> results = ImmutableList.builder();

        for (String name: serverValues) {
            try {
                results.add(Enum.valueOf(prop.type(), name));
            } catch (IllegalArgumentException | NullPointerException e) {
                LOG.log(Level.WARNING, "could not get enum constant; skipped", e);
            }
        }

        return results.build();
    }
}
