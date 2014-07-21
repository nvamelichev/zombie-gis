
package ru.zombator.zombiegis.obj.renderer.def;

import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.obj.renderer.ObjRenderer;

/**
 * Абстрактный базовый класс для реализаций {@link ObjRenderer отрисовщиков объектов карты}.
 * <p>
 * Разделяет отрисовку объектов карты на два этапа: {@link #doRender(Obj) формирование набора графических примитивов}
 * и {@link #applyStyle(Object, ViewModel) постобработку}.
 *
 * @param <D> тип информационной модели
 * @param <V> тип модели отображения
 * @param <G> тип графических примитивов
 *
 * @author nvamelichev
 *
 * @see #doRender(Obj) отрисовка
 * @see #applyStyle(Object, ViewModel) постобработка
 */
public abstract class DefRenderer<D extends DataModel, V extends ViewModel, G> implements ObjRenderer<D, V, G> {

    @Override
    public final Result<G> render(Obj<D, V> obj) {
        // отрисовка
        List<G> rendered = doRender(obj);

        // постобработка
        List<G> postProcessed = new ArrayList<>();
        for (G graphic: rendered) {
            postProcessed.add(applyStyle(graphic, obj.getView()));
        }

        return DefResult.create(postProcessed);
    }

    /**
     * Выполняет отрисовку объекта карты с помощью графических примитивов.
     *
     * @param obj отрисовываемый объект карты
     *
     * @return графические примитивы, соответствующие объекту
     */
    protected abstract List<G> doRender(Obj<D, V> obj);

    /**
     * Выполняет постобработку графических примитивов, выданных методом
     * {@link #doRender(Obj) doRender()}.
     * <p>
     * В реализации по умолчанию, применяет к каждому графическому примитиву
     * атрибуты оформления: {@link #getColor(ViewModel) цвет объекта} и
     * {@link #getBackground(ViewModel) цвет фона}.
     *
     * @param graphic графический примитив-оригинал
     * @param view модель отображения объекта карты
     *
     * @return обработанный графический примитив
     *
     * @see #getColor(ViewModel)
     * @see #getBackground(ViewModel)
     */
    protected G applyStyle(G graphic, V view) {
        graphic = applyColor(graphic, getColor(view));
        graphic = applyBackground(graphic, getBackground(view));

        return graphic;
    }

    /**
     * Возвращает цвет объекта.
     *
     * @param view модель отображения объекта
     *
     * @return цвет объекта
     *
     * @see #applyStyle(Object, ViewModel)
     * @see #applyColor(Object, ViewModel)
     */
    protected Paint getColor(V view) {
        return view.getForeColor();
    }

    /**
     * Возвращает цвет фона объекта.
     *
     * @param view модель отображения объекта
     *
     * @return цвет фона
     *
     * @see #applyStyle(Object, ViewModel)
     * @see #applyBackground(Object, ViewModel)
     */
    protected Paint getBackground(V view) {
        return view.getBackColor();
    }

    /**
     * Применяет оформление - цвет объекта - к графическому примитиву.
     * <p>
     * В реализации по умолчанию ничего не делает.
     *
     * @param graphic графический примитив-оригинал
     * @param color цвет объекта
     *
     * @return графический примитив, к которому применен цвет объекта
     *
     * @see #applyStyles(Object, ViewModel)
     * @see #getColor(ViewModel)
     */
    protected G applyColor(G graphic, Paint color) {
        return graphic;
    }

    /**
     * Применяет оформление - цвет фона - к графическому примитиву.
     * <p>
     * В реализации по умолчанию ничего не делает.
     *
     * @param graphic графический примитив-оригинал
     * @param background цвет фона
     *
     * @return графический примитив, к которому применен цвет фона
     *
     * @see #applyStyles(Object, ViewModel)
     * @see #getBackground(ViewModel)
     */
    protected G applyBackground(G graphic, Paint background) {
        return graphic;
    }
}
