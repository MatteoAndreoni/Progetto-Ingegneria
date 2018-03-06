package Views;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ModifyView extends JFrame{

    private JPanel modifyPanel;
    private JButton addProductButton;
    private JButton updateProductButton;
    private JButton deleteProductButton;
    private JButton quitButton;

    public ModifyView(){
        this.setContentPane(this.modifyPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(550, 400 );
        setupView(this);
    }
    public void setupView(JFrame f){
        //modifiche grafiche per i bottoni
        quitButton.setBorderPainted(false);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setOpaque(false);

        addProductButton.setBorderPainted(false);
        addProductButton.setFocusPainted(false);
        addProductButton.setContentAreaFilled(false);
        addProductButton.setOpaque(false);

        updateProductButton.setBorderPainted(false);
        updateProductButton.setFocusPainted(false);
        updateProductButton.setContentAreaFilled(false);
        updateProductButton.setOpaque(false);

        deleteProductButton.setBorderPainted(false);
        deleteProductButton.setFocusPainted(false);
        deleteProductButton.setContentAreaFilled(false);
        deleteProductButton.setOpaque(false);

        //gestione eventi bottoni
        quitButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));

        addProductButton.addActionListener(e -> {
            new AddProductView();
        });

        updateProductButton.addActionListener(e -> {
            new UpdateProductView();
        });

        deleteProductButton.addActionListener(e -> {
            new DeleteProductView();
        });
    }
}