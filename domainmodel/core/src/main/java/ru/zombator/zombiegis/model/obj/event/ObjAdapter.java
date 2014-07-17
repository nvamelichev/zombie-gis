package ru.zombator.zombiegis.model.obj.event;

import java.util.Collection;

import ru.zombator.zombiegis.model.obj.Obj;

/**
 * Адаптер для {@link ObjListener слушателей изменений объектов карты}.
 *
 * @author nvamelichev
 */
public abstract class ObjAdapter implements ObjListener {
    @Override
    public void objsAdded(Collection<Obj<?, ?>> added) {
    }

    @Override
    public void objsChanged(Collection<Obj<?, ?>> changed) {
    }

    @Override
    public void objsRemoved(Collection<Obj<?, ?>> removed) {
    }
}
