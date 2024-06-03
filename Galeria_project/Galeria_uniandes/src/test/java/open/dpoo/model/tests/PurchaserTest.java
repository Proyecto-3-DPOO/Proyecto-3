package open.dpoo.model.tests;
import open.dpoo.model.galeria.AuctionOffer;
import open.dpoo.model.galeria.Bill;
import open.dpoo.model.galeria.piece.Piece;
import open.dpoo.model.usuarios.Purchaser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PurchaserTest {

    private Purchaser purchaser;
    private Piece piece;
    private AuctionOffer auctionOffer;

    @BeforeEach
    void setUp() {
        purchaser = new Purchaser("John Doe", "1234567890", "1234567890", "john@example.com", "password");
        piece = new Piece("Test Piece", "Test Artist", 2020);
        auctionOffer = new AuctionOffer(piece, 100.0);
    }

    @Test
    void testBuyPiece() {
        Bill bill = new Bill(null, null, null, purchaser, piece, 100.0);
        assertTrue(purchaser.buyPiece(bill));
    }

    @Test
    void testMakeAuctionOffer() {
        assertTrue(purchaser.makeAuctionOffer(piece, 100.0));
    }

    @Test
    void testVerify() {
        purchaser.verify();
        assertTrue(purchaser.isVerified());
    }

    @Test
    void testAddOffer() {
        purchaser.addOffer(auctionOffer);
        assertTrue(purchaser.getOffers().contains(auctionOffer));
    }
}