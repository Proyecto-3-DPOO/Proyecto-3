package main.java.open.dpoo.interfaz;

import javax.sound.midi.SysexMessage;
import javax.swing.*;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Availability;
import main.java.open.dpoo.model.galeria.piece.Impression;
import main.java.open.dpoo.model.galeria.piece.Painting;
import main.java.open.dpoo.model.galeria.piece.Photography;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.galeria.piece.Sculpture;
import main.java.open.dpoo.model.galeria.piece.TemporalLocation;
import main.java.open.dpoo.model.galeria.piece.Valoration;
import main.java.open.dpoo.model.galeria.piece.Video;
import main.java.open.dpoo.model.usuarios.Author;
import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

 

public class MainInterface extends JFrame implements ActionListener{

    public  BaseUser user;

    public  AccountManager accountManager = new AccountManager();

    public final static String FIRST = "First";
    public final static String PREVIOUS = "Previous";
    public final static String NEXT = "Next";
    public final static String LAST = "Last";

    JPanel segundoPanel = new JPanel();

    JPanel panelOpciones ;

    public  Integer posCurrentPieceShow = 0;

    public MainInterface(){
        setTitle("Uniandes Gallery");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        

        if(RegisterDialog.rol == "Admin"){
            adminViewUtilities admin = new adminViewUtilities(this);

            gbc.weightx = 0.5;
            gbc.weighty = 0.1; // Smaller row
            gbc.gridx = 1;
            JPanel navegacion = createNavigationPanel(admin.piece);
            add(navegacion, gbc);


            segundoPanel.setLayout(new BorderLayout());
            
            if(Inventory.getInstance().getExhibition().size() > 0){
                segundoPanel.add(showPieceInfo(Inventory.getInstance().getExhibition().get(0)), BorderLayout.CENTER);
                
            }
            JPanel options = createOptions(admin.options, admin.actions);
            this.panelOpciones = options;
            segundoPanel.add(options, BorderLayout.EAST);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2; // Span across two columns
            gbc.weightx = 1.5;
            gbc.weighty = 0.7; // Larger row
            add(segundoPanel, gbc);
            
            setVisible(true);

            
        }else if(RegisterDialog.rol == "Cajero"){

            
            cashierView cashierView = new cashierView();

            JPanel panel = createOptions(cashierView.options, cashierView.actions);
            cashierView.add(panel, BorderLayout.CENTER);

            setVisible(true);

        }

    }

    public JPanel createNavigationPanel(Piece piece) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1, 0, 10)); // Vertical space between buttons

        JButton btnFirst;
        JButton btnPrevious;
        JButton btnNext;
        JButton btnLast;

        btnFirst = new JButton("<<");
        btnFirst.setActionCommand(FIRST);
        btnFirst.addActionListener(this);
        panel.add(btnFirst);

        btnPrevious = new JButton("<");
        btnPrevious.setActionCommand(PREVIOUS);
        btnPrevious.addActionListener(this);
        panel.add(btnPrevious);

        btnNext = new JButton(">");
        btnNext.setActionCommand(NEXT);
        btnNext.addActionListener(this);
        panel.add(btnNext);

        btnLast = new JButton(">>");
        btnLast.setActionCommand(LAST);
        btnLast.addActionListener(this);
        panel.add(btnLast);

        panel.setPreferredSize(new Dimension(10, 40));

        return panel;
    }


    public JPanel showPieceInfo(Piece piece){
        JPanel panel = new JPanel();
        setLayout(new GridLayout(0, 2)); // GridLayout con 2 columnas y filas automáticas

        
        // Añadir las etiquetas y valores de los atributos de la pieza
        addTextField("ID:", String.valueOf(piece.getId()), panel);
        addTextField("Title:", piece.getTitle(), panel);
        addTextField("Year:", String.valueOf(piece.getYear()), panel);
        addTextField("Origin:", piece.getOrigin().getDisplayCountry(), panel);
        addTextField("Authors:", piece.getAuthors().toString(), panel);
        addTextField("Description:", piece.getDescription(), panel);
        addTextField("Availability:", piece.getAvailability().toString(), panel);
        addTextField("Valoration:", piece.getValoration().toString(), panel);
        addTextField("Storage:", piece.getStorage().toString(), panel);

        return panel;

    }

  

    private void addTextField(String label, String value, JPanel panel) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setEnabled(false);
        textField.setText(value);
        InterfazUtilities.textFieldModel(textField, getBackground(), label);
        panel.add(textField);
    }

    public JPanel createOptions(String[] options, HashMap<String, JFrame> actions) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout( options.length, 1, 0, 10)); // Vertical space between buttons

        for (String option : options) {
            JButton button = new JButton(option);
            InterfazUtilities.buttonModel(button, getBackground(), null);
            button.setText(option);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame actionMethod = actions.get(option);
                    if (actionMethod != null) {
                        actionMethod.setVisible(true);
                        System.out.println("Action found for " + option);
                    } else {
                        System.out.println("No action found for " + option);
                    }
                }
            });
            panel.add(button);
        }
        panel.setPreferredSize(new Dimension(200, options.length * 10));

        return panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent pEvent) {
        String command = pEvent.getActionCommand();
        Inventory inventory = Inventory.getInstance();
        List<Piece> pieces = inventory.getPieces();
    
        JPanel panelInfo = new JPanel();
        if (pieces.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pieces available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        if (command.equals(FIRST)) {
            segundoPanel.removeAll();
            panelInfo = showPieceInfo(pieces.get(0));
            posCurrentPieceShow = 0;
        } else if (command.equals(NEXT)) {
            if (posCurrentPieceShow < pieces.size() - 1) {
                segundoPanel.removeAll();
                posCurrentPieceShow++;
                panelInfo = showPieceInfo(pieces.get(posCurrentPieceShow));
            } else {
                segundoPanel.removeAll();
                posCurrentPieceShow = 0;
                panelInfo = showPieceInfo(pieces.get(0));
            }
        } else if (command.equals(PREVIOUS)) {
            if (posCurrentPieceShow > 0) {
                segundoPanel.removeAll();
                posCurrentPieceShow--;
                panelInfo = showPieceInfo(pieces.get(posCurrentPieceShow));
            } else {
                segundoPanel.removeAll();
                posCurrentPieceShow = pieces.size() - 1;
                panelInfo = showPieceInfo(pieces.get(posCurrentPieceShow));
            }
        } else if (command.equals(LAST)) {
            posCurrentPieceShow = pieces.size() - 1;
            panelInfo = showPieceInfo(pieces.get(posCurrentPieceShow));
        }
    
        segundoPanel.add(panelInfo, BorderLayout.CENTER);
        segundoPanel.add(panelOpciones, BorderLayout.EAST);
        segundoPanel.revalidate();
        segundoPanel.repaint();
    }
    
    public BaseUser giveUser(String name, String password){
        BaseUser userq = AccountManager.login(name, password);
            user = userq;
        return userq;

    }

    public void registerUser(BaseUser user){
        AccountManager.register(user);
    }
   
    
    public static void main(String[] args) {
        InterfazUtilities.inicializePieces();
        SwingUtilities.invokeLater(() -> {
            LoginForm loginDialog = new LoginForm();
            loginDialog.setVisible(true);
        });
    }
}