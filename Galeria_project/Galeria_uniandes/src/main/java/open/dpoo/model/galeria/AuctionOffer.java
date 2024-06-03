package main.java.open.dpoo.model.galeria;

import main.java.open.dpoo.model.galeria.piece.Piece;

/** The AuctionOffer class represents an offer made by a purchaser for a specific piece in an auction. */
public class AuctionOffer {
    private final Piece piece;
    private double offer;

    /**
     * Constructs an AuctionOffer object with the specified piece and offer.
     *
     * @param piece The piece for which the offer is made
     * @param offer The amount offered by the purchaser for the piece
     */
    public AuctionOffer(Piece piece, double offer) {
        this.offer = offer;
        this.piece = piece;
    }

    @Override public String toString() {
        return "AuctionOffer{" + "piece=" + piece + ", offer=" + offer + '}';
    }

    public Piece getPiece() {
        return piece;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }
}