package main.java.open.dpoo.model.usuarios;

/**
 * The BaseUser class represents a basic user within the system.
 * It provides common attributes and methods that are shared among different types of users.
 */
public class BaseUser {
    protected String name;
    protected String document;
    protected String phone;
    protected String login;
    protected String password;

    /**
     * Constructor for creating a BaseUser instance.
     *
     * @param name     The name of the user.
     * @param document The document number of the user.
     * @param phone    The phone number of the user.
     * @param login    The login username of the user.
     * @param password The login password of the user.
     */
    public BaseUser(String name, String document, String phone, String login, String password) {
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
