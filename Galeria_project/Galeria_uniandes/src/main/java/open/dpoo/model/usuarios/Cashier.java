package main.java.open.dpoo.model.usuarios;

import main.java.open.dpoo.model.galeria.Bill;
import main.java.open.dpoo.model.payment.Gateway;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The Cashier class represents a user with the role of a cashier in the system.
 * It extends the BaseUser class and includes additional attributes and methods specific to cashiers.
 */
public class Cashier extends BaseUser {
    private final Set<Bill> bills = new HashSet<>();
    private Administrator administrator;

    /**
     * Constructor for creating a Cashier instance.
     *
     * @param name           The name of the cashier.
     * @param document       The document number of the cashier.
     * @param phone          The phone number of the cashier.
     * @param login          The login username of the cashier.
     * @param password       The login password of the cashier.
     * @param administrator  The administrator associated with the cashier.
     */
    public Cashier(String name, String document, String phone, String login, String password,
                   Administrator administrator) {
        super(name, document, phone, login, password);
        this.administrator = administrator;
    }

    public String generateTransactionNumber(Gateway gateway){
        Random random = new Random();
        String numeroAleatorio = String.valueOf(random.nextInt(9999999 - 1000000 + 1) + 1000000);
        if (!gateway.getAccounts().containsKey(numeroAleatorio)){
            return numeroAleatorio;
        } else {
            return generateTransactionNumber(gateway);
        }
    }

    /**
     * Adds a bill to the cashier's set of bills.
     *
     * @param bill The bill to be added.
     */
    public void addBill(Bill bill) {bills.add(bill);}

    public Set<Bill> getBills() {
        return bills;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}
