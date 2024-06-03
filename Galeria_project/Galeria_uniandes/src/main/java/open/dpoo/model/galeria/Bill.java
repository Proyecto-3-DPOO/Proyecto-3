package main.java.open.dpoo.model.galeria;

import main.java.open.dpoo.exception.IncorrectOfferException;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.Administrator;
import main.java.open.dpoo.model.usuarios.Cashier;
import main.java.open.dpoo.model.usuarios.Purchaser;

import java.util.Date;

/** The Bill class represents a purchase bill for art pieces in the gallery. */
public class Bill {
    private final Piece piece;
    private final double offer;
    private Date date = new Date();

    /**
     * Constructs a Bill object for normal purchases.
     *
     * @param administrator The administrator overseeing the purchase
     * @param inventory     The inventory of the gallery
     * @param cashier       The cashier handling the transaction
     * @param purchaser     The purchaser making the purchase
     * @param piece         The piece being purchased
     * @param offer         The amount offered for the piece
     * @throws IncorrectOfferException If the offer is not valid for the piece
     */
    public Bill(Administrator administrator, Inventory inventory, Cashier cashier, Purchaser purchaser, Piece piece,
                double offer) throws IncorrectOfferException {
        if (administrator == null) {
            throw new IncorrectOfferException("No se puede realizar la compra sin un administrador.");
        }
        if (piece.getValoration().getFixValue() != 0 && offer >= piece.getValoration().getFixValue()) {
            this.offer = offer;
            this.piece = piece;
            inventory.removePieceVEN(piece);
            cashier.addBill(this);
        } else if (piece.getValoration().getFixValue() == 0 && offer >= piece.getValoration().getMinValue()) {
            this.offer = offer;
            this.piece = piece;
            inventory.removePieceVEN(piece);
            cashier.addBill(this);
        } else {
            throw new IncorrectOfferException("Esta oferta no es válida para esta pieza. Debe acudir a las subastas.");
        }
    }

    /**
     * Constructs a Bill object for auction purchases.
     *
     * @param piece   The piece being purchased
     * @param offer   The amount offered for the piece
     * @param auction The auction where the purchase is made
     * @throws IncorrectOfferException If the piece is not in an auction
     */
    public Bill(Piece piece, double offer, Auction auction) throws IncorrectOfferException {
        if (auction == null) {
            throw new IncorrectOfferException("Esta pieza no está en subasta.");
        }
        this.piece = piece;
        this.offer = offer;
    }

    /**
     * Sets the date of the bill.
     *
     * @param date The date of the bill
     * @return The Bill object for method chaining
     */
    public Bill setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override public String toString() {
        return "Bill{" + "piece=" + piece + ", offer=" + offer + ", date=" + date + '}';
    }

    public Piece getPiece() {
        return piece;
    }

    public double getOffer() {
        return offer;
    }

    public Date getDate() {
        return date;
    }
}
