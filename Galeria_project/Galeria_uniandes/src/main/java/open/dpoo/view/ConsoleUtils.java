package main.java.open.dpoo.view;

import main.java.open.dpoo.exception.InvalidInputException;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;

import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * The ConsoleUtils class provides a user interface for the art gallery for different user roles.
 * The interface is presented in the console.
 * Depending on the user role, different options are presented.
 * The user roles include {@link open.dpoo.model.usuarios.Administrator}, {@link open.dpoo.model.usuarios.Cashier}, {@link open.dpoo.model.usuarios.Purchaser}, and the default user {@link open.dpoo.model.usuarios.BaseUser}.
 */
public class ConsoleUtils {

    /** Displays the initial menu of the art gallery. */
    static void showMenu() {
        System.out.println("Bienvenido a la galería de arte");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
    }

    /**
     * Collects information to log in.
     *
     * @param sc Scanner to read user input.
     * @return An array of two elements: {nickname/login, password}
     */
    static String[] showLogin(Scanner sc) {
        String[] login = new String[2];
        System.out.println("Ingrese su nombre de usuario:");
        login[0] = sc.next();
        System.out.println("Ingrese su contrasenia");
        login[1] = sc.next();
        return login;
    }

    /**
     * Collects information for registration.
     *
     * @param sc Scanner to read user input.
     * @return An array of five elements: {name, document, phone, login, password}
     */
    static String[] getBaseRegister(Scanner sc) {
        String[] register = new String[5];
        System.out.println("Registrarse");
        System.out.println("Ingrese su nombre:");
        register[0] = sc.next();
        System.out.println("Ingrese su documento:");
        register[1] = sc.next();
        System.out.println("Ingrese su teléfono:");
        register[2] = sc.next();
        System.out.println("Ingrese su nombre de usuario:");
        register[3] = sc.next();
        System.out.println("Ingrese su contraseña:");
        register[4] = sc.next();
        return register;
    }

    /** Displays all artworks available in the inventory. */
    static void viewAllArtworks() {
        Inventory.getInstance().getPieces().forEach(System.out::println);
    }

    /**
     * Displays details of a specific artwork identified by its ID.
     *
     * @param sc     Scanner to read user input.
     * @param logger Logger for logging errors or warnings.
     */
    static void viewArtwork(Scanner sc, Logger logger) {
        System.out.println("Ingrese el id de la obra:");
        long id = sc.nextLong();
        Optional<Piece> piece = Inventory.getInstance().getPieceById(id);
        if (piece.isPresent()) {
            System.out.println(piece.get());
        } else {
            logger.warning("[AdministratorC::viewArtwork] Obra no encontrada");
            throw new InvalidInputException("Obra no encontrada");
        }
    }

    /** Displays all auctions available in the inventory. */
    static void viewAuctions() {
        Inventory.getInstance().getAuctions().forEach(System.out::println);
    }

    /** Displays the inventory status including pieces on exhibition, stored pieces, sold pieces, and pieces in auction. */
    static void viewInventory() {
        System.out.println("Obras en exhibicion:");
        Inventory.getInstance().getExhibition().forEach(System.out::println);
        System.out.println("Obras en bodega:");
        Inventory.getInstance().getStored().forEach(System.out::println);
        System.out.println("Obras vendidas:");
        Inventory.getInstance().getSold().forEach(System.out::println);
        System.out.println("Obras en subasta:");
        Inventory.getInstance().getAuctions().forEach(System.out::println);
    }

}
