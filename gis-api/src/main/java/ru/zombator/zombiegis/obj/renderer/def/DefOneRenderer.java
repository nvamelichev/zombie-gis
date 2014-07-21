
package ru.zombator.zombiegis.obj.renderer.def;

import java.util.Collections;
import java.util.List;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;

/**
 * Абстрактный базовый класс для {@link ru.zombator.zombiegis.obj.renderer.ObjRenderer отрисовщиков объектов карты},
 * создающих ровно один графический примитив для одного объекта карты.
 *
 * @param <D> тип информационной модели
 * @param <V> тип модели отображения
 * @param <G> тип графических примитивов
 *
 * @author nvamelichev
 *
 * @see #doRenderOne(Obj) отрисовка
 */
public abstract class DefOneRenderer<D extends DataModel, V extends ViewModel, G> extends DefRenderer<D, V, G> {
    @Override
    protected final List<G> doRender(Obj<D, V> obj) {
        return Collections.singletonList(doRenderOne(obj));
    }

    /**
     * Выполняет отрисовку указанного объекта карты в виде одного графического примитива.
     *
     * @param obj объект карты
     *
     * @return графический примитив, соответствующий объекту карты
     */
    protected abstract G doRenderOne(Obj<D, V> obj);
}
