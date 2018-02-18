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
        this.setSize(500, 400);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));

        modificaButton.setBorderPainted(false);
        modificaButton.setFocusPainted(false);
        modificaButton.setContentAreaFilled(false);
        modificaButton.setOpaque(false);

        annullaButton.setBorderPainted(false);
        annullaButton.setFocusPainted(false);
        annullaButton.setContentAreaFilled(false);
        annullaButton.setOpaque(false);


        modificaButton.addActionListener(e -> {
            try {
                CatalogController.modifyProduct(nome.getText(), prezzo.getText(), pezzi.getText());
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
            catch(java.lang.NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "Immettere tutti i campi in maniera corretta");
            }
        });
    }


}
