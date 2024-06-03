package main.java.open.dpoo.view;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.exception.AccountException;
import main.java.open.dpoo.exception.InvalidInputException;
import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.AuctionOffer;
import main.java.open.dpoo.model.galeria.Bill;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;
import main.java.open.dpoo.model.usuarios.Purchaser;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The PurchaserConsole class represents the user interface for the {@link Purchaser} role.
 * It provides functionality for purchasers to interact with the system, including viewing artworks,
 * buying artworks, and participating in auctions.
 */
public class PurchaserConsole {
    /** Logger for logging console operations. */
    private final static Logger logger = Logger.getLogger(PurchaserConsole.class.getName());
    /** The Purchaser user associated with this console instance. */
    private final Purchaser user;

    /**
     * Constructor for PurchaserConsole.
     *
     * @param user The Purchaser user associated with this console.
     */
    PurchaserConsole(Purchaser user) {this.user = user;}

    /**
     * Displays the registration form for a new Purchaser user. {@link ConsoleUtils#getBaseRegister(Scanner)}
     *
     * @param sc Scanner for user input.
     * @return An array of five elements: {name, document, phone, login, password}.
     */
    static String[] showRegister(Scanner sc) {
        return ConsoleUtils.getBaseRegister(sc);
    }

    /**
     * Main method to start the Purchaser console.
     * Displays the main menu and handles user input to perform various actions.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.showMenu();
        int option = sc.nextInt();
        ;
        if (option == 1) {
            logger.info("[PurchaserC::main] Iniciar sesión");
            String[] login = ConsoleUtils.showLogin(sc);
            BaseUser log = AccountManager.login(login[0], login[1]);
            if (log instanceof Purchaser) {
                new PurchaserConsole((Purchaser) log).startApp(sc);
            } else {
                logger.info("[PurchaserC::main] Ingreso en aplicacion incorrecta");
                throw new AccountException("Ingreso en aplicacion incorrecta");
            }
        } else if (option == 2) {
            logger.info("[PurchaserC::main] Registrarse");
            String[] register = showRegister(sc);
            Purchaser log = new Purchaser(register[0], register[1], register[2], register[3], register[4]);
            AccountManager.register(log);
            new PurchaserConsole(log).startApp(sc);
        } else if (option == 3) {
            logger.info("[PurchaserC::main] Salir");
            System.exit(0);
        } else {
            logger.warning("[PurchaserC::main] Opción inválida");
            throw new InvalidInputException("Opción inválida");
        }
    }

    /**
     * Starts the application for the {@link Purchaser} role after successful login.
     * Displays the main menu for purchaser-related tasks and handles user input accordingly.
     *
     * @param sc Scanner for user input.
     */
    private void startApp(Scanner sc) {
        logger.info("[PurchaserC::startApp] Inicio de la aplicacion (Comprador)");
        System.out.println("Bienvenido " + user.getName());
        showMenu0();
        int option;
        do {
            System.out.println("Escoge...");
            option = sc.nextInt();
            if (option == 1) {
                ConsoleUtils.viewAllArtworks();
            } else if (option == 2) {
                buyArtwork(sc);
            } else if (option == 3) {
                participateAuction(sc);
            } else if (option == 0) {
                logger.info("[PurchaserC::startApp] Salir");
            } else {
                logger.warning("[PurchaserC::startApp] Opción inválida");
                throw new InvalidInputException("Opción inválida");
            }
        } while (option != 0);
    }

    /**
     * Allows the purchaser to participate in an auction.
     * Displays available auctions and allows the purchaser to place bids on artworks.
     *
     * @param sc Scanner for user input.
     */
    private void participateAuction(Scanner sc) {
        System.out.println("Quieres ver las subastas a las que puedes ingresar? (S/N)");
        List<Auction> collect = Inventory.getInstance().getAuctions().stream()
                .filter(auction -> auction.getStartAuctionDate().before(new Date())).collect(Collectors.toList());
        if (sc.next().equalsIgnoreCase("s")) {
            int bound = collect.size();
            for (int i = 0; i < bound; i++) {
                System.out.printf("%03d ----------%n", i);
                List<Piece> piecesAuction = collect.get(i).getPiecesAuction();
                for (Piece piece : piecesAuction) {
                    System.out.println(piece.getId() + " " + piece.getTitle());
                }
                System.out.println("--------------");
            }
        }
        System.out.println("Ingrese el id de la subasta a la que desea participar:");
        int id = sc.nextInt();
        ;
        collect.get(id).addPurchaser(user);
        System.out.println("Ingrese el id de la obra a la que desea participar:");
        int pieceId = sc.nextInt();
        ;
        Optional<Piece> pieceById = Inventory.getInstance().getPieceById(pieceId);
        if (pieceById.isPresent()) {
            Piece piece = pieceById.get();
            System.out.println("Ingrese el monto de la oferta:");
            double amount = sc.nextDouble();
            ;
            user.addOffer(new AuctionOffer(piece, amount));
            logger.info("[PurchaserC::participateAuction] Oferta realizada");
        } else {
            logger.warning("[PurchaserC::participateAuction] Obra no encontrada");
            throw new InvalidInputException("Obra no encontrada");
        }
    }

    /**
     * Allows the purchaser to buy an artwork.
     * Collects input from the purchaser regarding the artwork to be purchased and the purchase amount,
     * and generates a bill for the transaction.
     *
     * @param sc Scanner for user input.
     */
    private void buyArtwork(Scanner sc) {
        System.out.println("Ingrese el id de la obra a comprar:");
        long id = sc.nextLong();
        ;
        Optional<Piece> pieceOptional = Inventory.getInstance().getPieceById(id);
        if (pieceOptional.isPresent()) {
            Piece piece = pieceOptional.get();
            System.out.println("Ingrese el monto de la compra:");
            double amount = sc.nextDouble();
            ;
            Optional<Cashier> assigned = AccountManager.searchCashier();
            if (!assigned.isPresent()) {
                logger.warning("[PurchaserC::buyArtwork] Cajero no encontrado");
                throw new AccountException("Cajero no encontrado");
            } else {
                new Bill(assigned.get().getAdministrator(), Inventory.getInstance(), assigned.get(), user, piece,
                        amount);
            }
        }
    }

    /** Displays the main menu options for the {@link Purchaser} role. */
    private void showMenu0() {
        System.out.println("1. Ver obras");
        System.out.println("2. Comprar obra");
        System.out.println("3. Participar en subasta");

        System.out.println("0. Salir");
    }
}
