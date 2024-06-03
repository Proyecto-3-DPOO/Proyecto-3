package main.java.open.dpoo.model.galeria;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import main.java.open.dpoo.exception.IncorrectOfferException;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.Purchaser;

/** The Auction class represents an auction event for selling art pieces. */
public class Auction {
    private static final Logger logger = Logger.getLogger(Auction.class.getName());

    private final List<Piece> piecesAuction = new LinkedList<>();
    private final List<Purchaser> purchasers = new LinkedList<>();
    private final Inventory inventory = Inventory.getInstance();
    private final Date startAuctionDate;
    private final Date endAuctionDate;

    /**
     * Constructs an Auction object with specified start and end dates.
     *
     * @param startAuctionDate The start date of the auction
     * @param endAuctionDate   The end date of the auction
     */
    public Auction(Date startAuctionDate, Date endAuctionDate) {
        this.startAuctionDate = startAuctionDate;
        this.endAuctionDate = endAuctionDate;
    }

    /**
     * Adds a piece to the list of pieces available for auction.
     *
     * @param piece The piece to add to the auction
     */
    public void addPiece(Piece piece) {
        piecesAuction.add(piece);
    }

    /**
     * Adds a purchaser to the list of participants in the auction.
     *
     * @param purchaser The purchaser to add to the auction participants
     * @return The Auction object for method chaining
     */
    public Auction addPurchaser(Purchaser purchaser) {
        purchasers.add(purchaser);
        return this;
    }

    /**
     * Conducts the auction process for a specific piece identified by its ID.
     *
     * @param id The ID of the piece to be auctioned
     */
    public void auction(int id) {
        auction(piecesAuction.get(id));
    }

    public void auction(Piece auctionPiece) {
        logger.info("Auctioning piece: " + auctionPiece.getTitle());
        if (auctionPiece.getAvailability().isAuctioned()) {
            double maxOffer = auctionPiece.getValoration().getMinValue();
            Purchaser maxOfferPurchaser = null;
            for (Purchaser purchaser : purchasers) {
                for (AuctionOffer auctionOffer : purchaser.getOffers()) {
                    if (auctionOffer.getPiece().equals(auctionPiece)) {
                        if (auctionOffer.getOffer() >= maxOffer) {
                            maxOffer = auctionOffer.getOffer();
                            maxOfferPurchaser = purchaser;
                        }
                        purchaser.getOffers().remove(auctionOffer);
                        break;
                    }
                }
            }
            if (maxOfferPurchaser != null) {
                inventory.removePieceVEN(auctionPiece);
                inventory.getSold().add(auctionPiece);
                maxOfferPurchaser.buyPiece(new Bill(auctionPiece, maxOffer, this));
                logger.info("Piece auctioned successfully: " + auctionPiece.getTitle() + " - Sold to: " +
                        maxOfferPurchaser.getName() + " - Offer: " + maxOffer);
            }
        } else {
            throw new IncorrectOfferException("La pieza no está para subastar");
        }
    }

    @Override public String toString() {
        return "Auction{" + "startAuctionDate=" + startAuctionDate + ", endAuctionDate=" + endAuctionDate +
                ", piecesAuction=" + piecesAuction.stream().map(Piece::getTitle).collect(Collectors.toList()) + '}';
    }

    public List<Piece> getPiecesAuction() {
        return piecesAuction;
    }

    public List<Purchaser> getPurchasers() {
        return purchasers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Date getStartAuctionDate() {
        return startAuctionDate;
    }

    public Date getEndAuctionDate() {
        return endAuctionDate;
    }
}
