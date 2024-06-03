package open.dpoo.model.tests;
import open.dpoo.exception.IncorrectOfferException;
import open.dpoo.model.galeria.Auction;
import open.dpoo.model.galeria.piece.Piece;
import open.dpoo.model.usuarios.Purchaser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    private Auction auction;
    private Piece piece;
    private Purchaser purchaser;

    @BeforeEach
    void setUp() {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour later
        auction = new Auction(startDate, endDate);
        piece = new Piece("Test Piece", "Test Artist", 2020);
        purchaser = new Purchaser("John Doe", "123456789", "1234567890", "john@example.com", null);
    }

    @Test
    void testAddPieceToAuction() {
        auction.addPiece(piece);
        assertTrue(auction.getPiecesAuction().contains(piece));
    }

    @Test
    void testAddPurchaserToAuction() {
        auction.addPurchaser(purchaser);
        assertTrue(auction.getPurchasers().contains(purchaser));
    }

    @Test
    void testAuctionPieceSuccess() {
        auction.addPiece(piece);
        auction.addPurchaser(purchaser);
        purchaser.makeAuctionOffer(piece, 200.0);
        auction.auction(piece);
        assertTrue(auction.getInventory().getSold().contains(piece));
    }

    @Test
    void testAuctionPieceFailureNotAuctioned() {
        piece.getAvailability().setAuctioned(false);
        assertThrows(IncorrectOfferException.class, () -> auction.auction(piece));
    }
}