package ru.zombator.zombiegis.datasource;

import java.io.Serializable;
import java.util.List;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;

/**
 * Значения свойств объекта.
 *
 * @author nvamelichev
 */
public interface PropValues {
    /**
     * Возвращает первое значение свойства.
     *
     * @param <T> тип значений свойства
     *
     * @param prop свойство
     *
     * @return первое значение свойства или <code>null</code>, если у объекта нет значений данного свойства
     */
    @CheckForNull <T extends Serializable> T get(@NonNull TypedProp<T> prop);

    /**
     * Возвращает первое значение свойства.
     *
     * @param <T> тип значений свойства
     *
     * @param prop свойство
     * @param def значение по умолчанию
     *
     * @return первое значение свойства или <code>def</code>, если у объекта нет значений данного свойства
     */
    @NonNull <T extends Serializable> T get(@NonNull TypedProp<T> prop, @NonNull T def);

    /**
     * Возвращает все значения указанного свойства объекта.
     *
     * @param <T> тип значений свойства
     *
     * @param prop свойство
     *
     * @return список значений свойства; если у объекта нет значений свойства - пустой список
     */
    @NonNull <T extends Serializable> List<T> getAll(@NonNull TypedProp<T> prop);

    /**
     * Проверяет, есть ли у объекта значения указанного свойства.
     *
     * @param prop свойство
     *
     * @return <code>true</code>, если у объекта есть значения свойства; иначе <code>false</code>
     */
    boolean hasValue(@NonNull TypedProp<?> prop);
}
