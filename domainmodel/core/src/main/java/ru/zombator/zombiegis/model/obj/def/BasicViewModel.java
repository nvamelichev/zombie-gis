package ru.zombator.zombiegis.model.obj.def;

import java.awt.Paint;

import ru.zombator.zombiegis.model.obj.ViewModel;

/**
 * Базовый класс для реализации {@link ViewModel модели отображения}.
 *
 * @author anv
 */
public class BasicViewModel implements ViewModel {
    private final boolean showName;
    private final Paint foreColor;
    private final Paint backColor;

    protected BasicViewModel(boolean showName, Paint foreColor, Paint backColor) {
        this.showName = showName;
        this.foreColor = foreColor;
        this.backColor = backColor;
    }

    @Override
    public boolean isShowName() {
        return showName;
    }

    @Override
    public Paint getForeColor() {
        return foreColor;
    }

    @Override
    public Paint getBackColor() {
        return backColor;
    }

    /**
     * Возвращает построителя модели отображения.
     *
     * @return построитель модели отображения
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель модели отображения.
     */
    public static class Builder {
        private boolean showName;
        private Paint foreColor;
        private Paint backColor;

        public Builder showName() {
            return showName(true);
        }

        public Builder hideName() {
            return showName(false);
        }

        public Builder showName(Boolean showName) {
            if (showName == null) {
                return this;
            }

            this.showName = showName;
            return this;
        }

        public Builder color(Paint foreColor) {
            this.foreColor = foreColor;
            return this;
        }

        public Builder background(Paint backColor) {
            this.backColor = backColor;
            return this;
        }

        /**
         * Создает модель отображения.
         *
         * @return модель отображения
         */
        public BasicViewModel build() {
            return new BasicViewModel(showName, foreColor, backColor);
        }

        // Для наследников:
        protected final boolean isShowName() {
            return showName;
        }

        protected final Paint getForeColor() {
            return foreColor;
        }

        protected final Paint getBackColor() {
            return backColor;
        }
    }
}
