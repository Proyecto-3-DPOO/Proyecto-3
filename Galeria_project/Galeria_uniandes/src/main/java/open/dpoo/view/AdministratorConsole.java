package main.java.open.dpoo.view;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.exception.AccountException;
import main.java.open.dpoo.exception.InvalidInputException;
import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.*;
import main.java.open.dpoo.model.usuarios.Administrator;
import main.java.open.dpoo.model.usuarios.Author;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;
import main.java.open.dpoo.model.usuarios.Operator;
import main.java.open.dpoo.model.usuarios.Owner;
import main.java.open.dpoo.model.usuarios.Purchaser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The AdministratorConsole class represents the user interface for the {@link Administrator} role.
 * It provides functionality for administrators to interact with the system, including registering new users,
 * logging in, and performing various administrative tasks such as managing inventory, auctions, and artworks.
 */
public class AdministratorConsole {
    /** Logger for logging console operations. */
    private static final Logger logger = Logger.getLogger(AdministratorConsole.class.getName());
    /** The Administrator user associated with this console instance. */
    private final Administrator user;

    /**
     * Constructor for AdministratorConsole.
     *
     * @param user The Administrator user associated with this console.
     */
    AdministratorConsole(Administrator user) {this.user = user;}

    /**
     * Displays the registration form for a new user. {@link ConsoleUtils#getBaseRegister(Scanner)}
     *
     * @param sc Scanner for user input.
     * @return An array of five elements: {name, document, phone, login, password}.
     */
    public static String[] showRegister(Scanner sc) {
        return ConsoleUtils.getBaseRegister(sc);
    }

    /**
     * Main method to start the Administrator console.
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
                logger.info("[AdministratorC::main] Iniciar sesión");
                String[] login = ConsoleUtils.showLogin(sc);
                BaseUser log = AccountManager.login(login[0], login[1]);
                if (log instanceof Administrator) {
                    new AdministratorConsole((Administrator) log).startApp(sc);
                } else {
                    logger.info("[AdministratorC::main] Ingreso en aplicacion incorrecta");
                    throw new AccountException("Ingreso en aplicacion incorrecta");
                }
            } else if (option == 2) {
                logger.info("[AdministratorC::main] Registrarse");
                String[] register = showRegister(sc);
                Administrator log = new Administrator(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                new AdministratorConsole(log).startApp(sc);
            }
        } catch (InputMismatchException e) {
            logger.warning("[AdministratorC::main] Opción inválida");
            System.err.println("Opción inválida");
            option = 0;
        }
        while (option > 0 && option < 3);
        logger.info("[AdministratorC::main] Salir");
        System.exit(0);
    }

    /**
     * Starts the application for the {@link Administrator} role after successful login.
     * Displays the main menu for administrative tasks and handles user input accordingly.
     *
     * @param sc Scanner for user input.
     */
    private void startApp(Scanner sc) {
        logger.info("[AdministratorC::startApp] Inicio de la aplicacion (Administrador)");
        System.out.println("Bienvenido " + user.getName());
        showMenu0();
        int option;
        do {
            System.out.println("Escoge...");
            option = sc.nextInt();
            if (option == 1) {
                ConsoleUtils.viewInventory();
            } else if (option == 2) {
                ConsoleUtils.viewAuctions();
            } else if (option == 3) {
                startAuction(sc);
            } else if (option == 4) {
                addArtwork(sc);
            } else if (option == 5) {
                ConsoleUtils.viewArtwork(sc, logger);
            } else if (option == 6) {
                ConsoleUtils.viewAllArtworks();
            } else if (option == 7) {
                consignArtwork(sc);
            } else if (option == 8) {
                createAccount(sc);
            } else if (option == 0) {
                logger.info("[AdministratorC::startApp] Salir");
            } else {
                logger.warning("[AdministratorC::startApp] Opción inválida");
                throw new InvalidInputException("Opción inválida");
            }
        } while (option != 0);
    }

    /**
     * Creates a new user account based on the specified role.
     * Allows the administrator to create accounts for Cashiers, Purchasers, Authors, Operators, and Owners.
     *
     * @param sc Scanner for user input.
     */
    private void createAccount(Scanner sc) {
        System.out.println("Ingrese el rol del usuario a crear:");
        System.out.println("Cajero - Comprador - Administrador - Otro (Ingrese el nombre del rol)");
        String role = sc.next();
        switch (role) {
            case "Cajero": {
                String[] register = CashierConsole.showRegister(sc);
                Optional<Administrator> administrator = AccountManager.searchAdmin(register[5]);
                if (!administrator.isPresent()) {
                    logger.warning("[AdministratorC::createAccount] Administrador no encontrado");
                    throw new AccountException("Administrador no encontrado");
                }
                Cashier log = new Cashier(register[0], register[1], register[2], register[3], register[4],
                        administrator.get());
                AccountManager.register(log);
                break;
            }
            case "Comprador": {
                String[] register = PurchaserConsole.showRegister(sc);
                Purchaser log = new Purchaser(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                break;
            }
            case "Administrador": {
                String[] register = AdministratorConsole.showRegister(sc);
                Administrator log = new Administrator(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                break;
            }
            case "Autor": {
                String[] register = ConsoleUtils.getBaseRegister(sc);
                Author log = new Author(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                break;
            }
            case "Operador": {
                String[] register = ConsoleUtils.getBaseRegister(sc);
                Operator log = new Operator(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                break;
            }
            case "Duenio": {
                String[] register = ConsoleUtils.getBaseRegister(sc);
                Owner log = new Owner(register[0], register[1], register[2], register[3], register[4]);
                AccountManager.register(log);
                break;
            }
            default: {
                logger.warning("[AdministratorC::createAccount] Rol inválido");
                throw new InvalidInputException("Rol inválido");
            }
        }
    }

    /**
     * Adds a new artwork to the inventory.
     * Collects input from the administrator regarding the artwork details and adds it to the inventory.
     *
     * @param sc Scanner for user input.
     */
    private void addArtwork(Scanner sc) {
        System.out.println("Ingrese el titulo de la obra:");
        String title = sc.next();
        System.out.println("Ingrese el anio de la obra:");
        int year = sc.nextInt();
        System.out.println("Ingrese el lugar de origen de la obra:");
        Locale origin = new Locale("es", sc.next());
        System.out.println("Ingrese la descripcion de la obra:");
        String description = sc.next();
        System.out.println("Ingrese el documento de el/los autor/es de la obra (separado por coma):");
        String[] authors = sc.next().split(",");
        List<Author> authorsList = Arrays.stream(authors).map(AccountManager::searchByDoc).filter(Optional::isPresent)
                .map(Optional::get).filter(Author.class::isInstance).map(user -> (Author) user)
                .collect(Collectors.toList());

        System.out.println("Ingrese el documento del duenio de la obra:");
        String ownerDocument = sc.next();
        Optional<BaseUser> ownerOptional = AccountManager.searchByDoc(ownerDocument);
        if (!ownerOptional.isPresent() || !(ownerOptional.get() instanceof Owner)) {
            logger.warning("[AdministratorC::consignArtwork] Dueño no encontrado");
            throw new AccountException("Dueño no encontrado");
        }
        Owner owner = (Owner) ownerOptional.get();
        System.out.println("Bloqueada?");
        boolean blocked = sc.nextBoolean();
        System.out.println("Vendida?");
        boolean sold = sc.nextBoolean();
        System.out.println("Subastada?");
        boolean auctioned = sc.nextBoolean();
        Availability availability = new Availability(owner, blocked, sold, auctioned);

        System.out.println("Ingrese el valor fijo de la obra:");
        double fixedValue = sc.nextDouble();
        System.out.println("Ingrese el valor minimo de la obra en subasta:");
        double minValue = sc.nextDouble();
        System.out.println("Ingrese el valor inicial de la obra en subasta:");
        double initialValue = sc.nextDouble();
        Valoration valoration = new Valoration(fixedValue, minValue, initialValue);

        Date entryDate, limitDate;
        try {
            System.out.println("Ingrese la fecha de ingreso de la obra:");
            entryDate = SimpleDateFormat.getDateInstance().parse(sc.next());
            System.out.println("Ingrese la fecha limite de la obra:");
            limitDate = SimpleDateFormat.getDateInstance().parse(sc.next());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        TemporalLocation storage = new TemporalLocation(entryDate, limitDate);

        Piece piece = collectInput(title, year, origin, authorsList, description, availability, valoration, storage,
                sc);
        if (sold) {
            Inventory.getInstance().getSold().add(piece);
        }
        if (auctioned) {
            Date startAuctionDate, endAuctionDate;
            try {
                System.out.println("Ingrese el inicio de la subasta:");
                startAuctionDate = SimpleDateFormat.getDateInstance().parse(sc.next());
                System.out.println("Ingrese el fin de la subasta:");
                endAuctionDate = SimpleDateFormat.getDateInstance().parse(sc.next());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Inventory.getInstance().getAuction(startAuctionDate, endAuctionDate).addPiece(piece);
        }
        System.out.println("Exhibida o en bodega?");
        String location = sc.next().toLowerCase();
        if (location.contains("exh")) {
            Inventory.getInstance().addPieceEXH(piece);
        } else {
            Inventory.getInstance().addPieceBOD(piece);
        }
    }

    private Piece collectInput(String title, int year, Locale origin, List<Author> authorsList, String description,
                               Availability availability, Valoration valoration, TemporalLocation storage, Scanner sc) {
        System.out.println("Ingrese el tipo de obra (Pintura, Escultura, Fotografia, Video, Impresion):");
        String type = sc.next().toLowerCase();
        if (type.contains("pint")) {
            System.out.println("Ingrese el ancho de la obra:");
            int width = sc.nextInt();
            System.out.println("Ingrese el alto de la obra:");
            int height = sc.nextInt();
            System.out.println("Ingrese el estilo de la obra:");
            String style = sc.next();
            logger.info("[AdministratorC::collectInput] Creando pintura");
            return new Painting(Inventory.getId(), title, year, origin, authorsList, description, availability,
                    valoration, storage, style, height, width);
        } else if (type.contains("esc")) {
            System.out.println("Ingrese el ancho de la obra:");
            double width = sc.nextDouble();
            System.out.println("Ingrese el alto de la obra:");
            double height = sc.nextDouble();
            System.out.println("Ingrese la profundidad de la obra:");
            double depth = sc.nextDouble();
            System.out.println("Ingrese el peso de la obra:");
            double weight = sc.nextDouble();
            System.out.println("Es electrica?");
            boolean electric = sc.nextBoolean();
            Sculpture sculpture = new Sculpture(Inventory.getId(), title, year, origin, authorsList, description,
                    availability, valoration, storage, width, height, depth, weight, electric);
            System.out.println("Ingrese los materiales de la escultura (separados por coma):");
            Arrays.stream(sc.next().split(",")).forEach(sculpture::addMaterial);
            System.out.println("Ingrese los detalles de la escultura (separados por coma):");
            Arrays.stream(sc.next().split(",")).forEach(sculpture::addDetail);
            logger.info("[AdministratorC::collectInput] Creando escultura");
            return sculpture;
        } else if (type.contains("fot")) {
            System.out.println("Ingrese los megapixeles de la obra:");
            int megapixels = sc.nextInt();
            System.out.println("Ingrese el tamaño de la obra:");
            int size = sc.nextInt();
            System.out.println("Ingrese el lente de la obra:");
            String lens = sc.next();
            logger.info("[AdministratorC::collectInput] Creando fotografia");
            return new Photography(Inventory.getId(), title, year, origin, authorsList, description, availability,
                    valoration, storage, megapixels, size, lens);
        } else if (type.contains("vid")) {
            System.out.println("Ingrese los fps de la obra:");
            int fps = sc.nextInt();
            System.out.println("Ingrese la resolucion de la obra:");
            String resolution = sc.next();
            System.out.println("Ingrese la duracion de la obra:");
            int duration = sc.nextInt();
            logger.info("[AdministratorC::collectInput] Creando video");
            return new Video(Inventory.getId(), title, year, origin, authorsList, description, availability, valoration,
                    storage, resolution, fps, duration);
        } else if (type.contains("imp")) {
            System.out.println("Ingrese el tipo de hoja de la obra:");
            String paperType = sc.next();
            System.out.println("Ingrese el tipo de tinta de la obra:");
            String tintType = sc.next();
            System.out.println("Ingrese el tamaño de la obra:");
            String size = sc.next();
            System.out.println("Ingrese la calidad de la obra:");
            String quality = sc.next();
            System.out.println("Ingrese la tecnica de la obra:");
            String technique = sc.next();
            logger.info("[AdministratorC::collectInput] Creando impresion");
            return new Impression(Inventory.getId(), title, year, origin, authorsList, description, availability,
                    valoration, storage, paperType, tintType, size, quality, technique);
        }
        logger.warning("[AdministratorC::collectInput] Tipo de obra incorrecto");
        throw new InvalidInputException("Tipo de obra incorrecto");
    }

    /**
     * Consigns an artwork to an owner and marks it as blocked.
     * Temporarily stores the artwork and updates its availability status.
     *
     * @param sc Scanner for user input.
     */
    private void consignArtwork(Scanner sc) {
        System.out.println("Ingrese el documento del duenio de la obra:");
        String ownerDocument = sc.next();
        Optional<BaseUser> ownerOptional = AccountManager.searchByDoc(ownerDocument);
        if (!ownerOptional.isPresent() || !(ownerOptional.get() instanceof Owner)) {
            logger.warning("[AdministratorC::consignArtwork] Dueño no encontrado");
            throw new AccountException("Dueño no encontrado");
        }
        Owner owner = (Owner) ownerOptional.get();
        System.out.println("Ingrese el id de la obra a consignar:");
        long id = sc.nextLong();
        Optional<Piece> pieceOptional = owner.getPieceById(id);
        if (!pieceOptional.isPresent()) {
            logger.warning("[AdministratorC::consignArtwork] Obra no encontrada");
            throw new InvalidInputException("Obra no encontrada");
        }
        Piece piece = pieceOptional.get();
        piece.getAvailability().setBlocked(true);

        Date start, end;
        try {
            System.out.println("Desde que momento?");
            start = SimpleDateFormat.getDateInstance().parse(sc.next());
            System.out.println("Hasta que momento?");
            end = SimpleDateFormat.getDateInstance().parse(sc.next());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        piece.setStorage(new TemporalLocation(start, end));
        Inventory.getInstance().addPieceEXH(piece);
    }

    /**
     * Starts a new auction or forces the start of an existing auction.
     * Allows the administrator to initiate a new auction, force the start of an existing one,
     * or check for and start any ongoing auctions.
     *
     * @param sc Scanner for user input.
     */
    private void startAuction(Scanner sc) {
        System.out.println("Quieres forzar el inicio de una subasta, crear una o iniciar una ya creada? (F/C/N)");
        String option = sc.next().toLowerCase();
        if (option.equals("c")) {
            Date start, end;
            try {
                System.out.println("Ingrese la fecha de inicio de la subasta:");
                start = SimpleDateFormat.getDateInstance().parse(sc.next());
                System.out.println("Ingrese la fecha de fin de la subasta:");
                end = SimpleDateFormat.getDateInstance().parse(sc.next());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Inventory.getInstance().addAuction(start, end);
        } else if (option.equals("f")) {
            Date start, end;
            try {
                System.out.println("Ingrese el inicio de la subasta que busca forzar su inicio");
                start = SimpleDateFormat.getDateInstance().parse(sc.next());
                System.out.println("Ingrese el fin de la subasta que busca forzar su inicio");
                end = SimpleDateFormat.getDateInstance().parse(sc.next());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Optional<Auction> any = Inventory.getInstance().getAuctions().stream()
                    .filter(auction -> auction.getStartAuctionDate().equals(start) &&
                            auction.getEndAuctionDate().equals(end)).findAny();
            if (any.isPresent()) {
                Auction auction = any.get();
                System.out.println("Subasta activa\nObras en subasta:");
                auction.getPiecesAuction().stream().map(piece -> "> " + piece.getId() + " - " + piece.getTitle())
                        .forEach(System.out::println);
                System.out.println("Desea iniciar la subasta? (S/N)");
                if (sc.next().equalsIgnoreCase("s")) {
                    auction.getPiecesAuction().forEach(auction::auction);
                }
            } else {
                logger.warning("[AdministratorC::startAuction] Subasta no encontrada");
                throw new InvalidInputException("Subasta no encontrada");
            }

        } else {
            Date today = new Date();
            Optional<Auction> any = Inventory.getInstance().getAuctions().stream()
                    .filter(auction -> auction.getStartAuctionDate().before(today) &&
                            auction.getEndAuctionDate().after(today)).findAny();
            if (any.isPresent()) {
                Auction auction = any.get();
                System.out.println("Subasta activa\nObras en subasta:");
                auction.getPiecesAuction().stream().map(piece -> "> " + piece.getId() + " - " + piece.getTitle())
                        .forEach(System.out::println);
                System.out.println("Desea iniciar la subasta? (S/N)");
                if (sc.next().equalsIgnoreCase("s")) {
                    auction.getPiecesAuction().forEach(auction::auction);
                }
            } else {
                logger.warning("[AdministratorC::startAuction] No hay subastas activas");
                throw new InvalidInputException("No hay subastas activas");

            }
        }
    }

    /** Muestra el menú de opciones para el rol de {@link Administrator}. */
    private void showMenu0() {
        System.out.println("1. Ver inventario");
        System.out.println("2. Ver subastas");
        System.out.println("3. Empezar subasta");
        System.out.println("4. Agregar obra");
        System.out.println("5. Ver obra");
        System.out.println("6. Ver obras");
        System.out.println("7. Consignar obra");

        System.out.println("8. Crear cuenta");

        System.out.println("0. Salir");
    }
}
