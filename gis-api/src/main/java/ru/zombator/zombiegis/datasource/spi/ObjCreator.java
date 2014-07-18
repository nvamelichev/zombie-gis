package ru.zombator.zombiegis.datasource.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openide.util.lookup.NamedServiceDefinition;

import ru.zombator.zombiegis.annotations.NamedServiceExDefinition;
import ru.zombator.zombiegis.datasource.ObjData;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.properties.Type;

/**
 * Создатель объектов предметной области.
 * <p>
 * Создатели регистрируются как глобальные именованные сервисы с помощью аннотации
 * {@link ObjectFactory.Registration &#64;ObjectFactory.Registration} и должны иметь конструктор без параметров.
 * Чтобы получить все создатели объектов, используйте вызов:
 * <blockquote>
 * <pre>
 * Lookups.forPath(ObjCreator.PATH + "/" + type).lookupAll(ObjCreator.class),
 * </pre>
 * где <code>type</code> - {@link Type#name() название} {@link Type типа объектов}.
 * </blockquote>
 *
 * @author nvamelichev
 */
public interface ObjCreator {
    /**
     * Путь, по которому создатели объектов регистрируются в <code>META-INF/namedservices</code>.
     * Для регистрации используйте аннотацию {@link ObjCreator.Registration &#64;ObjCreator.Registration}.
     */
    String PATH = "ObjCreators";

    /**
     * Проверяет, можно ли преобразовать объект с данным набором свойств в объект предметной области.
     *
     * @param data данные объекта
     *
     * @return <code>true</code>, если данная фабрика может создать объект предметной области по данному
     * набору свойств; иначе <code>false</code>
     *
     * @see #toDomain(ObjData)
     */
    boolean canConvert(ObjData data);

    /**
     * Создает объект предметной области по данному набору свойств.
     *
     * @param props данные объекта
     *
     * @return объект предметной области
     *
     * @throws IllegalArgumentException <code>!{@link #canCreate(ObjData) canCreate(props)}</code>
     */
    Obj<?, ?> toDomain(ObjData props);

    /**
     * Аннотация для регистрации создателей объектов.
     */
    @NamedServiceExDefinition(serviceType = ObjCreator.class, path = ObjCreator.PATH + "/@type()")
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public static @interface Registration {
        /**
         * Тип объектов, которые можно создать.
         */
        Type type();

        /**
         * Позиция фабрики объектов предметной области. Система рассматривает фабрики по возрастанию
         * их позиции; порядок рассмотрения двух фабрик с одной и той же позицией не определен.
         *
         * @return позиция
         */
        int position() default Integer.MAX_VALUE;
    }
}
