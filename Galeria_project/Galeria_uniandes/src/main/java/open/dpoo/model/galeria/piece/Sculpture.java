package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Author;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Sculpture extends Piece {
    private final List<String> materials = new LinkedList<>();
    private final List<String> details = new LinkedList<>();
    private final double width;
    private final double height;
    private final double depth;
    private final double weight;
    private final boolean electric;

    public Sculpture(long id, String title, int year, Locale origin, List<Author> authors, String description,
                     Availability availability, Valoration valoration, TemporalLocation storage, double width,
                     double height, double depth, double weight, boolean electric) {
        super(id, title, year, origin, authors, description, availability, valoration, storage);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.electric = electric;
    }

    public void addMaterial(String material) {
        materials.add(material);
    }

    public void addDetail(String detail) {
        details.add(detail);
    }

    @Override public String toString() {
        return "Sculpture{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", origin=" + origin +
                ", authors=" + authors + ", description='" + description + '\'' + ", availability=" + availability +
                ", valoration=" + valoration + ", storage=" + storage + ", materials=" + materials + ", details=" +
                details + ", width=" + width + ", height=" + height + ", depth=" + depth + ", weight=" + weight +
                ", electric=" + electric + '}';
    }

    public List<String> getMaterials() {
        return materials;
    }

    public List<String> getDetails() {
        return details;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isElectric() {
        return electric;
    }
}
