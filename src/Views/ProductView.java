package Views;

import Controllers.CartController;
import Controllers.CatalogController;
import Models.Catalog;
import Models.Product;
import Models.User;
import SupportClasses.TableFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ProductView extends JFrame{
    private JPanel productPanel;
    private JLabel imageLabel;
    private JLabel title;
    private JButton addToCartButton;
    private JButton annullaButton;
    private JLabel price;
    private JLabel description;
    private JLabel artist;
    private JLabel genre;
    private JComboBox quantity;
    private Product _product;
    private CartController _controller;

    public ProductView(Product product, CartController controller)
    {
        _product = product;
        _controller = controller;
        this.setContentPane(this.productPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(830, 500 );
        SetupView(this);
    }

    public void SetupView(JFrame frame)
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(_product.get_coverImage()));
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        imageLabel.setIcon(imageIcon);
        title.setText(_product.get_title());
        price.setText(_product.get_price() + " €");
        description.setText(_product.get_description());
        artist.setText(_product.get_artist().get_name());
        genre.setText(_product.get_genre());

        for(int i=1 ; i<=_product.get_productStocks() ; i++){
            quantity.addItem(i);
        }

        if(_product.get_productStocks() == 0){
            addToCartButton.setEnabled(false);
        }

        annullaButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        addToCartButton.addActionListener( e -> addProductToCart());
    }

    public void addProductToCart()
    {
        if(_controller != null) {
            int quantita = (int)quantity.getSelectedItem();
            _controller.addToCart(_product, quantita);
            JOptionPane.showMessageDialog(null, "Prodotto aggiunto correttamente al carrello.");
        }
        else
            JOptionPane.showMessageDialog(null, "E' necessario essere loggati come clienti per procedere");
    }
}
