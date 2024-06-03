package open.dpoo.model.tests;

import open.dpoo.exception.IncorrectOfferException;
import open.dpoo.model.galeria.Bill;
import open.dpoo.model.galeria.Inventory;
import open.dpoo.model.galeria.piece.Availability;
import open.dpoo.model.galeria.piece.Piece;
import open.dpoo.model.galeria.piece.Video;
import open.dpoo.model.usuarios.Administrator;
import open.dpoo.model.usuarios.Cashier;
import open.dpoo.model.usuarios.Purchaser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {

    private Administrator administrator;
    private Inventory inventory;
    private Cashier cashier;
    private Purchaser purchaser;
    private Piece piece;
    

    @BeforeEach
    void setUp() {
        administrator = new Administrator("John Doe", "123456789", "1234567890", "admin", "admin123");
        inventory = Inventory.getInstance();
        cashier = new Cashier(null, null, null, null, null, administrator);
        purchaser = new Purchaser(null, null, null, null, null);
        Piece piece = new Video(0, null, 0, null, null, null, null, null, null, null, 0, 0);
        piece.getAvailability().setBlocked(false);
    }

    @Test
    void testVerifyPurchaseSuccess() {
        assertTrue(administrator.verifyPurchase(cashier, purchaser, piece, 100.0));
    }

    @Test
    void testVerifyPurchaseFailurePieceBlocked() {
        piece.getAvailability().setBlocked(true);
        assertFalse(administrator.verifyPurchase(cashier, purchaser, piece, 100.0));
    }

    @Test
    void testVerifyPurchaseFailureIncorrectOffer() {
        assertThrows(IncorrectOfferException.class, () ->
                administrator.verifyPurchase(cashier, purchaser, piece, 80.0));
    }

    @Test
    void testAddPiece() {
        administrator.addPiece(piece, "exhibir");
        assertTrue(inventory.getExhibition().contains(piece));
    }

    @Test
    void testRemovePiece() {
        administrator.addPiece(piece, "exhibir");
        administrator.removePiece(piece, "exhibir");
        assertFalse(inventory.getExhibition().contains(piece));
    }

    @Test
    void testExhibitPiece() {
        administrator.exhibitPiece(piece);
        assertFalse(inventory.getExhibition().contains(piece));
    }
}