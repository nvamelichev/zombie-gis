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
     *
     * @throws GISException не удалось получить список карт
     */
    Map<Long, MapMeta> getList() throws GISException;

    /**
     * Создает, но не открывает карту.
     *
     * @param mapId идентификатор карты
     *
     * @return карта
     *
     * @throws NoSuchMapException карты с таким идентификатором не существует
     * @throws GISException ошибка при получении карты
     *
     * @see ObjMap#open()
     */
    ObjMap newMap(long mapId) throws NoSuchMapException, GISException;
}
