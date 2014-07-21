package ru.zombator.zombiegis.datasource.spi.def;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.netbeans.api.annotations.common.NonNull;
import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.def.DefObj;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.properties.Type;

/**
 * Абстрактный базовый класс для создателей объектов предметной области.
 * <p>
 * Классам-наследникам достаточно указать набор поддерживаемых ими типов объектов и
 * реализовать методы создания {@link #createDataModel(ObjData) информационной модели} и
 * {@link #createViewModel(ObjData) модели отображения} объекта.
 *
 * @author nvamelichev
 *
 * @see #createDataModel(ObjData)
 * @see #createViewModel(ObjData)
 */
public abstract class BasicObjCreator implements ObjCreator {
    private final Set<Type> types;

    /**
     * @param supported поддерживаемый тип объектов
     */
    protected BasicObjCreator(Type supported) {
        this(new Type[]{supported});
    }

    /**
     * @param supported поддерживаемые типы объектов
     *
     * @throws IllegalArgumentException <code>supported.length == 0</code>
     */
    protected BasicObjCreator(Type[] supported) {
        Preconditions.checkArgument(supported.length > 0, "number of supported types must be >= 1");

        this.types = ImmutableSet.copyOf(supported);
    }

    @Override
    public boolean canConvert(ObjData data) {
        if (!data.getProps().hasValue(TypedProp.NAME)) {
            return false;
        }

        Type type = data.getProps().get(TypedProp.TYPE);
        if (type == null) {
            return false;
        }

        for (Type supported: types) {
            if (type.isSubtypeOf(supported)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Obj<?, ?> toDomain(ObjData data) {
        return new DefObj<>(data, createDataModel(data), createViewModel(data));
    }

    /**
     * Создает информационную модель объекта.
     *
     * @param data данные объекта
     *
     * @return информационная модель
     */
    protected abstract @NonNull DataModel createDataModel(@NonNull ObjData data);

    /**
     * Создает модель отображения объекта.
     *
     * @param data данные объекта
     *
     * @return модель отображения
     */
    protected abstract @NonNull ViewModel createViewModel(@NonNull ObjData data);
}
