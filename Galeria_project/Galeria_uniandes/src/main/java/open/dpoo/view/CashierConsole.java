package main.java.open.dpoo.view;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.exception.AccountException;
import main.java.open.dpoo.exception.InvalidInputException;
import main.java.open.dpoo.model.galeria.Bill;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.Administrator;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;
import main.java.open.dpoo.model.usuarios.Purchaser;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * The CashierConsole class represents the user interface for the {@link Cashier} role.
 * It provides functionality for cashiers to interact with the system, including viewing sales, artworks,
 * participating in auctions, and selling artworks to purchasers.
 */
public class CashierConsole {
    /** Logger for logging console operations. */
    private final static Logger logger = Logger.getLogger(CashierConsole.class.getName());
    /** The Cashier user associated with this console instance. */
    private final Cashier user;

    /**
     * Constructor for CashierConsole.
     *
     * @param user The Cashier user associated with this console.
     */
    CashierConsole(Cashier user) {this.user = user;}

    /**
     * Displays the registration form for a new {@link ConsoleUtils#getBaseRegister(Scanner)} user.
     *
     * @param sc Scanner for user input.
     * @return An array of six elements: {name, document, phone, login, password, adminDocument}.
     */
    static String[] showRegister(Scanner sc) {
        String[] register = new String[6];
        System.arraycopy(ConsoleUtils.getBaseRegister(sc), 0, register, 0, 5);
        System.out.println("Ingrese el documento del administrador:");
        register[5] = sc.next();
        return register;
    }

    /**
     * Main method to start the Cashier console.
     * Displays the main menu and handles user input to perform various actions.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.showMenu();
        int option;

        do try {
            System.out.println("Escoge...");
            option = sc.nextInt();
            if (option == 1) {
                logger.info("[CashierC::main] Iniciar sesión");
                String[] login = ConsoleUtils.showLogin(sc);
                BaseUser log = AccountManager.login(login[0], login[1]);
                if (log instanceof Cashier) {
                    new CashierConsole((Cashier) log).startApp(sc);
                } else {
                    logger.info("[CashierC::main] Ingreso en aplicacion incorrecta");
                    throw new AccountException("Ingreso en aplicacion incorrecta");
                }
            } else if (option == 2) {
                logger.info("[CashierC::main] Registrarse");
                String[] register = showRegister(sc);
                Optional<Administrator> administrator = AccountManager.searchAdmin(register[5]);
                if (!administrator.isPresent()) {
                    logger.warning("[CashierC::main] Administrador no encontrado");
                    throw new AccountException("Administrador no encontrado");
                }
                Cashier log = new Cashier(register[0], register[1], register[2], register[3], register[4],
                        administrator.get());
                AccountManager.register(log);
                new CashierConsole(log).startApp(sc);

            }
        } catch (InputMismatchException e) {
            logger.warning("[CashierC::main] Opción inválida");
            System.err.println("Opción inválida");
            option = 0;
        }
        while (option >= 0 && option < 3);
        logger.info("[CashierC::main] Salir");
        System.exit(0);
    }

    /**
     * Starts the application for the Cashier role after successful login.
     * Displays the main menu for cashier-related tasks and handles user input accordingly.
     *
     * @param sc Scanner for user input.
     */
    private void startApp(Scanner sc) {
        logger.info("[CashierC::startApp] Inicio de la aplicacion (Cajero)");
        System.out.println("Bienvenido " + user.getName());
        showMenu0();
        int option;
        do {
            System.out.println("Escoge...");
            option = sc.nextInt();
            if (option == 1) {
                viewSales();
            } else if (option == 2) {
                ConsoleUtils.viewAuctions();
            } else if (option == 3) {
                ConsoleUtils.viewAllArtworks();
            } else if (option == 4) {
                ConsoleUtils.viewArtwork(sc, logger);
            } else if (option == 5) {
                sellArtwork(sc);
            } else if (option == 0) {
                logger.info("[CashierC::startApp] Salir");
            } else {
                logger.warning("[CashierC::startApp] Opción inválida");
                throw new InvalidInputException("Opción inválida");
            }
        } while (option != 0);
    }

    /**
     * Sells an artwork to a purchaser.
     * Collects input from the cashier regarding the artwork to be sold, the purchaser, and the sale amount,
     * and generates a bill for the transaction.
     *
     * @param sc Scanner for user input.
     */
    private void sellArtwork(Scanner sc) {
        System.out.println("Ingrese el id de la obra a vender:");
        long id = sc.nextLong();
        ;
        Optional<Piece> pieceOptional = Inventory.getInstance().getPieceById(id);
        if (pieceOptional.isPresent()) {
            Piece piece = pieceOptional.get();
            System.out.println("Ingrese el documento del comprador:");
            String purchaserDocument = sc.next();
            Optional<BaseUser> purchaserOptional = AccountManager.searchByDoc(purchaserDocument);
            if (!purchaserOptional.isPresent() || !(purchaserOptional.get() instanceof Purchaser)) {
                logger.warning("[CashierC::sellArtwork] Comprador no encontrado");
                throw new AccountException("Comprador no encontrado");
            }
            Purchaser purchaser = (Purchaser) purchaserOptional.get();
            System.out.println("Ingrese el monto de la venta:");
            double amount = sc.nextDouble();
            ;
            new Bill(user.getAdministrator(), Inventory.getInstance(), user, purchaser, piece, amount);
        }
    }

    /** Displays the sales made by the cashier. */
    private void viewSales() {
        user.getBills().forEach(System.out::println);
    }

    /** Displays the main menu options for the {@link Cashier} role */
    private void showMenu0() {
        System.out.println("1. Ver ventas");
        System.out.println("2. Ver subastas");
        System.out.println("3. Ver obras");
        System.out.println("4. Ver obra");
        System.out.println("5. Vender obra");

        System.out.println("0. Salir");
    }
}
