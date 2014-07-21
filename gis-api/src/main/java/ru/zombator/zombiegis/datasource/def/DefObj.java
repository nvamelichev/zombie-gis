package ru.zombator.zombiegis.datasource.def;

import java.util.Objects;

import org.netbeans.api.annotations.common.NonNull;

import org.joda.time.Instant;

import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.TypedProp;
import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.properties.Type;

/**
 * Стандартная реализация объекта карты.
 *
 * @param <D> тип информационной модели
 * @param <V> тип модели отображения
 *
 * @author nvamelichev
 */
public final class DefObj<D extends DataModel, V extends ViewModel> implements Obj<D, V> {
    private final long id;
    private final Instant timestamp;

    private final Type type;
    private final boolean mobile;

    private final D data;
    private final V view;

    public DefObj(@NonNull ObjData objData, @NonNull D dataModel, @NonNull V viewModel) {
        this.id = objData.getId();
        this.timestamp = new Instant(objData.getTimestamp());

        this.type = Objects.requireNonNull(objData.getProps().get(TypedProp.TYPE), "TYPE property");
        this.mobile = type.isSubtypeOf(Type.MOBILE);

        this.data = Objects.requireNonNull(dataModel);
        this.view = Objects.requireNonNull(viewModel);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }

    @Override
    public String getName() {
        return getData().getName();
    }

    @Override
    public Pos getCenter() {
        return getData().getCenter();
    }

    @Override
    public boolean isVisible() {
        return getData().isVisible();
    }

    @Override
    public D getData() {
        return data;
    }

    @Override
    public V getView() {
        return view;
    }
}
