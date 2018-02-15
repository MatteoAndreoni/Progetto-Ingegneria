package Views;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ModifyView extends JFrame{

    private JPanel modifyPanel;
    private JButton okButton;
    private JRadioButton aggiungiProdottoRadioButton;
    private JRadioButton modificaProdottoRadioButton;
    private JRadioButton eliminaProdottoRadioButton;
    private JButton annullaButton;

    public ModifyView(){
        this.setContentPane(this.modifyPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(400, 400 );
        setupView(this);
    }
    public void setupView(JFrame f){

        ButtonGroup group = new ButtonGroup();
        group.add(aggiungiProdottoRadioButton);
        group.add(modificaProdottoRadioButton);
        group.add(eliminaProdottoRadioButton);

        aggiungiProdottoRadioButton.setSelected(true);

        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));

        okButton.addActionListener(e -> {
            if(aggiungiProdottoRadioButton.isSelected()){
                new AggiungiView();
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
            else if(modificaProdottoRadioButton.isSelected()){
                new ModificaView();
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
            else if(eliminaProdottoRadioButton.isSelected()){
                new EliminaView();
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
