package Views;

import Models.User;
import SupportClasses.RegistrationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class RegistrationView extends JFrame{

    private JPanel registrationPanel;
    private JTextField username;
    private JPasswordField password;
    private JButton accettaButton;
    private JButton annullaButton;
    private JTextField cf;
    private JTextField nome;
    private JTextField cognome;
    private JTextField indirizzo;
    private JTextField telefono;
    private JTextField cellulare;


    public RegistrationView()
    {
        this.setContentPane(this.registrationPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 500 );
        setupView(this);

    }

    public void setupView(JFrame f){

        annullaButton.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING)));


        accettaButton.addActionListener(e ->
        {

            User u;
            u = new User(cf.getText(),username.getText(),password.getText(),nome.getText(),cognome.getText(),indirizzo.getText(),telefono.getText(),cellulare.getText(),false);
            int res=RegistrationManager.registerUser(u);

            if(res == -1)
                JOptionPane.showMessageDialog(null,"Nome utente non disponibile");
            else
                if(res == 0)
                    JOptionPane.showMessageDialog(null,"errore durante la registrazione,riprova più tardi");
            else
                    JOptionPane.showMessageDialog(null,"registrazione avvenuta con successo");

        });

    }
}
