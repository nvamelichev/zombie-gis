package ru.zombator.zombiegis.datasource;

import java.util.Collection;

import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Интерфейс глобального сервиса, преобразующего DTO в {@link PropValues наборы свойств}, по которым
 * уже можно {@link ru.zombator.zombiegis.datasource.spi.ObjectFactory#toDomain(PropValues) создавать}
 * объекты предметной области на клиенте.
 * <p>
 * Для получения экземпляра DTOConverter, используйте:
 * <blockquote>
 * <pre>
 * Lookup.getDefault().lookup(DTOConverter.class)
 * </pre>
 * </blockquote>
 * <p>
 * Стандартная реализация DTOConverter при своей работе использует
 * {@link ValueConverter конвертеры серверных "сырых" значений свойств в клиентские}.
 *
 * @author nvamelichev
 *
 * @see ValueConverter
 * @see ObjectFactory
 */
public interface DTOConverter {
    /**
     * Конвертирует свойства заданных объектов из серверного "сырого" формата в клиентский.
     * Например, свойства типа "перечисление" сервер передает в виде строк с именами элементов
     * перечисления; а DTOConverter возвращает уже настоящие элементы перечисления.
     *
     * @param dtos объекты передачи данных
     *
     * @return клиентские данные
     */
    Collection<ObjData> toObjData(Collection<ObjDTO> dtos);
}
