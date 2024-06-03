package main.java.open.dpoo.model.usuarios;

import main.java.open.dpoo.model.galeria.piece.Piece;

import java.util.LinkedList;
import java.util.List;

/**
 * The Author class represents a user who is an author within the system.
 * Authors may have special privileges related to content creation or management.
 */
public class Author extends BaseUser {
    private final List<Piece> pieces = new LinkedList<>();

    /**
     * Constructor for creating an Author instance.
     *
     * @param name     The name of the author.
     * @param document The document number of the author.
     * @param phone    The phone number of the author.
     * @param login    The login username of the author.
     * @param password The login password of the author.
     */
    public Author(String name, String document, String phone, String login, String password) {
        super(name, document, phone, login, password);
    }

    private void addPiece(Piece piece) {
        pieces.add(piece);
    }

    @Override public String toString() {
        return "Author{" + "name='" + name + '\'' + ", document='" + document + '\'' + ", phone='" + phone + '\'' +
                ", login='" + login + '\'' + ", password='" + password + '\'' + ", pieces=" + pieces + '}';
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}

