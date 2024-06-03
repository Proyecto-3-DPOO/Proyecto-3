package main.java.open.dpoo.model.usuarios;

import main.java.open.dpoo.model.galeria.Bill;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;

/**
 * The Administrator class represents a user with administrative privileges in the system.
 * Administrators have access to functionalities such as verifying purchases, managing inventory,
 * and exhibiting pieces for sale.
 */
public class Administrator extends BaseUser {

    /** Constants representing different modes of inventory management. */
    private final String EXHIBIR = "exhibir", VENDER = "vender", BODEGA = "bodega";

    /** The inventory associated with the administrator. */
    private final Inventory inventory;

    /**
     * Constructor for creating an Administrator instance.
     *
     * @param name     The name of the administrator.
     * @param document The document number of the administrator.
     * @param phone    The phone number of the administrator.
     * @param login    The login username of the administrator.
     * @param password The login password of the administrator.
     */
    public Administrator(String name, String document, String phone, String login, String password) {
        super(name, document, phone, login, password);
        this.inventory = Inventory.getInstance();
    }

    /**
     * Verifies a purchase transaction initiated by a cashier.
     *
     * @param cashier   The cashier initiating the purchase.
     * @param purchaser The purchaser making the purchase.
     * @param piece     The piece being purchased.
     * @param offer     The offered amount for the purchase.
     * @return True if the purchase is verified and completed successfully, false otherwise.
     */
    public boolean verifyPurchase(Cashier cashier, Purchaser purchaser, Piece piece, double offer) {
        return purchaser.isVerified() && !piece.getAvailability().isBlocked() &&
                purchaser.buyPiece(new Bill(this, inventory, cashier, purchaser, piece, offer));
    }

    /**
     * Adds a piece to the inventory based on the specified mode.
     *
     * @param piece The piece to be added to the inventory.
     * @param mode  The mode indicating where the piece should be added (exhibir, vender, bodega).
     */
    public void addPiece(Piece piece, String mode) {
        if (EXHIBIR.equals(mode)) {
            inventory.addPieceEXH(piece);
        } else if (VENDER.equals(mode)) {
            inventory.addPieceVEN(piece);
        } else if (BODEGA.equals(mode)) {
            inventory.addPieceBOD(piece);
        }
    }

    /**
     * Removes a piece from the inventory based on the specified mode.
     *
     * @param piece The piece to be removed from the inventory.
     * @param mode  The mode indicating where the piece should be removed from (exhibir, vender, bodega).
     */
    public void removePiece(Piece piece, String mode) {
        if (EXHIBIR.equals(mode)) {
            inventory.removePieceEXH(piece);
        } else if (VENDER.equals(mode)) {
            inventory.removePieceVEN(piece);
        } else if (BODEGA.equals(mode)) {
            inventory.removePieceBOD(piece);
        }
    }

    /**
     * Exhibits a piece for sale in the inventory.
     *
     * @param piece The piece to be exhibited for sale.
     */
    public void exhibitPiece(Piece piece) {
        inventory.exhibitPiece(piece);
    }
}
