package ru.zombator.zombiegis.datasource.spi.def;

import static ru.zombator.zombiegis.datasource.TypedProp.*;

import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.spi.def.BasicObjCreator;
import ru.zombator.zombiegis.model.obj.DataModel;
import ru.zombator.zombiegis.model.obj.ViewModel;
import ru.zombator.zombiegis.model.obj.def.BasicDataModel;
import ru.zombator.zombiegis.model.obj.def.BasicViewModel;

/**
 * Абстрактный базовый класс для создателей объектов, использующих {@link BasicDataModel}, {@link BasicViewModel}
 * и их наследников в качестве реализации информационной модели и модели отображения объекта.
 *
 * @param <DB> тип построителя информационной модели объекта
 * @param <VB> тип построителя модели отображения объекта
 *
 * @author nvamelichev
 *
 * @see #dataBuilder() dataBuilder()
 * @see #viewBuilder() viewBuilder()
 * @see #fillData(BasicDataModel.Builder, ObjData) fillData(BasicDataModel.Builder, ObjData)
 * @see #fillView(BasicViewModel.Builder, ObjData) fillView(BasicViewModel.Builder, ObjData)
 */
public abstract class DefObjCreator<DB extends BasicDataModel.Builder, VB extends BasicViewModel.Builder> extends BasicObjCreator {
    /**
     * @return создает построитель информационной модели объекта
     */
    protected abstract DB dataBuilder();

    /**
     * @return создает построитель модели отображения объекта
     */
    protected abstract VB viewBuilder();

    @Override
    protected final DataModel createDataModel(PropValues props) {
        return fillData(dataBuilder(), props).build();
    }

    @Override
    protected final ViewModel createViewModel(PropValues props) {
        return fillView(viewBuilder(), props).build();
    }

    /**
     * Заполняет построитель информационной модели объекта данными, соответствующими объекту карты.
     * <p>
     * Рекомендуемый способ переопределения:
     * <blockquote>
     * <pre>
     * return super.fillData(bldr.&lt;...>, props);
     * </pre>
     * </blockquote>
     * При этом на самом верхнем уровне автоматически заполняются поля "местоположение", "имя объекта"
     * и "видимость объекта"; заполнять их в вашем переопределенном методе не требуется.
     *
     * @param bldr построитель информационной модели объекта
     * @param props значения свойств объекта
     *
     * @return заполненный построитель информационной модели объекта
     */
    protected BasicDataModel.Builder fillData(DB bldr, PropValues props) {
        return bldr
               .at(props.get(LOCATION))
               .named(props.get(NAME))
               .visible(props.get(VISIBLE));
    }

    /**
     * Заполняет построитель модели отображения объекта данными, соответствующими объекту карты.
     * <p>
     * Рекомендуемый способ переопределения:
     * <blockquote>
     * <pre>
     * return super.fillView(bldr.&lt;...>, props);
     * </pre>
     * </blockquote>
     * При этом на самом верхнем уровне автоматически заполняются поля "цвет объекта", "цвет фона"
     * и "видимость подписи объекта"; заполнять их в вашем переопределенном методе не требуется.
     *
     * @param bldr построитель модели отображения объекта
     * @param props значения свойств объекта
     *
     * @return заполненный построитель модели отображения объекта
     */
    protected BasicViewModel.Builder fillView(VB bldr, PropValues props) {
        return bldr
               .background(props.get(BACKGROUND))
               .color(props.get(FOREGROUND))
               .showName(props.get(SHOW_NAME));
    }
}
