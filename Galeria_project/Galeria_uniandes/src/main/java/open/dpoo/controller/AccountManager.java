package main.java.open.dpoo.controller;

import main.java.open.dpoo.model.usuarios.Administrator;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The AccountManager class provides functionality for managing user accounts,
 * including registration, login, and searching for specific types of users.
 */
public class AccountManager {

    /**
     * Logger for logging account management operations.
     */
    private static final Logger logger = Logger.getLogger(AccountManager.class.getName());

    /**
     * Map containing registered users, with the login name as the key and the BaseUser object as the value.
     */
    private static final Map<String, BaseUser> users = new HashMap<>();

    /**
     * Static block to initialize the users map with a default super admin user.
     */
    static {
        users.put("superadmin",
                new Administrator("Super Admin", "00000000000", "00000000000", "superadmin", "superadmin"));
    }

    /**
     * Registers a new user if the login name is not already taken.
     *
     * @param user The BaseUser object to register.
     */
    public static void register(BaseUser user) {
        users.putIfAbsent(user.getLogin(), user);
        logger.info("[AccountManager::register] Registered user: " + user);
    }

    /**
     * Logs in a user with the provided login name and password.
     *
     * @param login    The login name of the user.
     * @param password The password of the user.
     * @return The BaseUser object if login is successful, otherwise null.
     */
    public static BaseUser login(String login, String password) {
        BaseUser user = users.get(login);
        if (user != null && user.getPassword().equals(password)) {
            logger.info("[AccountManager::login] User logged in: " + user);
            return user;
        }
        logger.warning("[AccountManager::login] Invalid login or password");
        return null;
    }

    /**
     * Searches for an administrator user in the registered users.
     *
     * @return An Optional containing the found Administrator object, or empty if no administrator is found.
     */
    public static Optional<Administrator> searchAdmin() {
        for (BaseUser user : users.values()) {
            if (user instanceof Administrator) {
                logger.info("[AccountManager::searchAdmin*] Found administrator: " + user);
                return Optional.of((Administrator) user);
            }
        }
        logger.warning("[AccountManager::searchAdmin*] No administrator found");
        return Optional.empty();
    }

    /**
     * Searches for a cashier user in the registered users.
     *
     * @return An Optional containing the found Cashier object, or empty if no cashier is found.
     */
    public static Optional<Cashier> searchCashier() {
        for (BaseUser user : users.values()) {
            if (user instanceof Cashier) {
                logger.info("[AccountManager::searchCashier*] Found cashier: " + user);
                return Optional.of((Cashier) user);
            }
        }
        logger.warning("[AccountManager::searchCashier*] No cashier found");
        return Optional.empty();
    }

    /**
     * Searches for an administrator user with the specified document number.
     *
     * @param document The document number to search for.
     * @return An Optional containing the found Administrator object, or empty if no matching administrator is found.
     * If document is null, returns the result of searchAdmin() method.
     */
    public static Optional<Administrator> searchAdmin(String document) {
        if (document == null) return searchAdmin();
        for (BaseUser user : users.values()) {
            if (user instanceof Administrator) {
                Administrator administrator = (Administrator) user;
                if (administrator.getDocument().equals(document)) {
                    logger.info("[AccountManager::searchAdmin] Found administrator: " + administrator);
                    return Optional.of(administrator);
                }
            }
        }
        logger.warning("[AccountManager::searchAdmin] No administrator found with document: " + document);
        return Optional.empty();
    }

    /**
     * Searches for a user with the specified document number.
     *
     * @param document The document number to search for.
     * @return An Optional containing the found BaseUser object, or empty if no matching user is found.
     */
    public static Optional<BaseUser> searchByDoc(String document) {
        for (BaseUser user : users.values()) {
            if (user.getDocument().equals(document)) {
                logger.info("[AccountManager::searchByDoc] Found user: " + user);
                return Optional.of(user);
            }
        }
        logger.warning("[AccountManager::searchByDoc] No user found with document: " + document);
        return Optional.empty();
    }
    
   public static void paymentRegister(){
        
   }
}

