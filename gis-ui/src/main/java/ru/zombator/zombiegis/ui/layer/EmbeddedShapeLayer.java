package ru.zombator.zombiegis.ui.layer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.swing.ImageIcon;

import com.bbn.openmap.dataAccess.shape.DbfHandler;
import com.bbn.openmap.io.FormatException;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.layer.shape.SpatialIndex;
import com.bbn.openmap.util.PropUtils;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.netbeans.api.annotations.common.StaticResource;

/**
 * Слой с политическими границами, загружающий данные из файла, находящегося в ресурсах.
 *
 * @author nvamelichev
 */
public final class EmbeddedShapeLayer extends ShapeLayer {
    private static final Logger LOG = Logger.getLogger(EmbeddedShapeLayer.class.getName());

    @StaticResource(relative = true)
    private static final String SHAPE_FILE = "world_borders.shp";

    @Override
    protected void setFileProperties(String realPrefix, Properties props) {
        URL shapeFileURL = getClass().getResource(SHAPE_FILE);

        if (shapeFileURL != null) {
            String dbfFileName = SpatialIndex.dbf(shapeFileURL.toString());
            try {
                spatialIndex = new SpatialIndex(shapeFileURL.toString());

                DbfHandler dbfh = createDbfHandler(dbfFileName);
                dbfh.setProperties(realPrefix, props);
                spatialIndex.setDbf(dbfh);
            } catch (FormatException | IOException e) {
                LogRecord rec = new LogRecord(Level.FINE, "{0}: Couldn't create DBF handler for {1}");
                rec.setParameters(new Object[]{getName(), dbfFileName});
                rec.setThrown(e);

                LOG.log(rec);
            }

            String imageURLString = props.getProperty(realPrefix + pointImageURLProperty);

            try {
                if (imageURLString != null && imageURLString.length() > 0) {
                    URL imageURL = PropUtils.getResourceOrFileOrURL(this, imageURLString);
                    ImageIcon imageIcon = new ImageIcon(imageURL);
                    spatialIndex.setPointIcon(imageIcon);
                }
            } catch (MalformedURLException murle) {
                LogRecord rec = new LogRecord(Level.INFO, "{0}: point image URL not so good:\n\t{1}");
                rec.setParameters(new Object[]{getName(), imageURLString});
                rec.setThrown(murle);

                LOG.log(rec);
            } catch (NullPointerException npe) {
                LogRecord rec = new LogRecord(Level.INFO, "{0}: can't access icon image:\n\t{1}");
                rec.setParameters(new Object[]{getName(), imageURLString});
                rec.setThrown(npe);

                LOG.log(rec);
            }

            setSpatialIndex(spatialIndex);
        } else {
            LOG.log(Level.WARNING, "{0}: No Shape file was specified:\n\t{1}",
                    new Object[]{getName(), realPrefix + shapeFileProperty});
        }
    }

}
