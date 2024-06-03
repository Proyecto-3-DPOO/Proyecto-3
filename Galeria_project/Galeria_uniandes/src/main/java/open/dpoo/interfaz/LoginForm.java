package main.java.open.dpoo.interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import main.java.open.dpoo.model.usuarios.BaseUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginForm extends JFrame implements ActionListener {
    
    private static final String REGISTER = "Register";

    private static final String LOGIN = "Login";

    private static final String USERNAME = "Username";

    private static final String PASSWORD = "Password";

    private JTextField userField = new JTextField();

    private JTextField passwordField= new JTextField();

    private static String name;

    private static String password;

    private MainInterface main = new MainInterface();

    private Color backgroundColor = getBackground();


    public LoginForm() {
        setTitle("Login Form");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración del panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(backgroundColor);

        // Configuración del panel de bienvenida
        JPanel panelWelcome = createWelcomePanel();
        panelPrincipal.add(panelWelcome, BorderLayout.NORTH);

        // Configuración del panel de registro
        JPanel panelRegister = createRegisterPanel();
        panelPrincipal.add(panelRegister, BorderLayout.CENTER);

        // Configuración del panel de login
        JPanel panelLogin = createLoginPanel();
        panelPrincipal.add(panelLogin, BorderLayout.SOUTH);

        // Añadir panel principal al marco
        add(panelPrincipal, BorderLayout.NORTH);
    }

    
    
    private JPanel createWelcomePanel() {
        JPanel panelWelcome = new JPanel(new BorderLayout());
        JLabel welLabel = new JLabel("Bienvenido a la ", SwingConstants.CENTER);
        welLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panelWelcome.add(welLabel, BorderLayout.CENTER);
        panelWelcome.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panelWelcome;
    }

    private JPanel createRegisterPanel() {
        JPanel panelRegister = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel regLabel = new JLabel("Si no tienes cuenta");
        regLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton regButton = new JButton("Regístrate");
        InterfazUtilities.buttonModel(regButton, backgroundColor, null);
        regButton.addActionListener(this);
        regButton.setActionCommand(REGISTER);
        panelRegister.add(regLabel);
        panelRegister.add(regButton);
        return panelRegister;
    }

    private JPanel createLoginPanel() {
        JPanel panelLogin = new JPanel(new GridLayout(3, 1));
        userField.setBorder(new TitledBorder("Usuario"));
        InterfazUtilities.textFieldModel(userField, backgroundColor, null);
        userField.addActionListener(this);
        userField.setActionCommand(USERNAME);
        passwordField.setBorder(new TitledBorder("Contraseña"));
        InterfazUtilities.textFieldModel(passwordField, backgroundColor, null);
        passwordField.addActionListener(this);
        passwordField.setActionCommand(PASSWORD);
        panelLogin.add(userField);
        panelLogin.add(passwordField);
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JButton loginButton = new JButton("Inciar Sesion");

        InterfazUtilities.buttonModel(loginButton, backgroundColor, null);
        loginButton.addActionListener(this);
        loginButton.setActionCommand(LOGIN);
        panelLogin.add(loginButton);
        return panelLogin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(REGISTER)) {
            RegisterDialog dialog = new RegisterDialog(this);
            dialog.setVisible(true);
        }
        else if(command.equals(LOGIN)){
            name = userField.getText();
            password = passwordField.getText(); 
            System.out.println("Login: " + e.getActionCommand());
            BaseUser userTem = main.giveUser(name, password);
            
            if (userTem != null){
                MainInterface mainInterface = new MainInterface();
                mainInterface.setVisible(true);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

        }
    }
}
