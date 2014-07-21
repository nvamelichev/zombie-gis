package ru.zombator.zombiegis.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import ru.zombator.zombiegis.GISException;
import ru.zombator.zombiegis.Maps;
import ru.zombator.zombiegis.model.MapMeta;
import ru.zombator.zombiegis.model.ObjMap;
import ru.zombator.zombiegis.ui.MapViewerTopComponent;
import ru.zombator.zombiegis.ui.MsgBox;

/**
 * Действие "Открыть карту".
 *
 * @author nvamelichev
 */
@NbBundle.Messages({
    "OpenMapAction.displayName=Open Map...",
    "OpenMapAction.dialogTitle=Open Map",
    "OpenMapAction.listError=Could not get Map List.",
    "OpenMapAction.getMapError=Could not get Map.",
})
@ActionID(category = "File", id = "ru.zombator.zombiegis.ui.action.OpenMapAction")
@ActionRegistration(displayName = "#OpenMapAction.displayName",
        iconBase = "ru/zombator/zombiegis/ui/map.png")
@ActionReferences({
    @ActionReference(path = "Toolbars/File", position = 100),
    @ActionReference(path = "Menu/File", position = 100),
})
public final class OpenMapAction implements ActionListener {
    private static final Logger LOG = Logger.getLogger(OpenMapAction.class.getName());

    @Override
    public void actionPerformed(ActionEvent e) {
        Maps mapListService = Lookup.getDefault().lookup(Maps.class);

        Map<Long, MapMeta> maps;
        try {
            maps = mapListService.getList();
        } catch (GISException ex) {
            LOG.log(Level.INFO, "could not get map list", ex);

            MsgBox.error(Bundle.OpenMapAction_listError());
            return;
        }

        Map.Entry<Long, MapMeta> selected = ChooseMapPanel.notify(Bundle.OpenMapAction_dialogTitle(), maps);
        if (selected == null) {
            return;
        }

        ObjMap objMap;
        try {
            objMap = mapListService.newMap(selected.getKey());
        } catch (GISException ex) {
            LogRecord rec = new LogRecord(Level.INFO, "could not get map \"{0}\" (id: {1})");
            rec.setThrown(ex);
            rec.setParameters(new Object[]{selected.getValue().getName(), selected.getKey()});
            LOG.log(rec);

            MsgBox.error(Bundle.OpenMapAction_getMapError());
            return;
        }

        MapViewerTopComponent tc = new MapViewerTopComponent(objMap);
        tc.open();
        tc.requestActive();
    }
}
