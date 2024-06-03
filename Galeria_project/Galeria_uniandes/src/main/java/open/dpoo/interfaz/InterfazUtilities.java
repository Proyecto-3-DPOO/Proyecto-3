package main.java.open.dpoo.interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

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
import main.java.open.dpoo.model.usuarios.Owner;
import main.java.open.dpoo.model.usuarios.Purchaser;


public class InterfazUtilities {

    public Inventory inventory = new Inventory();

    public static void inicializePieces(){
        Author author1 = new Author("Pablo Picasso", "123456789", "123-456-7890", "picasso", "password123");
        Author author2 = new Author("Vincent van Gogh", "987654321", "987-654-3210", "vangogh", "password456");
        Author author3 = new Author("Leonardo da Vinci", "456789123", "456-789-1230", "davinci", "password789");
        Author author4 = new Author("Claude Monet", "321654987", "321-654-9870", "monet", "password321");

        // Crear availability
        Owner owner1 = new Owner("Museum of Modern Art", "1622173872", "77787998", "museum@example.com", "123-456-7890");
        Owner owner2 = new Owner("Van Gogh Museum","643762443", "7770000", "vangogh@example.com", "987-654-3210");
        
        Availability availability1 = new Availability(owner1, false, false, false);
        Availability availability2 = new Availability(owner2, true, false, false);

        // Crear valoration
        Valoration valoration1 = new Valoration(1000000, 500000, 750000);
        Valoration valoration2 = new Valoration(2000000, 1000000, 1500000);

        // Crear temporalLocation
        @SuppressWarnings("deprecation")
        TemporalLocation storage1 = new TemporalLocation(new Date(2024, 1, 1), new Date(2024, 12, 31));
        @SuppressWarnings("deprecation")
        TemporalLocation storage2 = new TemporalLocation(new Date(2024, 6, 1), new Date(2024, 12, 31));

        // Crear piezas de arte
        Piece piece1 = new Painting(1, "Guernica", 1937, Locale.ITALY, Arrays.asList(author1), "A famous painting by Pablo Picasso",
                availability1, valoration1, storage1, "Cubism", 45, 60);
        Inventory.getInstance().addPieceBOD(piece1);

        Piece piece2 = new Painting(2, "Starry Night", 1889, Locale.FRANCE, Arrays.asList(author2), "A famous painting by Vincent van Gogh",
                availability2, valoration2, storage2, "Post-Impressionism", 45, 60);
        Inventory.getInstance().addPieceBOD(piece2);

        Piece piece3 = new Sculpture(3, "David", 1504, Locale.ITALY, Arrays.asList(author3), "A famous sculpture by Leonardo da Vinci",
                availability1, valoration1, storage1, 170,0, 560.0, 250.0, false);
        Inventory.getInstance().addPieceBOD(piece3);

        Piece piece4 = new Painting(4, "Water Lilies", 1920, Locale.FRANCE, Arrays.asList(author4), "A famous painting by Claude Monet",
                availability2, valoration2, storage2, "Impressionism", 45, 60);
                
        Inventory.getInstance().addPieceBOD(piece4);

        Date startAuctionDate1 = new Date(); // Usando la fecha actual como ejemplo
        Date endAuctionDate1 = new Date(System.currentTimeMillis() + 86400000); // Fecha actual + 1 día en milisegundos
        
        Inventory.getInstance().addAuction(startAuctionDate1, endAuctionDate1);

        Date startAuctionDate2 = new Date(); // Usando la fecha actual como ejemplo
        Date endAuctionDate2 = new Date(System.currentTimeMillis() + 172800000); // Fecha actual + 2 días en milisegundos
        
        Inventory.getInstance().addAuction(startAuctionDate2, endAuctionDate2);
        
        Date startAuctionDate3 = new Date(); // Usando la fecha actual como ejemplo
        Date endAuctionDate3 = new Date(System.currentTimeMillis() + 259200000); // Fecha actual + 3 días en milisegundos
        
        Inventory.getInstance().addAuction(startAuctionDate3, endAuctionDate3);
        
    }
    
    public static Piece collectInput(String type, Long id, String title, int year, Locale origin, List<Author> authorsList, String description,
                            Availability availability, Valoration valoration, TemporalLocation storage) {
        try {
            Piece piece;
            if (type.toUpperCase() =="video".toUpperCase()) {
                piece = new Video(id, title, year, origin, authorsList, description, availability, valoration, storage, description, year, year);
            } else if (type.toUpperCase() =="painting".toUpperCase()) {
                piece = new Painting(id, title, year, origin, authorsList, description, availability, valoration, storage, description, year, year);
            } else if (type.toUpperCase() =="sculpture".toUpperCase()) {
                piece = new Sculpture( id,  title,  year,  origin, authorsList,  description, availability,  valoration,  storage, 545454,
                45454, 77,777, false);
            } else if (type.toUpperCase() =="photography".toUpperCase()) {
                piece = new Photography(id, title, year, origin, authorsList, description, availability, valoration, storage, year, year, description); 
            } else{
                piece = new Impression(id, title, year, origin, authorsList, description, availability, valoration, storage, "",
                "", "String size", "String quality", "String technique");
            }
            return piece;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating piece: " + e.getMessage());
        }
    }

    private static void addSectionTitle(JPanel panel, GridBagConstraints gbc, String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }

    private static void addAuctionInfo(JPanel panel, GridBagConstraints gbc, Auction auction) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        addLabelAndData(panel, gbc, "Start Date:", sdf.format(auction.getStartAuctionDate()));
        addLabelAndData(panel, gbc, "End Date:", sdf.format(auction.getEndAuctionDate()));
        addLabelAndData(panel, gbc, "Pieces in Auction:", piecesToString(auction.getPiecesAuction()));
        addLabelAndData(panel, gbc, "Purchasers:", purchasersToString(auction.getPurchasers()));
    }

    private static String authorsToString(List<Author> authors) {
        StringBuilder authorsStr = new StringBuilder();
        for (Author author : authors) {
            authorsStr.append(author.getName()).append(" (").append(author.getDocument()).append("), ");
        }
        if (authorsStr.length() > 0) {
            authorsStr.setLength(authorsStr.length() - 2);
        }
        return authorsStr.toString();
    }

    private static String piecesToString(List<Piece> pieces) {
        StringBuilder piecesStr = new StringBuilder();
        for (Piece piece : pieces) {
            piecesStr.append(piece.getTitle()).append(", ");
        }
        if (piecesStr.length() > 0) {
            piecesStr.setLength(piecesStr.length() - 2);
        }
        return piecesStr.toString();
    }

    private static String purchasersToString(List<Purchaser> purchasers) {
        StringBuilder purchasersStr = new StringBuilder();
        for (Purchaser purchaser : purchasers) {
            purchasersStr.append(purchaser.getName()).append(", ");
        }
        if (purchasersStr.length() > 0) {
            purchasersStr.setLength(purchasersStr.length() - 2);
        }
        return purchasersStr.toString();
    }

    private static void addLabelAndData(JPanel panel, GridBagConstraints gbc, String label, String data) {
        JLabel labelComponent = new JLabel(label);
        JLabel dataComponent = new JLabel(data);
        labelComponent.setForeground(Color.DARK_GRAY);
        dataComponent.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(labelComponent, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(dataComponent, gbc);
        gbc.gridy++;
    }
    
    public static void buttonModel(JButton button, Color backgroundColor, Color textColor){
        if (textColor == null) {
            textColor = Color.WHITE;
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(textColor); // Color azul
        button.setForeground(backgroundColor); // Color del texto blanco
        button.setUI(new GradientButtonUI());
    }

    public static JTextField textFieldModel(JTextField textField, Color backgroundColor, String text){
        
        if(text != null){
            textField.setBorder(new TitledBorder(text));

        }
        textField.setBackground(backgroundColor);
        textField.setEditable(true);
        textField.setPreferredSize(new Dimension(400, 50));  // Ajusta el ancho y la altura
        return textField;
    }

    static class GradientButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            int width = c.getWidth();
            int height = c.getHeight();
            Color color1 = new Color(0, 150, 255);
            Color color2 = new Color(0, 100, 200);
            g2d.setPaint(new GradientPaint(0, 0, color1, 0, height, color2));
            g2d.fillRoundRect(0, 0, width, height, 20, 20); // Bordes redondeados
            g2d.dispose();
            super.paint(g, c);
        }
    }

    

}
