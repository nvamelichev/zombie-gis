package ru.zombator.zombiegis.model.obj.event;

import java.util.Collection;

import ru.zombator.zombiegis.model.obj.Obj;

/**
 * Слушатель изменений объектов карты.
 *
 * @author nvamelichev
 */
public interface ObjListener {
    /**
     * Вызывается после того, как на карту были добавлены указанные объекты.
     *
     * @param added добавленные объекты
     */
    void objsAdded(Collection<Obj<?, ?>> added);

    /**
     * Вызывается после того, как объекты карты были изменены.
     *
     * @param changed измененные объекты карты
     */
    void objsChanged(Collection<Obj<?, ?>> changed);

    /**
     * Вызывается после того, как объекты карты были удалены.
     *
     * @param removed удаленные объекты карты
     */
    void objsRemoved(Collection<Obj<?, ?>> removed);
}
