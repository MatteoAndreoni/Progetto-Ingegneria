package Views;

import Controllers.CatalogController;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class DeleteProductView extends JFrame{
    private JTextField id;
    private JButton eliminaButton;
    private JButton annullaButton;
    private JPanel deletePanel;

    public DeleteProductView(){
        this.setContentPane(this.deletePanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(550, 200);
        setupView(this);
    }

    public void setupView(JFrame f) {
        annullaButton.setBorderPainted(false);
        annullaButton.setFocusPainted(false);
        annullaButton.setContentAreaFilled(false);
        annullaButton.setOpaque(false);

        eliminaButton.setBorderPainted(false);
        eliminaButton.setFocusPainted(false);
        eliminaButton.setContentAreaFilled(false);
        eliminaButton.setOpaque(false);

        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        eliminaButton.addActionListener(e -> {
            if(!id.getText().equals("")) {
                try {
                    CatalogController.deleteProduct(Integer.parseInt(id.getText()));
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                }
                catch(NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Inserire un id valido");
                }

            }
            else
                JOptionPane.showMessageDialog(null, "Immettere un id");
        });
    }

}
