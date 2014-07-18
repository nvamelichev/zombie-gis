package ru.zombator.zombiegis.datasource.spi;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.openide.util.lookup.NamedServiceDefinition;

import ru.zombator.zombiegis.datasource.TypedProp;

/**
 * Конвертер из значений данных сервера в значения данных клиента.
 * <p>
 * Конвертеры регистрируются как именованные глобальные сервисы с помощью аннотации
 * {@link ValueConverter.Registration &#64;ValueConverter.Registration} и должны иметь конструктор без параметров.
 * Все конвертеры можно получить, используя следующий код:
 * <blockquote>
 * <pre>
 * Lookups.forPath(ValueConverter.PATH).lookupAll(ValueConverter.class)
 * </pre>
 * </blockquote>
 *
 * @param ServerT серверный тип данных
 * @param ClientT клиентский тип данных
 *
 * @author nvamelichev
 */
public interface ValueConverter<ServerT extends Serializable, ClientT extends Serializable> {
    /**
     * Путь для регистрации конвертеров в <code>META-INF/namedservices</code>.
     */
    String PATH = "ValueConverters";

    /**
     * Проверяет, может ли конвертер преобразовать значения указанного свойства.
     *
     * @param prop свойство
     *
     * @return <code>true</code>, если конвертер может преобразовать значения указанного свойства;
     * иначе <code>false</code>
     */
    boolean canConvert(TypedProp<?> prop);

    /**
     * Преобразует набор серверных значений свойства в набор клиентских значений свойства.
     *
     * @param prop свойство
     * @param serverValues серверные значения свойства
     *
     * @return клиентские значения свойства
     */
    Collection<ClientT> toClient(TypedProp<ClientT> prop, Collection<ServerT> serverValues);

    /**
     * Аннотация для регистрации конвертера серверных значений свойств в клиентские.
     */
    @NamedServiceDefinition(serviceType = ValueConverter.class, path = ValueConverter.PATH)
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public static @interface Registration {
        /**
         * Позиция данного конвертера относительно других конвертеров. Конвертеры рассматриваются
         * системой в порядке возрастания позиции; порядок рассмотрения конвертеров с одинаковым
         * значением позиции не определен.
         *
         * @return позиция
         */
        int position() default Integer.MAX_VALUE;
    }
}
