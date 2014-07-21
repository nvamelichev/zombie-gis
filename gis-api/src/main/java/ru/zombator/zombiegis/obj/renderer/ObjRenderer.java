package ru.zombator.zombiegis.obj.renderer;

import java.util.List;

import ru.zombator.zombiegis.annotations.NamedServiceExDefinition;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.properties.Type;

/**
 * Отрисовщик объектов карты.
 * <p>
 * Для регистрации используйте {@link ObjRenderer.Registration &#64;ObjRenderer.Registration}.
 * Для получения отрисовщиков определенных видов объектов карты, используйте:
 * <blockquote>
 * <pre>
 * Lookups.forPath(ObjRenderer.PATH + "/" + type.name()).lookupAll(ObjRenderer.class),
 * </pre>
 * </blockquote>
 * где <code>type</code> - тип отрисовываемых объектов карты.
 *
 * @param <D> тип информационной модели объекта
 * @param <V> тип модели отображения объекта
 * @param <G> тип графических примитивов
 *
 * @author nvamelichev
 */
public interface ObjRenderer<D extends DataModel, V extends ViewModel, G> {
    /**
     * Путь, по которому отрисовщики объектов карты регистрируются в <code>META-INF/namedservices</code>.
     */
    String PATH = "ObjRenderers";

    /**
     * Представляет указанный объект карты в виде совокупности графических примитивов.
     *
     * @param obj объект карты
     *
     * @return результат отрисовки объекта карты
     */
    Result<G> render(Obj<D, V> obj);

    /**
     * Результат отрисовки объекта карты.
     *
     * @param <G> тип графических примитивов
     */
    public interface Result<G> {
        /**
         * @return список графических примитивов
         */
        List<G> asList();

        // TODO: возможности для расширения:
        // * атрибуты вращения (система сама повернет графические примитивы на заданный угол относительно указанной точки);
        // * иные аффинные преобразования;
        // * графические эффекты.
    }

    /**
     * Аннотация для регистрации отрисовщика.
     */
    @NamedServiceExDefinition(path = PATH + "/type()", serviceType = ObjRenderer.class)
    public @interface Registration {
        /**
         * Тип отрисовываемых объектов.
         *
         * @return тип отрисовываемых объектов
         */
        Type type();

        /**
         * Позиция отрисовщика по порядку. Система рассматривает первыми отрисовщиков с меньшей позицией.
         * Если есть два отрисовщика с одинаковой позицией, порядок их рассмотрения не определен.
         *
         * @return позиция
         */
        int position() default Integer.MAX_VALUE;
    }
}
