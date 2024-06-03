package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.List;
import java.util.Locale;

public class Photography extends Piece {
    private final int megapixels;
    private final int tamanio;
    private final String len;

    public Photography(long id, String title, int year, Locale origin, List<Author> authors, String description,
                       Availability availability, Valoration valoration, TemporalLocation storage, int megapixels,
                       int tamanio, String len) {
        super(id, title, year, origin, authors, description, availability, valoration, storage);
        this.megapixels = megapixels;
        this.tamanio = tamanio;
        this.len = len;
    }

    @Override public String toString() {
        return "Photography{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", origin=" + origin +
                ", authors=" + authors + ", description='" + description + '\'' + ", availability=" + availability +
                ", valoration=" + valoration + ", storage=" + storage + ", megapixels=" + megapixels + ", tamanio=" +
                tamanio + ", len='" + len + '\'' + '}';
    }

    public int getMegapixels() {
        return megapixels;
    }

    public int getTamanio() {
        return tamanio;
    }

    public String getLen() {
        return len;
    }
}
