package ru.zombator.zombiegis.interfaces;

import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Сервис по работе с объектами указанной карты.
 *
 * @author nvamelichev
 */
public interface ObjService {
    // Callbacks
    /**
     * Подписывается на события изменения объектов карты и загружает текущее состояние карты.
     * При удачной подписке, в отдельном потоке будет вызван метод обработчика {@link GisObjCallback#onCreate(GisObjDTO[]) "объекты добавлены"}),
     * и ему будут переданы объекты, соответствующие текущему состоянию карты.
     * <p>
     * Гарантируется, что методы обработчика событий будут вызываться в одном потоке.
     *
     * @param mapId идентификатор карты
     * @param clbk обработчик событий изменения объектов карты
     *
     * @see ObjCallback
     *
     * @throws RemoteGISException не удалось подписаться
     */
    void startListening(long mapId, @NonNull ObjCallback clbk) throws RemoteGISException;

    /**
     * Отписывается от изменений объектов карты. После удачной отписки, в отдельном потоке будет вызван метод обработчика
     * {@link GisObjCallback#onDelete(Long[]) "объекты удалены"}.
     *
     * @param mapId идентификатор карты
     * @param clbk обработчик событий изменения объектов карты
     *
     * @throws RemoteGISException не удалось отписаться
     */
    void stopListening(long mapId, @NonNull ObjCallback clbk) throws RemoteGISException;

    // CRUD
    /**
     * Создает объекты с указанными свойствами на указанной карте.
     *
     * @param mapId идентификатор карты
     * @param objs создаваемые объекты
     *
     * @throws RemoteGISException не удалось создать объекты
     *
     * @return созданные объекты
     */
    ObjDTO[] createObjects(long mapId, @NonNull ObjDTO[] objs) throws RemoteGISException;

    /**
     * Изменяет указанные объекты.
     *
     * @param objs измененные объекты
     *
     * @throws RemoteGISException не удалось произвести изменения
     */
    void modifyObjects(@NonNull ObjDTO[] objs) throws RemoteGISException;

    /**
     * Удаляет указанные объекты.
     *
     * @param objIds идентификаторы удаляемых объектов. Если объекта с таким идентификатором нет,
     * то идентификатор игнорируется.
     *
     * @throws RemoteGISException не удалось удалить объекты
     */
    void deleteObjects(@NonNull long[] objIds) throws RemoteGISException;
}
