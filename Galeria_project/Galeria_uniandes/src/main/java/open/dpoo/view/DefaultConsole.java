package main.java.open.dpoo.view;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.exception.AccountException;
import main.java.open.dpoo.model.usuarios.Administrator;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;
import main.java.open.dpoo.model.usuarios.Purchaser;

import javax.swing.*;
import java.util.Scanner;
import java.util.logging.Logger;

/** Clase que representa la interfaz de usuario para el rol de usuario por defecto (basicamente el resto de herederos de {@link BaseUser}). */
public class DefaultConsole {
    private final static Logger logger = Logger.getLogger(DefaultConsole.class.getName());
    private final BaseUser user;

    public DefaultConsole(BaseUser user) {this.user = user;}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.showMenu();
        String[] login = ConsoleUtils.showLogin(sc);
        BaseUser log = AccountManager.login(login[0], login[1]);
        if (log instanceof Administrator || log instanceof Cashier || log instanceof Purchaser) {
            logger.info("[DefaultC::main] Ingreso en aplicacion incorrecta");
            throw new AccountException("Ingreso en aplicacion incorrecta");
        } else if (log != null) {
            new DefaultConsole(log).startApp();
        } else {
            logger.warning("[DefaultC::main] Ingreso en aplicacion incorrecta");
            throw new AccountException("Ingreso en aplicacion incorrecta");
        }
    }

    /**
     * Inicia la aplicacion para el rol de usuario por defecto.
     * No implementado.
     */
    @SuppressWarnings("MagicConstant") private void startApp() {
        logger.info("[DefaultC::startApp] Inicio de la aplicacion (Caso por defecto) -> No implementado");
        JOptionPane.showMessageDialog(null, "Esto no es relevante para la rubrica", user.getName(), 1);
    }
}
