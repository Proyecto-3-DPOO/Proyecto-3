package main.java.open.dpoo.model.usuarios;

import main.java.open.dpoo.model.galeria.AuctionOffer;
import main.java.open.dpoo.model.galeria.Bill;
import main.java.open.dpoo.model.galeria.piece.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * The Purchaser class represents a user with the role of a purchaser in the system.
 * It extends the BaseUser class and includes additional attributes and methods specific to purchasers.
 */
public class Purchaser extends BaseUser {
    private final Set<Bill> previousPurchases = new HashSet<>();
    private final Set<AuctionOffer> offers = new HashSet<>();
    private boolean verified = false;

    /**
     * Constructor for creating a Purchaser instance.
     *
     * @param name     The name of the purchaser.
     * @param document The document number of the purchaser.
     * @param phone    The phone number of the purchaser.
     * @param login    The login username of the purchaser.
     * @param password The login password of the purchaser.
     */
    public Purchaser(String name, String document, String phone, String login, String password) {
        super(name, document, phone, login, password);
    }

    /**
     * Adds a purchased piece to the purchaser's set of previous purchases.
     *
     * @param bill The bill representing the purchased piece.
     * @return True if the purchase is successfully added, false otherwise.
     */
    public boolean buyPiece(Bill bill) {
        return previousPurchases.add(bill);
    }

    /**
     * Makes an offer for an artwork in an auction.
     *
     * @param piece The piece being auctioned.
     * @param offer The amount of the offer.
     * @return True if the offer is successfully made, false otherwise.
     */
    public boolean makeAuctionOffer(Piece piece, double offer) {
        return offers.add(new AuctionOffer(piece, offer));
    }

    /**
     * Verifies the purchaser, indicating that they are a verified user in the system.
     */
    public void verify() {
        verified = true;
    }

    /**
     * Adds an offer to the purchaser's set of offers.
     *
     * @param offer The offer to be added.
     */
    public void addOffer(AuctionOffer offer) {
        offers.add(offer);
    }

    /**
     * Overrides the default toString method to provide a custom string representation of the Purchaser object.
     *
     * @return A string representation of the Purchaser object.
     */
    @Override public String toString() {
        return "Purchaser{" + "name='" + name + '\'' + ", document='" + document + '\'' + ", phone='" + phone + '\'' +
                ", login='" + login + '\'' + ", password='" + password + '\'' + ", previousPurchases=" +
                previousPurchases + ", offers=" + offers + ", verified=" + verified + '}';
    }

    public Set<Bill> getPreviousPurchases() {
        return previousPurchases;
    }

    public Set<AuctionOffer> getOffers() {
        return offers;
    }

    public boolean isVerified() {
        return verified;
    }
}
