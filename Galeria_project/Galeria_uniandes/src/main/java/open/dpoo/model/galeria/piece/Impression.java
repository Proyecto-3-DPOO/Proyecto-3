package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.List;
import java.util.Locale;

public class Impression extends Piece {
    private final String paperType;
    private final String tintType;
    private final String size;
    private final String quality;
    private final String technique;

    public Impression(long id, String title, int year, Locale origin, List<Author> authors, String description,
                      Availability availability, Valoration valoration, TemporalLocation storage, String paperType,
                      String tintType, String size, String quality, String technique) {
        super(id, title, year, origin, authors, description, availability, valoration, storage);
        this.paperType = paperType;
        this.tintType = tintType;
        this.size = size;
        this.quality = quality;
        this.technique = technique;
    }

    @Override public String toString() {
        return "Impression{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", origin=" + origin +
                ", authors=" + authors + ", description='" + description + '\'' + ", availability=" + availability +
                ", valoration=" + valoration + ", storage=" + storage + ", paperType='" + paperType + '\'' +
                ", tintType='" + tintType + '\'' + ", size='" + size + '\'' + ", quality='" + quality + '\'' +
                ", technique='" + technique + '\'' + '}';
    }

    public String getPaperType() {
        return paperType;
    }

    public String getTintType() {
        return tintType;
    }

    public String getSize() {
        return size;
    }

    public String getQuality() {
        return quality;
    }

    public String getTechnique() {
        return technique;
    }
}
