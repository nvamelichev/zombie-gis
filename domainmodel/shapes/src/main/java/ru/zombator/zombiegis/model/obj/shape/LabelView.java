package ru.zombator.zombiegis.model.obj.shape;

import java.awt.Font;
import java.awt.Paint;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import ru.zombator.zombiegis.model.obj.def.BasicViewModel;
import ru.zombator.zombiegis.properties.TextAlign;
import ru.zombator.zombiegis.properties.TextStyle;

/**
 * Модель отображения подписи.
 *
 * @author nvamelichev
 */
public final class LabelView extends BasicViewModel {
    private final String text;

    private final String fontName;
    private final int fontSize;

    private final Set<TextStyle> styles;
    private final TextAlign alignment;

    private LabelView(Paint foreColor, Paint backColor,
            String text,
            String fontName, int fontSize,
            TextAlign alignment, Set<TextStyle> styles) {
        super(false, foreColor, backColor);
        this.text = text;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.alignment = alignment;
        this.styles = ImmutableSet.copyOf(styles);
    }

    /**
     * @return текст подписи
     */
    public String getText() {
        return text;
    }

    /**
     * @return название шрифта
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * @return размер шрифта в пунктах
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @return стили текста
     */
    public Set<TextStyle> getStyles() {
        return styles;
    }

    /**
     * @return выравнивание
     */
    public TextAlign getAlignment() {
        return alignment;
    }

    /**
     * Возвращает построитель модели отображения надписи.
     *
     * @return построитель
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Построитель модели отображения надписи.
     */
    public static final class Builder extends BasicViewModel.Builder {
        private static final int DEFAULT_FONT_SIZE = 12;

        private String text = "";

        private String fontName = Font.SANS_SERIF;
        private int fontSize = DEFAULT_FONT_SIZE;

        private final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        private TextAlign alignment;

        public Builder text(String text) {
            if (text == null) {
                return this;
            }

            this.text = text;
            return this;
        }

        public Builder font(String fontName) {
            if (fontName == null || fontName.trim().isEmpty()) {
                return this;
            }

            this.fontName = fontName;
            return this;
        }

        public Builder size(int fontSize) {
            if (fontSize <= 0) {
                return this;
            }

            this.fontSize = fontSize;
            return this;
        }

        public Builder styles(Collection<TextStyle> styles) {
            this.styles.clear();
            this.styles.addAll(styles);
            return this;
        }

        public Builder bold() {
            this.styles.add(TextStyle.BOLD);
            return this;
        }

        public Builder italic() {
            this.styles.add(TextStyle.ITALIC);
            return this;
        }

        public Builder underline() {
            this.styles.add(TextStyle.UNDERLINE);
            return this;
        }

        public Builder strikethru() {
            this.styles.add(TextStyle.STRIKETHRU);
            return this;
        }

        public Builder alignLeft() {
            return align(TextAlign.LEFT);
        }

        public Builder alignRight() {
            return align(TextAlign.RIGHT);
        }

        public Builder alignCenter() {
            return align(TextAlign.CENTER);
        }

        public Builder align(TextAlign alignment) {
            if (alignment == null) {
                return this;
            }

            this.alignment = alignment;
            return this;
        }

        @Override
        public LabelView build() {
            return new LabelView(getForeColor(), getBackColor(),
                text,
                fontName, fontSize,
                alignment, styles);
        }
    }
}
