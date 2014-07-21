package ru.zombator.zombiegis.model.obj.def;

import com.google.common.base.Preconditions;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.model.obj.MobDataModel;

/**
 * Базовый класс для реализаций {@link MobDataModel информационной модели подвижного объекта}.
 *
 * @author anv
 */
public class BasicMobDataModel extends BasicDataModel implements MobDataModel {
    private final double course;
    private final double speed;

    protected BasicMobDataModel(String name, Pos pos, boolean visible, double course, double speed) {
        super(name, pos, visible);

        this.course = course;
        this.speed = speed;
    }

    @Override
    public double getCourse() {
        return course;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    /**
     * Возвращает построитель информационной модели подвижного объекта.
     *
     * @return построитель
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель информационной модели подвижного объекта.
     */
    public static class Builder extends BasicDataModel.Builder {
        private static final double MIN_COURSE = 0;
        private static final double MAX_COURSE = 360;

        private double course;
        private double speed;

        protected Builder() {
        }

        public Builder onCourse(Double course) {
            if (course == null) {
                return this;
            }

            Preconditions.checkArgument(course >= MIN_COURSE && course <= MAX_COURSE,
                    "course must lie between %s and %s degrees", MIN_COURSE, MAX_COURSE);
            this.course = course;

            return this;
        }

        public Builder movingAt(Double speed) {
            if (speed == null) {
                return this;
            }

            Preconditions.checkArgument(speed >= 0 && speed < Double.POSITIVE_INFINITY,
                    "speed must be non-negative and not infinite");
            this.speed = speed;
            return this;
        }

        @Override
        public BasicMobDataModel build() {
            return new BasicMobDataModel(getName(), getPos(), isVisible(), course, speed);
        }
    }
}
