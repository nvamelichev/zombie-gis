package ru.zombator.zombiegis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, расширяющая {@link NamedServiceDefinition} - поддерживает ссылки на значения
 * полей типа Enum и Enum[].
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface NamedServiceExDefinition {
    /**
     * Типы сервисов, которые можно зарегистрировать.
     */
    Class<?>[] serviceType();

    /**
     * Путь, по которому регистрируются именованные сервисы. Может содержать ссылки на значения свойств
     * аннотированной аннотации. Например, для использования атрибута <code>xyzzy</code>
     * аннотированной аннотации напишите: <code>/my/service/path/@xyzzy()/somewhere</code>.
     * 
     * Поддерживаемые типы свойств аннотации, на которые можно сослаться таким образом: String, String[], Enum, Enum[].
     * В случае значений-массивов, регистрация сервиса производится для всех возможных значений в массиве.
     */
    String path();

    /**
     * Название целочисленного атрибута, определяющего позицию сервиса в списке сервисов. По умолчанию,
     * (<code>position == "-"</code>) ищется атрибут <code>position</code>.
     * Если требуется игнорировать значение атрибута <code>position</code>, используйте <code>""</code>.
     *
     * @param name название целочисленного атрибута аннотированной аннотации, который определяет позицию регистрируемого
     * сервиса
     */
    String position() default "-";
}
