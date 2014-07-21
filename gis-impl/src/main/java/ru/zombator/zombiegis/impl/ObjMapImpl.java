package ru.zombator.zombiegis.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openide.util.Lookup;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import ru.zombator.util.ListenerSupport;
import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.datasource.ObjFactory;
import ru.zombator.zombiegis.interfaces.ObjCallback;
import ru.zombator.zombiegis.interfaces.ObjService;
import ru.zombator.zombiegis.interfaces.RemoteGISException;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.model.event.MapListener;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.event.ObjListener;
import ru.zombator.zombiegis.model.obj.util.Objs;
import ru.zombator.zombiegis.transfers.MapDTO;
import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Реализация карты.
 * <p>
 * Содержимое карты модифицируется ровно одним потоком - ObjCallback Thread ({@link SingleThreadedObjCallback}),
 * но может читаться любым количеством потоков.
 * <br>
 * Пользователю методами {@link #objs() objs()} и {@link #objs(Predicate) objs(Predicate)}
 * возвращаются неизменяемые "мгновенные снимки" состояния карты.
 *
 * @author nvamelichev
 *
 * @see SingleThreadedObjCallback
 */
public final class ObjMapImpl implements ObjMap {
    private static final Logger LOG = Logger.getLogger(ObjMapImpl.class.getName());

    private final long id;
    private final MapMeta meta;

    private final AtomicBoolean open = new AtomicBoolean();
    private final AtomicBoolean opening = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();

    private final ListenerSupport<MapListener> mapListeners = new ListenerSupport<>(MapListener.class);
    private final ListenerSupport<ObjListener> objListeners = new ListenerSupport<>(ObjListener.class);

    private final ObjCallback callback = new SingleThreadedObjCallback(new MapObjCallback());

    private final ConcurrentMap<Long, Obj<?, ?>> objects = new ConcurrentHashMap<>();

    public ObjMapImpl(MapDTO mapdto) {
        this.id = mapdto.id;
        this.meta = new MapMetaImpl(mapdto);
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
        Preconditions.checkState(open.get(), "cannot get map objects when map is not opened");
        return ImmutableSet.copyOf(objects.values());
    }

    @Override
    public void open() throws GISException {
        checkClosed();

        if (open.get()) {
            return;
        }

        try {
            if (opening.compareAndSet(false, true)) {
                objService().startListening(id, callback);
            }
            open.set(true);
        } catch (RemoteGISException e) {
            throw new GISException(e);
        } finally {
            opening.set(false);
        }

        mapListeners.getFireable().mapOpened(this);
    }

    @Override
    public boolean isOpen() {
        return open.get();
    }

    @Override
    public boolean isClosed() {
        return closed.get();
    }

    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        open.set(false);

        try {
            objService().stopListening(id, callback);
        } catch (RemoteGISException e) {
            LOG.log(Level.WARNING, "caught error while unsubscribing", e);
        }

        objListeners.close();

        mapListeners.getFireable().mapClosed(this);
        mapListeners.close();

        objects.clear();
    }

    @Override
    public void addListener(MapListener listener) {
        checkClosed();
        mapListeners.addListener(listener);
    }

    @Override
    public void removeListener(MapListener listener) {
        checkClosed();
        mapListeners.removeListener(listener);
    }

    @Override
    public void addObjListener(ObjListener listener) {
        checkClosed();
        objListeners.addListener(listener);
    }

    @Override
    public void removeObjListener(ObjListener listener) {
        checkClosed();
        objListeners.removeListener(listener);
    }

    private ObjService objService() {
        return Lookup.getDefault().lookup(ObjService.class);
    }

    private void checkClosed() throws IllegalStateException {
        Preconditions.checkState(!closed.get(), "map is closed");
    }

    @Override
    public String toString() {
        return isClosed() ?
               "ObjMapImpl *Closed*" :
               new StringBuilder("ObjMapImpl ")
               .append(meta.getName()).append("\"  ")
               .append(" [id: ").append(id).append(", ")
               .append("meta: ").append(meta).append(']')
               .toString();
    }

    private final class MapObjCallback implements ObjCallback {
        private final ObjFactory OBJ_FACTORY = Lookup.getDefault().lookup(ObjFactory.class);

        @Override
        public void onCreate(ObjDTO[] created) {
            objListeners.getFireable().objsAdded(toDomain(created));
            }

        @Override
        public void onModify(ObjDTO[] modified) {
            objListeners.getFireable().objsChanged(toDomain(modified));
        }

        /**
         * Конвертирует DTO в объекты предметной области и помещает их в отображение "ид объекта --> объект предметной области".
         *
         * @param dtos DTO
         *
         * @return объекты предметной области, которые удалось получить из переданных DTO
         */
        private Collection<Obj<?, ?>> toDomain(ObjDTO[] dtos) {
            Collection<Obj<?, ?>> objs = OBJ_FACTORY.toDomain(Arrays.asList(dtos));
            for (Obj<?, ?> obj: objs) {
                objects.put(obj.getId(), obj);
        }

            return objs;
        }

        @Override
        public void onDelete(Long[] deleted) {
            ImmutableList.Builder<Obj<?, ?>> deletedObjs = ImmutableList.builder();
            for (Long id: deleted) {
                objects.get(id);
            }

            objListeners.getFireable().objsRemoved(deletedObjs.build());
        }
    }
}
