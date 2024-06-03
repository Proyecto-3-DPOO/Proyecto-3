package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public abstract class Piece {
    protected long id;
    protected String title;
    protected int year;
    protected Locale origin;
    protected List<Author> authors;
    protected String description;

    protected Availability availability;
    protected Valoration valoration;
    protected TemporalLocation storage;

    public Piece(long id, String title, int year, Locale origin, List<Author> authors, String description,
                 Availability availability, Valoration valoration, TemporalLocation storage) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.origin = origin;
        this.authors = authors;
        this.description = description;
        this.availability = availability;
        this.valoration = valoration;
        this.storage = storage;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;
        return getId() == piece.getId() && getYear() == piece.getYear() && getTitle().equals(piece.getTitle()) &&
                Objects.equals(getOrigin(), piece.getOrigin()) && Objects.equals(getAuthors(), piece.getAuthors()) &&
                Objects.equals(getDescription(), piece.getDescription()) &&
                Objects.equals(getAvailability(), piece.getAvailability()) &&
                Objects.equals(getValoration(), piece.getValoration()) &&
                Objects.equals(getStorage(), piece.getStorage());
    }

    @Override public int hashCode() {
        int result = Long.hashCode(getId());
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getYear();
        result = 31 * result + Objects.hashCode(getOrigin());
        result = 31 * result + Objects.hashCode(getAuthors());
        result = 31 * result + Objects.hashCode(getDescription());
        return result;
    }

    @Override public abstract String toString();

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Locale getOrigin() {
        return origin;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Valoration getValoration() {
        return valoration;
    }

    public TemporalLocation getStorage() {
        return storage;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void setValoration(Valoration valoration) {
        this.valoration = valoration;
    }

    public void setStorage(TemporalLocation storage) {
        this.storage = storage;
    }
}
