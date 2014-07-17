package ru.zombator.zombiegis.model.obj.shape;

import java.util.List;

import com.google.common.collect.ImmutableList;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.geo.Pos3D;
import ru.zombator.zombiegis.model.obj.def.BasicDataModel;

/**
 * Информационная модель полигона.
 *
 * @author anv
 */
public final class PolyData extends BasicDataModel {
    private final List<Pos> points;

    private PolyData(String name, boolean visible, List<Pos> points) {
        super(name, Pos3D.centroid(points), visible);
        this.points = ImmutableList.copyOf(points);
    }

    /**
     * @return точки полигона
     */
    public List<Pos> getPoints() {
        return points;
    }
}
