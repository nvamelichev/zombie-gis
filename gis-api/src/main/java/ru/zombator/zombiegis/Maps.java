package ru.zombator.zombiegis;

import java.util.Map;

import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;

/**
 * Глобальный клиентский сервис по работе с картами.
 * <p>
 * Получите реализацию через глобальный NetBeans Lookup:
 * <blockquote>
 * <pre>
 * Lookup.getDefault().lookup(Maps.class)
 * </pre>
 * </blockquote>
 *
 * @author nvamelichev
 */
public interface Maps {
    /**
     * Возвращает список карт.
     *
     * @return соответствие "идентификатор карты"-"карта"
     */
    Map<Long, MapMeta> getList() throws GISException;

    /**
     * Создает, но не открывает карту.
     *
     * @param mapId идентификатор карты
     *
     * @return карта
     *
     * @throws IllegalArgumentException карты с таким идентификатором не существует
     *
     * @see ObjMap#open()
     */
    ObjMap newMap(long mapId);
}
