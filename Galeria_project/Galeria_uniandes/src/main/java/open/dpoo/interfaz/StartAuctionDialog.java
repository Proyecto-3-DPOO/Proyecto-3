package main.java.open.dpoo.interfaz;

import javax.swing.*;

import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.Inventory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

public class StartAuctionDialog extends JFrame implements ActionListener {
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final Logger logger = Logger.getLogger(StartAuctionDialog.class.getName());

    public StartAuctionDialog(JFrame parent) {
        super("Start Auction");
        setSize(getPreferredSize());
        JLabel startDateLabel = new JLabel("Start Date (yyyy-MM-dd): ");
        startDateField = new JTextField(20);
        InterfazUtilities.textFieldModel(startDateField, getBackground(), getName());
        JLabel endDateLabel = new JLabel("End Date (yyyy-MM-dd): ");
        endDateField = new JTextField(20);
        InterfazUtilities.textFieldModel(endDateField, getBackground(), getName());
        JButton startButton = new JButton("Start Auction");
        startButton.addActionListener(this);
        InterfazUtilities.buttonModel(startButton, getBackground(), getForeground());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(startDateLabel);
        panel.add(startDateField);
        panel.add(endDateLabel);
        panel.add(endDateField);
        panel.add(startButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Auction")) {
            String startDateStr = startDateField.getText();
            String endDateStr = endDateField.getText();

            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

                Inventory.getInstance().addAuction(startDate, endDate);

                Optional<Auction> any = Inventory.getInstance().getAuctions().stream()
                    .filter(auction -> auction.getStartAuctionDate().equals(startDate) &&
                            auction.getEndAuctionDate().equals(endDate)).findAny();
                    if (any.isPresent()) {
                        Auction auction = any.get();
                        JOptionPane.showMessageDialog(this, "Subasta creada!", "Subasta Creada", JOptionPane.ERROR_MESSAGE);
                        auction.getPiecesAuction().stream().map(piece -> "> " + piece.getId() + " - " + piece.getTitle())
                                .forEach(System.out::println);
                        
                    }
                    
                        dispose(); // Cerrar la ventana después de iniciar la subasta
                }catch (ParseException ex) {
                        logger.warning("Invalid date format: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para mostrar la ventana
    public void showDialog() {
        setVisible(true);
    }
}


