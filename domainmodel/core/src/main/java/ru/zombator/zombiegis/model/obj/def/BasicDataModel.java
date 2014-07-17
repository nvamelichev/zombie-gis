package ru.zombator.zombiegis.model.obj.def;

import java.util.Objects;

import ru.zombator.zombiegis.geo.Pos;
import ru.zombator.zombiegis.model.obj.DataModel;

/**
 * Базовый класс для реализаций {@link DataModel информационной модели}.
 *
 * @author nvamelichev
 */
public class BasicDataModel implements DataModel {
    private final String name;
    private final Pos center;
    private final boolean visible;

    protected BasicDataModel(String name, Pos center, boolean visible) {
        this.name = Objects.requireNonNull(name, "name");
        this.center = Objects.requireNonNull(center, "center");
        this.visible = visible;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Pos getCenter() {
        return center;
    }

    @Override
    public boolean isVisible() {
        return visible;
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
    public static class Builder {
        private String name = "";
        private Pos pos = Pos.fromDeg(0, 0);
        private boolean visible = true;

        protected Builder() {
        }

        public Builder named(String name) {
            this.name = name;
            return this;
        }

        public Builder at(Pos pos) {
            this.pos = Objects.requireNonNull(pos, "pos");
            return this;
        }

        public Builder visible() {
            return visible(true);
        }

        public Builder hidden() {
            return visible(false);
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        /**
         * @return информационная модель
         */
        public BasicDataModel build() {
            return new BasicDataModel(name, pos, visible);
        }

        // Для наследников, которые хотят воспользоваться значениями:
        protected final String getName() {
            return name;
        }

        protected final Pos getPos() {
            return pos;
        }

        protected final boolean isVisible() {
            return visible;
        }
    }
}
