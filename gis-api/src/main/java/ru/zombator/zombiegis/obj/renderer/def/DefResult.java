package ru.zombator.zombiegis.obj.renderer.def;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.obj.renderer.ObjRenderer;
import ru.zombator.zombiegis.obj.renderer.ObjRenderer.Result;

/**
 * Стандартная реализация {@link ObjRenderer.Result результата отрисовки объекта карты}.
 *
 * @param <G> тип графических примитивов
 *
 * @author nvamelichev
 */
public final class DefResult<G> implements ObjRenderer.Result<G> {
    private final List<G> list;

    public static <G> Result<G> create(Iterable<? extends G> elements) {
        return new DefResult<>(elements);
    }

    @SafeVarargs
    public static <G> Result<G> create(G... elements) {
        return new DefResult<>(Arrays.asList(elements));
    }

    private DefResult(Iterable<? extends G> list) {
        this.list = ImmutableList.copyOf(list);
    }

    @Override
    public List<G> asList() {
        return list;
    }
}
