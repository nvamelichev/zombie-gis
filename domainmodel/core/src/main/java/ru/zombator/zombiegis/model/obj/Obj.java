package ru.zombator.zombiegis.model.obj;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;

import net.jcip.annotations.Immutable;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.properties.Type;

/**
 * Объект карты.
 *
 * <ul>
 * <li>
 * Реализация {@link Obj} представляет состояние объекта карты в некоторый момент времени; это состояние
 * должно быть неизменяемым.
 * <p>
 * При изменении объектов карты, старое состояние заменяется новым. Те, кто сохранил ссылку на старое состояние,
 * могут безопасно продолжать его использовать, даже если объект был изменен или удален. Если требуется всегда
 * использовать самое последнее состояние объекта и реагировать в случае удаления объекта, можно
 * {@link ru.zombator.zombiegis.model.ObjMap#addObjListener(ru.zombator.zombiegis.model.obj.event.ObjListener)
 * подписаться} на изменения состояния объекта карты.
 * </li>
 * <li>
 * Состояние объекта карты делится на две части:
 * <ul>
 * <li>
 * {@link #getData() информационную модель}, описывающую географические атрибуты объекта: его местоположение,
 * курс и скорость (если применимо), составные части;
 * </li>
 * <li>
 * {@link #getView() модель отображения}, описывающую атрибуты отображения объекта: цвета, линии, экранные размеры
 * (в случае значка) и т.п.
 * </li>
 * </ul>
 * </li>
 * <li>
 * Реализация {@link Object#hashCode() hashCode()} и {@link Object#equals(Object) equals()}:
 * <ul>
 * <li>
 * Два объекта, у которых {@link #getId() идентификатор} <code>!= null</code>, равны, если их идентификаторы равны.
 * </li>
 * <li>
 * Два объекта <code>a</code> и <code>b</code>, имеющие {@link #getId() идентификатор} <code>== null</code>,
 * равны, если им соответствует одна и та же ссылка (<code>a == b</code>).
 * </li>
 * <li>
 * Два объекта, у одного из которых {@link #getId() идентификатор} <code>== null</code>, а у другого - нет,
 * не равны друг другу.
 * </li>
 * </ul>
 * </li>
 * </ul>
 *
 * @param <D> тип информационной модели
 * @param <V> тип модели отображения
 *
 * @author nvamelichev
 *
 * @see DataModel
 * @see MobDataModel
 * @see ViewModel
 */
@Immutable
public interface Obj<D extends DataModel, V extends ViewModel> {
    /**
     * @return уникальный идентификатор объекта карты;
     *         <code>null</code>, если объект еще не сохранен на сервере
     */
    @CheckForNull Long getId();

    /**
     * Возвращает название объекта карты. Вызов аналогичен вызову
     * <code>{@link #getData() getData()}.{@link DataModel#getName() getName()}</code>.
     *
     * @return название объекта карты
     */
    @NonNull String getName();

    /**
     * Возвращает тип объекта карты.
     *
     * @return
     */
    @NonNull Type getType();

    /**
     * Проверяет, является ли объект подвижным (имеет ли он курс и скорость).
     *
     * @return <code>true</code>, если объект подвижный; иначе <code>false</code>
     *
     * @see MobDataModel
     */
    boolean isMobile();

    /**
     * Проверяет, отображается объект на карте или нет. Вызов аналогичен вызову
     * <code>{@link #getData() getData()}.{@link DataModel#isVisible() isVisible()}</code>.
     *
     * @return <code>true</code>, если объект видимый; иначе <code>false</code>
     */
    boolean isVisible();

    /**
     * @return информационная модель
     */
    @NonNull D getData();

    /**
     * @return модель отображения
     */
    @NonNull V getView();

    /**
     * @return карта, которой принадлежит объект
     */
    @NonNull ObjMap getMap();
}
