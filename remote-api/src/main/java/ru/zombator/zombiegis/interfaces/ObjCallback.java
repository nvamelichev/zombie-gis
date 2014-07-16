package ru.zombator.zombiegis.interfaces;

import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Методы, вызываемые при изменениях объектов карты.
 *
 * @author nvamelichev
 */
public interface ObjCallback {
    /**
     * Вызывается после того, как на карте были созданы новые объекты.
     *
     * @param created созданные объекты карты
     */
    void onCreate(ObjDTO[] created);

    /**
     * Вызывается после того, как объекты карты были изменены.
     *
     * @param modified измененные объекты карты
     */
    void onModify(ObjDTO[] modified);

    /**
     * Вызывается после того, как объекты были удалены с карты.
     *
     * @param deleted идентификаторы удаленных объектов
     */
    void onDelete(Long[] deleted);
}
