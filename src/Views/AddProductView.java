package Views;

import Controllers.CatalogController;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class AddProductView extends JFrame {
    private JPanel addPanel;
    private JTextField titolo;
    private JTextField artista;
    private JTextField pezzi;
    private JTextField prezzo;
    private JTextField genere;
    private JButton aggiungiButton;
    private JButton annullaButton;
    private JTextField perCopertina;
    private JTextField descrizione;

    public AddProductView() {
        this.setContentPane(this.addPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(400, 400);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.setBorderPainted(false);
        annullaButton.setFocusPainted(false);
        annullaButton.setContentAreaFilled(false);
        annullaButton.setOpaque(false);

        aggiungiButton.setBorderPainted(false);
        aggiungiButton.setFocusPainted(false);
        aggiungiButton.setContentAreaFilled(false);
        aggiungiButton.setOpaque(false);

        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        aggiungiButton.addActionListener(e -> {
            if(titolo.getText().equals("") || artista.getText().equals("") || genere.getText().equals("") || prezzo.getText().equals("") || pezzi.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Immettere tutti i campi in maniera corretta");
            }
            else{
                CatalogController.addProduct(titolo.getText(), artista.getText(), genere.getText(), prezzo.getText(), pezzi.getText(), perCopertina.getText(), descrizione.getText());
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        });
    }


}
