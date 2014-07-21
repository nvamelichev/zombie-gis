package ru.zombator.zombiegis.datasource.spi.def;

import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.datasource.def.DefObj;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;

/**
 * Абстрактный базовый класс для создателей объектов предметной области.
 * <p>
 * Классам-наследникам достаточно реализовать методы создания {@link #createDataModel(ObjData) информационной модели} и
 * {@link #createViewModel(ObjData) модели отображения} объекта.
 *
 * @author nvamelichev
 *
 * @see #createDataModel(ObjData)
 * @see #createViewModel(ObjData)
 */
public abstract class BasicObjCreator implements ObjCreator {
    @Override
    public boolean canConvert(ObjData data) {
        return data.getProps().hasValue(TypedProp.NAME);
    }

    @Override
    public final Obj<?, ?> toDomain(ObjData data) {
        return new DefObj<>(data, createDataModel(data.getProps()), createViewModel(data.getProps()));
    }

    /**
     * Создает информационную модель объекта.
     *
     * @param props значения свойств объекта
     *
     * @return информационная модель
     */
    protected abstract @NonNull DataModel createDataModel(@NonNull PropValues props);

    /**
     * Создает модель отображения объекта.
     *
     * @param props значения свойств объекта
     *
     * @return модель отображения
     */
    protected abstract @NonNull ViewModel createViewModel(@NonNull PropValues props);
}
