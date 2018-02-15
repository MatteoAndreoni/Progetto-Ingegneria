package Views;

import Controllers.CatalogController;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ModificaView extends JFrame{
    private JTextField nome;
    private JTextField pezzi;
    private JTextField prezzo;
    private JButton modificaButton;
    private JButton annullaButton;
    private JPanel modifyPanel;

    public ModificaView() {
        this.setContentPane(this.modifyPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(600, 400);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        modificaButton.addActionListener(e -> {

            CatalogController.modifyProduct(nome.getText(),prezzo.getText(),pezzi.getText());

            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        });
    }


}
