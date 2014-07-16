package ru.zombator.zombiegis.interfaces;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.transfers.MapDTO;

/**
 * Интерфейс сервиса для получения списка всех доступных карт.
 *
 * @author nvamelichev
 */
public interface MapListService {
    // List
    /**
     * Возвращает все доступные карты.
     *
     * @return все доступные карты
     *
     * @throws RemoteGISException не удалось получить список карт
     */
    MapDTO[] getMaps() throws RemoteGISException;

    /**
     * Возвращает карту с указанным идентификатором.
     *
     * @param id идентификатор карты
     * @return карта с указанным идентификатором; <code>null</code>, если такой карты нет
     * @throws RemoteGISException
     */
    MapDTO getMap(long id) throws RemoteGISException;

    // CRUD
    /**
     * Создает новую карту с указанным именем, центром и изначальным масштабом.
     *
     * @param name название карты
     * @param center центр карты
     * @param initialZoom изначальный масштаб
     *
     * @return созданная карта
     *
     * @throws RemoteGISException не удалось создать карту
     */
    MapDTO createMap(String name, Pos center, double initialZoom) throws RemoteGISException;

    /**
     * Обновляет указанную карту. Если такой карты нет, ничего не делает.
     *
     * @param map обновленная карта
     *
     * @throws RemoteGISException при обновлении возникла ошибка
     *
     * @return <code>true</code>, если карта была обновлена; иначе <code>false</code>
     */
    boolean modifyMap(MapDTO map) throws RemoteGISException;

    /**
     * Удаляет карту с указанным идентификатором. Если такой карты нет, ничего не делает.
     *
     * @param id идентификатор удаляемой карты
     *
     * @throws RemoteGISException при удалении возникла ошибка
     *
     *  @return <code>true</code>, если карта была удалена; иначе <code>false</code>
     */
    boolean deleteMap(long id) throws RemoteGISException;
}
