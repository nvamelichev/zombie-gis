package ru.zombator.zombiegis.model.obj.shape;

import java.awt.Paint;

import ru.zombator.zombiegis.model.obj.def.BasicViewModel;

/**
 * Модель отображения полигона.
 *
 * @author anv
 */
public final class PolyView extends BasicViewModel {
    private final boolean closed;

    private PolyView(boolean showName, Paint foreColor, Paint backColor,
            boolean closed) {
        super(showName, foreColor, backColor);
        this.closed = closed;
    }

    /**
     * @return <code>true</code>, если полигон замкнутый; иначе <code>false</code>
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Возвращает построителя модели отображения полигона.
     *
     * @return построитель
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель модели отображения полигона.
     */
    public static final class Builder extends BasicViewModel.Builder {
        private boolean closed;

        public Builder closed() {
            return closed(true);
        }

        public Builder open() {
            return closed(false);
        }

        public Builder closed(boolean closed) {
            this.closed = closed;
            return this;
        }

        @Override
        public PolyView build() {
            return new PolyView(isShowName(), getForeColor(), getBackColor(), closed);
        }
    }
}
