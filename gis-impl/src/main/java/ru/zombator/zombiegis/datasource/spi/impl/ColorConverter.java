package ru.zombator.zombiegis.datasource.spi.impl;

import com.google.common.collect.ImmutableList;
import java.awt.Color;
import java.util.Collection;
import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.spi.ValueConverter;

/**
 * Конвертер Long ARGB --> Color.
 *
 * @author nvamelichev
 */
@ValueConverter.Registration
public final class ColorConverter implements ValueConverter<Long, Color> {
    @Override
    public boolean canConvert(TypedProp<?> prop) {
        return Color.class.equals(prop.type());
    }

    @Override
    public Collection<Color> toClient(TypedProp<Color> prop, Collection<Long> serverValues) {
        ImmutableList.Builder<Color> results = ImmutableList.builder();
        for (Long argb: serverValues) {
            results.add(new Color(argb.intValue()));
        }
        return results.build();
    }
}
