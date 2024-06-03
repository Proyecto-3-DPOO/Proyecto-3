package main.java.open.dpoo.model.usuarios;

import main.java.open.dpoo.model.galeria.piece.Piece;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Owner extends BaseUser {
    private final List<Piece> pieces = new LinkedList<>();

    public Owner(String name, String document, String phone, String login, String password) {
        super(name, document, phone, login, password);
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public Optional<Piece> getPieceById(long id) {
        return pieces.stream().filter(piece -> piece.getId() == id).findFirst();
    }

    @Override public String toString() {
        return "Owner{" + "name='" + name + '\'' + ", document='" + document + '\'' + ", phone='" + phone + '\'' +
                ", login='" + login + '\'' + ", password='" + password + '\'' + ", pieces=" + pieces + '}';
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
