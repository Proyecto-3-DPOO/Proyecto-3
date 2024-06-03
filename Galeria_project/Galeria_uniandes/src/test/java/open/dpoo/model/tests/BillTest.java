package open.dpoo.model.tests;

import open.dpoo.exception.IncorrectOfferException;
import open.dpoo.model.galeria.piece.Piece;
import open.dpoo.model.usuarios.Administrator;
import open.dpoo.model.usuarios.Cashier;
import open.dpoo.model.usuarios.Purchaser;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testNormalPurchase() {
        Administrator administrator = new Administrator();
        Inventory inventory = new Inventory();
        Cashier cashier = new Cashier();
        Purchaser purchaser = new Purchaser();
        Piece piece = new Piece();
        piece.getValoration().setFixValue(100.0);
        double offer = 120.0;

        Bill bill = assertDoesNotThrow(() -> new Bill(administrator, inventory, cashier, purchaser, piece, offer));
        assertEquals(bill.getOffer(), offer);
        assertEquals(bill.getPiece(), piece);
    }

    @Test
    void testNormalPurchaseWithInvalidOffer() {
        Administrator administrator = new Administrator();
        Inventory inventory = new Inventory();
        Cashier cashier = new Cashier();
        Purchaser purchaser = new Purchaser();
        Piece piece = new Piece();
        piece.getValoration().setFixValue(100.0);
        double offer = 80.0;

        IncorrectOfferException exception = assertThrows(IncorrectOfferException.class, () ->
                new Bill(administrator, inventory, cashier, purchaser, piece, offer));

        assertEquals("Esta oferta no es válida para esta pieza. Debe acudir a las subastas.", exception.getMessage());
    }

    @Test
    void testAuctionPurchase() {
        Piece piece = new Piece();
        double offer = 120.0;
        Auction auction = new Auction();

        Bill bill = assertDoesNotThrow(() -> new Bill(piece, offer, auction));
        assertEquals(bill.getOffer(), offer);
        assertEquals(bill.getPiece(), piece);
    }

    @Test
    void testAuctionPurchaseWithNullAuction() {
        Piece piece = new Piece();
        double offer = 120.0;

        IncorrectOfferException exception = assertThrows(IncorrectOfferException.class, () ->
                new Bill(piece, offer, null));

        assertEquals("Esta pieza no está en subasta.", exception.getMessage());
    }

    @Test
    void testSetDate() {
        Bill bill = new Bill(new Piece(), 100.0, new Auction());
        Date date = new Date();
        bill.setDate(date);
        assertEquals(date, bill.getDate());
    }
}