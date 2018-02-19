package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Random;

public class SuggestView extends JFrame{
    private JPanel suggestPanel;
    private JButton closeButton;
    private JLabel img;

    public SuggestView(){
        this.setContentPane(this.suggestPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(730, 450);
        setupView(this);
    }

    public void setupView(JFrame f){
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);

        closeButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));

        Random rand = new Random();
        int random = rand.nextInt(4)+1;
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("resources/suggerimento"+random+".png"));
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(700, 350, Image.SCALE_SMOOTH));
        final ImageIcon immagine = imageIcon;

        img.setIcon(immagine);
    }
}
