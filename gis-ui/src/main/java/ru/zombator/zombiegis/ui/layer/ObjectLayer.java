
package ru.zombator.zombiegis.ui.layer;

import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import java.util.Collection;
import java.util.Objects;
import org.openide.util.NbBundle;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.model.obj.event.ObjListener;

/**
 *
 * @author nvamelichev
 */
public final class ObjectLayer extends OMGraphicHandlerLayer {
    private final ObjMap map;

    private final ObjListener listener = new ObjectListener();

    public ObjectLayer(ObjMap map) {
        this.map = Objects.requireNonNull(map, "map");
        map.addObjListener(listener);
    }

    @Override
    public void dispose() {
        super.dispose();

        if (!map.isClosed()) {
            map.removeObjListener(listener);
        }
    }

    private final class ObjectListener implements ObjListener {
        @Override
        public void objsAdded(Collection<Obj<?, ?>> added) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void objsChanged(Collection<Obj<?, ?>> changed) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void objsRemoved(Collection<Obj<?, ?>> removed) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
