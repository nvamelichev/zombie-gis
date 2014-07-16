package ru.zombator.zombiegis.model.obj.shape;

import java.awt.Paint;

import com.google.common.base.Preconditions;

import ru.zombator.zombiegis.model.obj.BasicViewModel;

/**
 * Представление эллипса.
 *
 * @author anv
 */
public final class ArcView extends BasicViewModel {
    private final double start;
    private final double extent;

    private final double rX;
    private final double rY;

    private ArcView(boolean showName, Paint foreColor, Paint backColor,
            double start, double extent,
            double rX, double rY) {
        super(showName, foreColor, backColor);
        this.start = start;
        this.extent = extent;
        this.rX = rX;
        this.rY = rY;
    }

    /**
     * @return начальный угол в градусах, [-180..180&deg;]
     */
    public double getStart() {
        return start;
    }

    /**
     * @return длина дуги в градусах, [0..360&deg;]
     */
    public double getExtent() {
        return extent;
    }

    /**
     * @return радиус по X в морских милях
     */
    public double getRX() {
        return rX;
    }

    /**
     * @return радиус по Y в морских милях
     */
    public double getRY() {
        return rY;
    }

    /**
     * Возвращает построитель представления эллипса.
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель представления эллипса.
     */
    public static final class Builder extends BasicViewModel.Builder {
        private static final double START_MIN_VALUE = -180.0;
        private static final double START_MAX_VALUE = 180.0;

        private static final double EXTENT_MIN_VALUE = 0.0;
        private static final double EXTENT_MAX_VALUE = 360.0;

        /**
         * Радиус по умолчанию - 10 морских миль.
         */
        private static final double DEFAULT_RADIUS = 10.0;

        private double start = (START_MIN_VALUE + START_MAX_VALUE) / 2;
        private double extent = (EXTENT_MAX_VALUE + EXTENT_MIN_VALUE) / 2;

        private double rX = DEFAULT_RADIUS;
        private double rY = DEFAULT_RADIUS;

        public Builder startAt(double start) {
            Preconditions.checkArgument(start >= START_MIN_VALUE && start <= START_MAX_VALUE,
                    "start angle must lie between %s and %s", START_MIN_VALUE, START_MAX_VALUE);
            this.start = start;
            return this;
        }

        public Builder extent(double extent) {
            Preconditions.checkArgument(extent >= EXTENT_MIN_VALUE && extent <= EXTENT_MAX_VALUE,
                    "extent must lie between %s and %s", EXTENT_MIN_VALUE, EXTENT_MAX_VALUE);
            this.extent = extent;
            return this;
        }

        public Builder xRadius(double rX) {
            Preconditions.checkArgument(rX > 0 && rX < Double.POSITIVE_INFINITY,
                    "X radius must be a strictly positive finite number");
            this.rX = rX;
            return this;
        }

        public Builder yRadius(double rY) {
            Preconditions.checkArgument(rY > 0 && rY < Double.POSITIVE_INFINITY,
                    "Y radius must be a strictly positive finite number");
            this.rY = rY;
            return this;
        }

        @Override
        public ArcView build() {
            return new ArcView(isShowName(), getForeColor(), getBackColor(),
                start, extent,
                rX, rY);
        }
    }
}
