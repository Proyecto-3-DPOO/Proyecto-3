package main.java.open.dpoo.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;

public class cashierView extends JFrame implements ActionListener{

    public static String[] options ={ "Ver ventas", "Ver Subastas", "Vender Obra" };

    public static HashMap<String, JFrame> actions = new HashMap<>();
    
    public final static String FIRST = "First";
    public final static String PREVIOUS = "Previous";
    public final static String NEXT = "Next";
    public final static String LAST = "Last";

    public static int auctionActual = 0;

    public cashierView() {
        super("Cashier View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new BorderLayout());
        
        setTitle("ver Ventas");
        setSize(600, 600);
        add(showAuctionInfo(), BorderLayout.CENTER);

        JPanel navigation = createNavigationPanel();
        add(navigation, BorderLayout.NORTH);

        JPanel panelInfo = new JPanel();
        add(panelInfo, BorderLayout.CENTER);

        

    }

      public JPanel showAuctionInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1)); // GridLayout con 2 columnas y filas automáticas

        Auction auction = Inventory.getInstance().getAuctions().get(auctionActual);
        addTextField("Start Auction Date:", auction.getStartAuctionDate().toString(), panel);
        addTextField("End Auction Date:", auction.getEndAuctionDate().toString(), panel);
        
        
        return panel;
    }

        private void addTextField(String label, String value, JPanel panel) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText(value);
        InterfazUtilities.textFieldModel(textField, getBackground(), label);
        panel.add(textField);
    }

    public JPanel createNavigationPanel() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        List<Auction>  auctions = Inventory.getInstance().getAuctions();
    
        JPanel panelInfo = new JPanel();
        if (auctions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pieces available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (command.equals(FIRST)) {
            panelInfo.removeAll();
            auctionActual = 0;
            panelInfo = showAuctionInfo();

        } else if (command.equals(NEXT)) {
            if (auctionActual < auctions.size() - 1) {
                panelInfo.removeAll();
                auctionActual++;
                panelInfo = showAuctionInfo();
            } else {
                panelInfo.removeAll();
                auctionActual = 0;
                panelInfo = showAuctionInfo();
            }
        } else if (command.equals(PREVIOUS)) {
            if (auctionActual > 0) {
                panelInfo.removeAll();
                auctionActual--;
                panelInfo = showAuctionInfo();
            } else {
                panelInfo.removeAll();
                auctionActual = auctions.size() - 1;
                panelInfo = showAuctionInfo();
            }
        } else if (command.equals(LAST)) {
            auctionActual = auctions.size() - 1;
            panelInfo = showAuctionInfo();
        }
    
        add(panelInfo, BorderLayout.CENTER);
        revalidate();
        repaint();
        }
        

  

}
