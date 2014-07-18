package ru.zombator.zombiegis.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import org.openide.util.Lookup;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.interfaces.ObjService;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.model.event.MapListener;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.event.ObjListener;
import ru.zombator.zombiegis.model.obj.util.Objs;

/**
 * Реализация карты.
 *
 * @author anv
 */
public final class ObjMapImpl implements ObjMap {
    private final long id;
    private final MapMeta meta;

    private final AtomicBoolean open = new AtomicBoolean();
    private final AtomicBoolean opening = new AtomicBoolean();

    public ObjMapImpl(long id, MapMeta meta) {
        this.id = id;
        this.meta = meta;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public MapMeta getMeta() {
        return meta;
    }

    @Override
    public ImmutableSet<Obj<?, ?>> objs() {
        return objs(Objs.all());
    }

    @Override
    public ImmutableSet<Obj<?, ?>> objs(Predicate<Obj<?, ?>> pred) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void open() throws GISException {
//        try {
//            objService().
//        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private ObjService objService() {
        return Lookup.getDefault().lookup(ObjService.class);
    }

    @Override
    public boolean isOpen() {
        return open.get();
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addListener(MapListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeListener(MapListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addObjListener(ObjListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeObjListener(ObjListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
