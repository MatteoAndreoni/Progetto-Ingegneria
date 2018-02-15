package Views;

import Controllers.CatalogController;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class EliminaView  extends JFrame{
    private JTextField nome;
    private JButton eliminaButton;
    private JButton annullaButton;
    private JPanel deletePanel;

    public EliminaView(){
        this.setContentPane(this.deletePanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(400, 400);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        eliminaButton.addActionListener(e -> {

            CatalogController.deleteProduct(nome.getText());

            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        });
    }

}
