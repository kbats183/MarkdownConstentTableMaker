package markdownConstentTableMaker;

import java.util.Objects;

public class MarkdownHeader {
    private final int level;
    private final String text;

    public MarkdownHeader(int level, String text) {
        this.level = level;
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkdownHeader header = (MarkdownHeader) o;
        return level == header.level &&
                Objects.equals(text, header.text);
    }
}
