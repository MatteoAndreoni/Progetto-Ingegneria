package Views;

import Controllers.SaleController;
import Models.Cart;
import Models.Sale;
import SupportClasses.Observer;
import SupportClasses.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

public class BuyView extends Observer {
    private JComboBox pagamentoCombo;
    private JComboBox spedizioneCombo;
    private JLabel prezzoTotale;
    private JButton compraButton;
    private JButton annullaButton;
    private JPanel _buyPanel;
    private JLabel _totalPrice;
    private Cart _cart;
    private MainView _main;
    SaleController sc;
    Sale s;

    public BuyView(Cart c, MainView v){

        s= new Sale();
        _cart = c;
        _main = v;
        _cart.attach(this);
        this.setContentPane(this._buyPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // _cart.resetCart();
            }
        });

        this.pack();
        this.setVisible(true);
        this.setSize(400, 400 );
        setupView();

        compraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBuyClick();
            }
        });

        annullaButton.addActionListener(e -> {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            new CartView(_cart);
        });

        spedizioneCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(spedizioneCombo.getSelectedItem().equals("normale")){

                    float p =_cart.get_totalPrice();
                    prezzoTotale.setText(Float.toString(p+s.postalDeliveryCost));

                }
                else
                {
                    if(!_cart.get_user().get_isPremium()) {
                        float p = _cart.get_totalPrice();
                        prezzoTotale.setText(Float.toString(p + s.expressDeliveryCost));
                    }
                    else{
                        float p = _cart.get_totalPrice();
                        prezzoTotale.setText(Float.toString(p));
                    }

                }
            }
        });

    }

    public void setupView(){

        pagamentoCombo.addItem("carta di credito");
        pagamentoCombo.addItem("paypal");
        pagamentoCombo.addItem("bonifico");

        spedizioneCombo.addItem("normale");
        spedizioneCombo.addItem("espressa");

        if(_cart.get_user().get_isPremium())
            _totalPrice.setText("Totale scontato del 10%:");
        else
            _totalPrice.setText("Totale:");

        float p =_cart.get_totalPrice();
        prezzoTotale.setText(Float.toString(p));

    }

    public void onBuyClick(){


        s.set_cart(_cart);
        s.set_deliveryType((String)spedizioneCombo.getSelectedItem());
        s.set_paymentType((String)pagamentoCombo.getSelectedItem());
        s.set_saleDate(LocalDateTime.now());
        if(s.get_deliveryType().equals("normale"))
            s.set_salePrice(_cart.get_totalPrice()+s.postalDeliveryCost);
        else
            if(!_cart.get_user().get_isPremium())
                s.set_salePrice(_cart.get_totalPrice()+s.expressDeliveryCost);
            else
                s.set_salePrice(_cart.get_totalPrice());

        sc= new SaleController(s);
        sc.buyCart();
        JOptionPane.showMessageDialog(null, "Acquisto avvenuto con successo.");

        _main.onSearchButtonClicked();
        this.dispose();

    }

    @Override
    public void update(Subject cart) {

        float p =_cart.get_totalPrice();
        System.out.println("IsPremium: " + _cart.get_user().get_isPremium());

        if(!_cart.get_user().get_isPremium()){
            if (pagamentoCombo.getSelectedItem().equals("normale"))
                prezzoTotale.setText(Float.toString(p + s.postalDeliveryCost));
            else
                prezzoTotale.setText(Float.toString(p + s.expressDeliveryCost));
        }
        else{
            if (pagamentoCombo.getSelectedItem().equals("normale"))
                prezzoTotale.setText(Float.toString(p + s.postalDeliveryCost));
            else
                prezzoTotale.setText(Float.toString(p));
        }


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
