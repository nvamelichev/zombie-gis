package ru.zombator.zombiegis.ui.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractListModel;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;

import com.google.common.collect.ImmutableList;

import ru.zombator.zombiegis.model.MapMeta;

/**
 * Панель для выбора карты.
 *
 * @author nvamelichev
 */
public final class ChooseMapPanel extends javax.swing.JPanel {
    private final DialogDescriptor desc;
    private final MapListModel mapListModel;

    public ChooseMapPanel(Map<Long, MapMeta> maps) {
        this(maps, null);
    }

    public ChooseMapPanel(Map<Long, MapMeta> maps, DialogDescriptor desc) {
        this.mapListModel = new MapListModel(maps);
        this.desc = desc;

        initComponents();
        updateValid();
    }

    private void updateValid() {
        if (desc != null) {
            desc.setValid(getSelectedMap() != null);
        }
    }

    /**
     * Отображает диалог для выбора карты с указанным заголовком.
     *
     * @param title заголовок диалога
     * @param maps допустимые варианты выбора карты
     *
     * @return выбранная карта или <code>null</code>, если выбор был отменен
     */
    public static Entry<Long, MapMeta> notify(String title, Map<Long, MapMeta> maps) {
        DialogDescriptor desc = new DialogDescriptor("", title);

        ChooseMapPanel panel = new ChooseMapPanel(maps, desc);
        desc.setMessage(panel);

        Object result = DialogDisplayer.getDefault().notify(desc);
        return DialogDescriptor.OK_OPTION.equals(result) ? panel.getSelectedMap() : null;
    }

    /**
     * @return выбранная карта
     */
    public Entry<Long, MapMeta> getSelectedMap() {
        return lstMaps.getSelectedValue();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMaps = new javax.swing.JLabel();
        spMaps = new javax.swing.JScrollPane();
        lstMaps = new javax.swing.JList<Entry<Long, MapMeta>>();

        org.openide.awt.Mnemonics.setLocalizedText(lblMaps, org.openide.util.NbBundle.getMessage(ChooseMapPanel.class, "ChooseMapPanel.lblMaps.text")); // NOI18N

        lstMaps.setModel(mapListModel);
        lstMaps.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstMaps.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstMapsValueChanged(evt);
            }
        });
        spMaps.setViewportView(lstMaps);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMaps)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spMaps, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaps)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spMaps, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lstMapsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstMapsValueChanged
        updateValid();
    }//GEN-LAST:event_lstMapsValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMaps;
    private javax.swing.JList<Entry<Long, MapMeta>> lstMaps;
    private javax.swing.JScrollPane spMaps;
    // End of variables declaration//GEN-END:variables

    private static final class MapListModel extends AbstractListModel<Map.Entry<Long, MapMeta>> {
        private final List<Entry<Long, MapMeta>> maps;

        private MapListModel(Map<Long, MapMeta> maps) {
            List<Entry<Long, MapMeta>> mapList = new ArrayList<>(maps.entrySet());
            Collections.sort(mapList, new Comparator<Entry<Long, MapMeta>>() {
                @Override
                public int compare(Entry<Long, MapMeta> o1, Entry<Long, MapMeta> o2) {
                    return o1.getValue().getName().compareTo(o2.getValue().getName());
                }
            });

            this.maps = ImmutableList.copyOf(mapList);
        }

        @Override
        public int getSize() {
            return maps.size();
        }

        @Override
        public Map.Entry<Long, MapMeta> getElementAt(int index) {
            return maps.get(index);
        }
    }
}