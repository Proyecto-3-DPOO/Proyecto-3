package main.java.open.dpoo.model.galeria;

import main.java.open.dpoo.model.galeria.piece.Piece;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/** The Inventory class manages the inventory of art pieces in the gallery. */
public class Inventory {
    private static Inventory instance = null;
    private static long id = 0;
    private final List<Piece> exhibition = new LinkedList<>();
    private final List<Piece> stored = new LinkedList<>();
    private final List<Piece> sold = new LinkedList<>();
    private final List<Piece> toSold = new LinkedList<>();
    private final List<Auction> auctions = new LinkedList<>();

    /**
     * Retrieves the singleton instance of the Inventory.
     *
     * @return The Inventory instance
     */
    public static Inventory getInstance() {
        return instance == null ? instance = new Inventory() : instance;
    }

    public static long getId() {return id++;}

    /**
     * Retrieves a specific piece from the inventory.
     *
     * @param piece The piece to retrieve
     * @return The retrieved piece, or null if not found
     */
    public Piece getPiece(Piece piece) {
        if (exhibition.contains(piece)) {
            return exhibition.get(exhibition.indexOf(piece));
        } else if (stored.contains(piece)) {
            return stored.get(stored.indexOf(piece));
        } else if (toSold.contains(piece)) {
            return toSold.get(toSold.indexOf(piece));
        } else if (sold.contains(piece)) {
            return sold.get(sold.indexOf(piece));
        }
        return null;
    }

    public Auction getAuction(Date startAuctionDate, Date endAuctionDate) {
        return auctions.stream().filter(auction -> auction.getStartAuctionDate().equals(startAuctionDate) &&
                auction.getEndAuctionDate().equals(endAuctionDate)).findFirst().orElse(null);
    }

    /**
     * Adds a piece to the exhibition.
     *
     * @param piece The piece to add to the exhibition
     */
    public void addPieceEXH(Piece piece) {
        exhibition.add(piece);
    }


    /**
     * Removes a piece from the exhibition.
     *
     * @param piece The piece to remove from the exhibition
     */
    public void removePieceEXH(Piece piece) {
        exhibition.remove(piece);
    }

    /**
     * Adds a piece to the list of pieces available for sale.
     *
     * @param piece The piece to add to the list of pieces for sale
     */
    public void addPieceVEN(Piece piece) {
        toSold.add(piece);
    }

    /**
     * Removes a piece from the list of pieces available for sale.
     *
     * @param piece The piece to remove from the list of pieces for sale
     */
    public void removePieceVEN(Piece piece) {
        toSold.remove(piece);
    }

    /**
     * Adds a piece to the storage inventory.
     *
     * @param piece The piece to add to the storage inventory
     */
    public void addPieceBOD(Piece piece) {
        stored.add(piece);
    }

    /**
     * Removes a piece from the storage inventory.
     *
     * @param piece The piece to remove from the storage inventory
     */
    public void removePieceBOD(Piece piece) {
        stored.remove(piece);
    }

    /**
     * Marks a piece as sold and moves it to the sold list.
     *
     * @param piece The piece to mark as sold
     */
    public void soldPiece(Piece piece) {
        if (toSold.contains(piece)) {
            getPiece(piece).getAvailability().setSold(true);
            sold.add(piece);
            toSold.remove(piece);
        }
    }

    /**
     * Moves a piece from storage to the exhibition.
     *
     * @param piece The piece to exhibit
     */
    public void exhibitPiece(Piece piece) {
        if (!exhibition.contains(piece) && stored.contains(piece)) {
            stored.remove(piece);
            exhibition.add(piece);
        }
    }

    public List<Piece> getPieces() {
        LinkedList<Piece> pieces = new LinkedList<>(exhibition);
        pieces.addAll(stored);
        return pieces;
    }

    public Optional<Piece> getPieceById(long id) {
        return exhibition.stream().filter(piece -> piece.getId() == id).findFirst();
    }

    public void addAuction(Date start, Date end) {
        auctions.add(new Auction(start, end));
    }

    public List<Piece> getExhibition() {
        return exhibition;
    }

    public List<Piece> getStored() {
        return stored;
    }

    public List<Piece> getSold() {
        return sold;
    }

    public List<Piece> getToSold() {
        return toSold;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }
}
