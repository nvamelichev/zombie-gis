package ru.zombator.zombiegis.datasource;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.google.common.base.Preconditions;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.properties.TextAlign;
import ru.zombator.zombiegis.properties.TextStyle;
import ru.zombator.zombiegis.properties.Type;
import ru.zombator.zombiegis.transfers.Primitive;
import ru.zombator.zombiegis.transfers.Prop;

/**
 * Перечисление свойств, которые используются клиентом.
 *
 * @param <T> тип значений свойства
 *
 * @author nvamelichev
 *
 * @see Prop
 */
public final class TypedProp<T extends Serializable> implements Serializable, Comparable<TypedProp<?>> {
    // Общие свойства:
    /**
     * Имя объекта.
     */
    public static final TypedProp<String> NAME = new TypedProp<>(Prop.NAME, String.class);
    /**
     * Тип объекта.
     */
    public static final TypedProp<Type> TYPE = new TypedProp<>(Prop.TYPE, Type.class);

    /**
     * Географическое положение объекта.
     */
    public static final TypedProp<Pos> LOCATION = new TypedProp<>(Prop.LOCATION, Pos.class);

    /**
     * Курс объекта.
     */
    public static final TypedProp<Double> COURSE = new TypedProp<>(Prop.COURSE, Double.class);
    /**
     * Скорость объекта.
     */
    public static final TypedProp<Double> SPEED = new TypedProp<>(Prop.SPEED, Double.class);

    /**
     * Основной цвет (цвет текста или цвет линий).
     */
    public static final TypedProp<Long> FOREGROUND = new TypedProp<>(Prop.FOREGROUND, Long.class);
    /**
     * Цвет фона.
     */
    public static final TypedProp<Long> BACKGROUND = new TypedProp<>(Prop.BACKGROUND, Long.class);

    // Специфические свойства:
    /**
     * Точки полигона.
     */
    public static final TypedProp<Pos> POLY_POINTS = new TypedProp<>(Prop.POLY_POINTS, Pos.class);
    /**
     * Замкнутый полигон или нет.
     */
    public static final TypedProp<Boolean> POLY_CLOSED = new TypedProp<>(Prop.POLY_CLOSED, Boolean.class);

    /**
     * Начальный угол дуги в градусах.
     */
    public static final TypedProp<Double> ARC_START = new TypedProp<>(Prop.ARC_START, Double.class);
    /**
     * Длина дуги в градусах.
     */
    public static final TypedProp<Double> ARC_EXTENT = new TypedProp<>(Prop.ARC_EXTENT, Double.class);
    /**
     * Радиус дуги по X в морских милях.
     */
    public static final TypedProp<Double> ARC_R_X = new TypedProp<>(Prop.ARC_R_X, Double.class);
    /**
     * Радиус дуги по Y в морских милях.
     */
    public static final TypedProp<Double> ARC_R_Y = new TypedProp<>(Prop.ARC_R_Y, Double.class);

    /**
     * Текст надписи.
     */
    public static final TypedProp<String> LABEL_TEXT = new TypedProp<>(Prop.LABEL_TEXT, String.class);
    /**
     * Название шрифта надписи.
     */
    public static final TypedProp<String> LABEL_FONT_NAME = new TypedProp<>(Prop.LABEL_FONT_NAME, String.class);
    /**
     * Размер шрифта надписи в пунктах.
     */
    public static final TypedProp<Long> LABEL_FONT_SIZE = new TypedProp<>(Prop.LABEL_FONT_SIZE, Long.class);
    /**
     * Стили шрифта.
     */
    public static final TypedProp<TextStyle> LABEL_TEXT_STYLE = new TypedProp<>(Prop.LABEL_TEXT_STYLE, TextStyle.class);
    /**
     * Выравнивание шрифта.
     */
    public static final TypedProp<TextAlign> LABEL_TEXT_ALIGN = new TypedProp<>(Prop.LABEL_TEXT_ALIGN, TextAlign.class);

    private static final Map<String, TypedProp<?>> VALUES = new LinkedHashMap<>();

    private final Prop prop;
    private final Class<T> clazz;

    private TypedProp(Prop prop, Class<T> clazz) {
        if (prop.isEnum()) {
            Preconditions.checkArgument(prop.getEnumType().equals(clazz),
                    "property eLong class must match clazz");
        } else {
            Preconditions.checkArgument(prop.getPrimitive().getType().equals(clazz),
                    "property primitive class must match clazz");
        }

        this.prop = prop;
        this.clazz = clazz;

        VALUES.put(prop.name(), this);
    }

    @Override
    public String toString() {
        return name();
    }

    /**
     * @return идентификатор свойства
     */
    public String name() {
        return prop.name();
    }

    /**
     * @return человекочитаемое имя свойства
     */
    public String displayName() {
        try {
            ResourceBundle bundle =
                ResourceBundle.getBundle(getClass().getPackage().getName() + "." + getClass().getSimpleName());
            return bundle.getString(name());
        } catch (MissingResourceException mre) {
            return toString();
        }
    }

    /**
     * @return тип значений свойства
     */
    public Class<T> type() {
        return clazz;
    }

    /**
     * @return серверный тип значений свойства
     */
    public Primitive primitive() {
        return prop.getPrimitive();
    }

    /**
     * @return <code>true</code>, если значение данного свойства - элемент перечисления; иначе <code>false</code>
     */
    public boolean isEnum() {
        return prop.isEnum();
    }

    /**
     * @return все поддерживаемые клиентом свойства
     */
    public static TypedProp<?>[] values() {
        return VALUES.values().toArray(new TypedProp<?>[0]);
    }

    /**
     * Возвращает свойство с указанным идентификатором.
     *
     * @param name идентификатор свойства
     *
     * @return свойство с указанным идентификатором
     *
     * @throws IllegalArgumentException нет свойства с таким идентификатором
     */
    public static TypedProp<?> valueOf(String name) {
        TypedProp<?> value = VALUES.get(name);
        if (value == null) {
            throw new IllegalArgumentException("no such TypedProp: " + name);
        }

        return value;
    }

    @Override
    public int hashCode() {
        return prop.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TypedProp<?> other = (TypedProp<?>) obj;
        return this.prop == other.prop;
    }

    @Override
    public int compareTo(TypedProp<?> other) {
        return this.prop.compareTo(other.prop);
    }

    private Object readResolve() throws ObjectStreamException {
        try {
            return valueOf(prop.name());
        } catch (IllegalArgumentException iae) {
            throw new InvalidObjectException(iae.getMessage());
        }
    }
}
