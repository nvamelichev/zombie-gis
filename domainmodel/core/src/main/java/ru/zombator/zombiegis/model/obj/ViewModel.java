package ru.zombator.zombiegis.model.obj;

import java.awt.Paint;

import org.netbeans.api.annotations.common.CheckForNull;

/**
 * Модель отображения объекта карты.
 *
 * @author nvamelichev
 *
 * @see BasicViewModel стандартная реализация
 */
public interface ViewModel {
    /**
     * Проверяет, отображается название объекта или нет.
     *
     * @return <code>true</code>, если название объекта отображается; иначе <code>false</code>
     */
    boolean isShowName();

    /**
     * Возвращает основной цвет объекта (цвет текста или цвет линий).
     *
     * @return основной цвет; <code>null</code>, если следует использовать стандартный цвет
     */
    @CheckForNull Paint getForeColor();

    /**
     * Возвращает цвет фона объекта.
     *
     * @return цвет фона; <code>null</code>, если следует использовать стандартный цвет
     */
    @CheckForNull Paint getBackColor();
}
