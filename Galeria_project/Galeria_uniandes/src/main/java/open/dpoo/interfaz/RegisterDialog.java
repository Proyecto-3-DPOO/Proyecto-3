package main.java.open.dpoo.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.java.open.dpoo.model.usuarios.BaseUser;

class RegisterDialog extends JDialog implements ActionListener  {

    private Color backgroundColor = getBackground();

    private MainInterface mainInterface = new MainInterface();

    private JTextField nameField = new JTextField();

    private JTextField docField = new JTextField();

    private JTextField phoneField = new JTextField();

    private JTextField userField = new JTextField();

    private JTextField passwordField = new JTextField();

    public static String name ;

    public static String document ;

    public static String phone ;

    public static String username ;

    public static String password ;

    public static String rol;

    private static final String NAME = "Name";

    private static final String DOCUMENT = "Document";

    private static final String PHONE = "Phone";

    private static final String USERNAME = "Username";

    private static final String PASSWORD = "Password";

    private static final String REGISTER = "Register";

    private static final String[] ROL = {"Escoga una opcion", "Admin", "Cajero", "Comprador"};

    private static final String SELECCIONAR_ROL = "SelectRol";


    public RegisterDialog(JFrame parent) {
        super(parent, "Register", true);
        setSize(500, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocationRelativeTo(parent);


        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(backgroundColor);

        
        JPanel panelRegisterItems = createRegisterItems();
        panelPrincipal.add(panelRegisterItems, BorderLayout.NORTH);


        JPanel panelRegisterButton = createRegisterButtonPanel();
        panelPrincipal.add(panelRegisterButton, BorderLayout.SOUTH);

        add(panelPrincipal);

    }

    private JPanel createRegisterButtonPanel() {
        JPanel panelRegister = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton regButton = new JButton("Regístrate");
        InterfazUtilities.buttonModel(regButton, backgroundColor, null);
        regButton.addActionListener(this);
        regButton.setActionCommand(REGISTER);

        panelRegister.add(regButton);


        return panelRegister;
    }
    
    private JPanel createRegisterItems() {
        JPanel panelLogin = new JPanel(new GridLayout(7, 1));
        
        nameField = InterfazUtilities.textFieldModel(nameField, backgroundColor, "Nombre");
        nameField.addActionListener(this);
        nameField.setActionCommand(NAME);

        docField = InterfazUtilities.textFieldModel(docField, backgroundColor, "Documento");
        docField.addActionListener(this);
        docField.setActionCommand(DOCUMENT);

        phoneField = InterfazUtilities.textFieldModel(phoneField, backgroundColor, "Numero de telefono");
        phoneField.addActionListener( this);
        phoneField.setActionCommand(PHONE);

        userField = InterfazUtilities.textFieldModel(userField, backgroundColor, "Nombre de usuario");
        userField.addActionListener( this);
        userField.setActionCommand(USERNAME);

        passwordField = InterfazUtilities.textFieldModel(passwordField, backgroundColor, "Contraseña");
        passwordField.addActionListener((ActionListener) this);
        passwordField.setActionCommand(PASSWORD);


        panelLogin.add(nameField);
        panelLogin.add(docField);
        panelLogin.add(phoneField);
        panelLogin.add(userField);
        panelLogin.add(passwordField);
        panelLogin.add(createButtonsRol());

        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        return panelLogin;
    }
    
    private JComboBox<String> createButtonsRol(){
        JPanel panelButtonsRol = new JPanel(new GridLayout(6, 1));

        JComboBox<String> tamanos = new JComboBox<>(ROL);


        tamanos.setPreferredSize(new Dimension(20, 25));
        tamanos.setBorder(new TitledBorder("Rol"));

        
        tamanos.addActionListener(this);

        tamanos.setActionCommand(SELECCIONAR_ROL);

        panelButtonsRol.add(tamanos);
        return tamanos;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals(NAME)){
            System.out.println("Name: " + e.getActionCommand());
        }
        else if(command.equals(DOCUMENT)){
            System.out.println("Document: " + e.getActionCommand());
        }
        else if(command.equals(PHONE)){
            System.out.println("Phone: " + e.getActionCommand());
        }
        else if(command.equals(USERNAME)){
            System.out.println("Username: " + e.getActionCommand());
        }
        else if(command.equals(PASSWORD)){
            System.out.println("Password: " + e.getActionCommand());
        }
        else if(command.equals(REGISTER)){
            System.out.println("Register: " + e.getActionCommand());
            name = nameField.getText();

            document = docField.getText();

            phone = phoneField.getText();

            username = userField.getText();

            password = passwordField.getText();

            BaseUser newUser = new BaseUser(name, document, phone, username, password);

            mainInterface.registerUser(newUser);
            
            this.dispose();
        }
        else if(command.equals(SELECCIONAR_ROL)){
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            String seleccion = (String) comboBox.getSelectedItem();
            System.out.println("Rol: " + seleccion);
            rol = seleccion;


        }
    }
   
}