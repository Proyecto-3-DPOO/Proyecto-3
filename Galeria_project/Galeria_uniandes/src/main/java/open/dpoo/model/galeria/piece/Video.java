package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.List;
import java.util.Locale;

public class Video extends Piece {
    private final String resolution;
    private final int fps;
    private final int duration;

    public Video(long id, String title, int year, Locale origin, List<Author> authors, String description,
                 Availability availability, Valoration valoration, TemporalLocation storage, String resolution, int fps,
                 int duration) {
        super(id, title, year, origin, authors, description, availability, valoration, storage);
        this.resolution = resolution;
        this.fps = fps;
        this.duration = duration;
    }

    @Override public String toString() {
        return "Video{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", origin=" + origin +
                ", authors=" + authors + ", description='" + description + '\'' + ", availability=" + availability +
                ", valoration=" + valoration + ", storage=" + storage + ", resolution='" + resolution + '\'' +
                ", fps=" + fps + ", duration=" + duration + '}';
    }

    public String getResolution() {
        return resolution;
    }

    public int getFps() {
        return fps;
    }

    public int getDuration() {
        return duration;
    }
}
