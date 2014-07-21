package ru.zombator.zombiegis.transfers;

import java.util.Objects;

import ru.zombator.zombiegis.properties.TextAlign;
import ru.zombator.zombiegis.properties.TextStyle;
import ru.zombator.zombiegis.properties.Type;

/**
 * Свойства объектов карты.
 *
 * @author nvamelichev
 *
 * @see Primitive способ представления значений свойства
 */
public enum Prop {
    // Общие свойства:
    /**
     * Название объекта.
     */
    NAME(Primitive.STR),
    /**
     * Тип объекта.
     */
    TYPE(Primitive.STR, Type.class),
    /**
     * Отображается ли объект.
     */
    VISIBLE(Primitive.BOOL),

    /**
     * Географическое положение объекта.
     */
    LOCATION(Primitive.POS),

    /**
     * Курс объекта.
     */
    COURSE(Primitive.REAL),
    /**
     * Скорость объекта.
     */
    SPEED(Primitive.REAL),

    /**
     * Основной цвет (цвет текста или цвет линий).
     */
    FOREGROUND(Primitive.NUM),
    /**
     * Цвет фона.
     */
    BACKGROUND(Primitive.NUM),

    // Специфические свойства:
    /**
     * Точки полигона.
     */
    POLY_POINTS(Primitive.POS),
    /**
     * Замкнутый полигон или нет.
     */
    POLY_CLOSED(Primitive.BOOL),

    /**
     * Начальный угол дуги в градусах.
     */
    ARC_START(Primitive.REAL),
    /**
     * Длина дуги в градусах.
     */
    ARC_EXTENT(Primitive.REAL),
    /**
     * Радиус дуги по X в морских милях.
     */
    ARC_R_X(Primitive.REAL),
    /**
     * Радиус дуги по Y в морских милях.
     */
    ARC_R_Y(Primitive.REAL),

    /**
     * Текст надписи.
     */
    LABEL_TEXT(Primitive.STR),
    /**
     * Название шрифта надписи.
     */
    LABEL_FONT_NAME(Primitive.STR),
    /**
     * Размер шрифта надписи в пунктах.
     */
    LABEL_FONT_SIZE(Primitive.NUM),
    /**
     * Стили шрифта.
     */
    LABEL_TEXT_STYLE(Primitive.NUM, TextStyle.class),
    /**
     * Выравнивание шрифта.
     */
    LABEL_TEXT_ALIGN(Primitive.NUM, TextAlign.class);

    private final Primitive primitive;
    private final Class<? extends Enum<?>> eclazz;

    private Prop(Primitive primitive) {
        this(primitive, null);
    }

    private Prop(Primitive primitive, Class<? extends Enum<?>> eclazz) {
        if (eclazz != null && primitive != Primitive.STR) {
            throw new IllegalArgumentException("enum values must be represented as strings (Primitive.STR)");
        }

        this.primitive = Objects.requireNonNull(primitive, "primitive");
        this.eclazz = eclazz;
    }

    /**
     * @return <code>true</code>, если значение данного свойства - имя элемента некоторого перечисления; иначе
     * <code>false</code>
     *
     * @see #getEnumType()
     */
    public boolean isEnum() {
        return eclazz != null;
    }

    /**
     * @return тип перечисления, если значение данного свойства - элемент перечисления; иначе <code>null</code>
     *
     * @see #isEnum()
     */
    public Class<? extends Enum<?>> getEnumType() {
        return eclazz;
    }

    /**
     * @return представление значения свойства на сервере
     */
    public Primitive getPrimitive() {
        return primitive;
    }
}
