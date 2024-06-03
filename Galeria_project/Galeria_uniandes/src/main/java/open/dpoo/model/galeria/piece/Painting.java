package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.List;
import java.util.Locale;

public class Painting extends Piece {
    private final int width;
    private final int height;
    private final String style;

    public Painting(long id, String title, int year, Locale origin, List<Author> authors, String description,
                    Availability availability, Valoration valoration, TemporalLocation storage, String style, int height,
                    int width) {
        super(id, title, year, origin, authors, description, availability, valoration, storage);
        this.width = width;
        this.height = height;
        this.style = style;
    }

    @Override public String toString() {
        return "Painting{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", origin=" + origin +
                ", authors=" + authors + ", description='" + description + '\'' + ", availability=" + availability +
                ", valoration=" + valoration + ", storage=" + storage + ", width=" + width + ", height=" + height +
                ", style='" + style + '\'' + '}';
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getStyle() {
        return style;
    }
}
