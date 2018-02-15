package Views;

import Controllers.CatalogController;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class AggiungiView extends JFrame {
    private JPanel addPanel;
    private JTextField titolo;
    private JTextField artista;
    private JTextField pezzi;
    private JTextField prezzo;
    private JTextField genere;
    private JButton aggiungiButton;
    private JButton annullaButton;

    public AggiungiView() {
        this.setContentPane(this.addPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(400, 400);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        aggiungiButton.addActionListener(e -> {

            CatalogController.addProduct(titolo.getText(), artista.getText(),genere.getText(),prezzo.getText(),pezzi.getText());

            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        });
    }


}
