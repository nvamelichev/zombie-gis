package ru.zombator.zombiegis.model.obj.shape;

import java.util.List;

import java.util.ArrayList;

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

    /**
     * Возвращает построитель информационной модели объекта.
     *
     * @return построитель
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель информационной модели.
     */
    public static class Builder extends BasicDataModel.Builder {
        private final List<Pos> points = new ArrayList<>();

        protected Builder() {
        }

        /**
         * @throws UnsupportedOperationException операция не поддерживается
         *
         * @deprecated Задание центра полигона не поддерживается, используйте {@link #points(List) points(List&lt;Pos>)}.
         */
        @Override
        @Deprecated
        public Builder at(Pos pos) {
            throw new UnsupportedOperationException("polygon cannot be defined by its center; call points(List<Pos>)");
        }

        public Builder points(List<Pos> points) {
            points.clear();
            points.addAll(points);
            return this;
        }

        /**
         * @return информационная модель
         */
        @Override
        public PolyData build() {
            return new PolyData(getName(), isVisible(), points);
        }

        // Для наследников, которые хотят воспользоваться значениями:
        protected final List<Pos> getPoints() {
            return ImmutableList.copyOf(points);
        }
    }
}
