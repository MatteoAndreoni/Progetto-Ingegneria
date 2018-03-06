package Views;

import Controllers.CartController;
import Models.Cart;
import Models.Product;
import SupportClasses.Observer;
import SupportClasses.Subject;
import SupportClasses.TableFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CartView extends Observer {

    private JPanel _cartPanel;
    private JTable _table1;
    private JScrollPane _scrollPane;
    private JButton compraButton;
    private JButton eliminaButton;
    private DefaultTableModel _model;
    private TableFactory _factory;
    private Cart _cart;
    private CartController _cartController;
    private MainView _main;

    int row = -1;


    public CartView(Cart c, MainView v)
    {
        _cart = c;
        _main = v;
        this.setContentPane(this._cartPanel);
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

    }


    public void setupView()
    {

        _cart.attach(this);

        _factory = new TableFactory();

        _model = _factory.getTableModel("CartView");

        _table1 = new JTable(_model)
        {
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        eliminaButton.setBorderPainted(false);
        eliminaButton.setFocusPainted(false);
        eliminaButton.setContentAreaFilled(false);
        eliminaButton.setOpaque(false);

        compraButton.setBorderPainted(false);
        compraButton.setFocusPainted(false);
        compraButton.setContentAreaFilled(false);
        compraButton.setOpaque(false);

        _table1.setRowHeight(60);
        _cartController = new CartController(_cart);
        _table1.setVisible(true);
        _scrollPane.setViewportView(_table1);
        _cartController.initCart();

        _table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                row = _table1.rowAtPoint(evt.getPoint());

            }
        });

        eliminaButton.addActionListener(e -> {
            if(_table1.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "Carrello vuoto");
            }
            else if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selezionare un oggetto da rimuovere");
            } else {
                _cartController.removeFromCart(row);
                row = -1;
            }
        });




        compraButton.addActionListener(e ->{
            if(_table1.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "Carrello vuoto. Aggiungere dei prodotti per proseguire");
            }
            else {
                new BuyView(_cart, _main);
                this.dispose();
            }
        });

    }

    @Override
    public void update(Subject cart)
    {
        _model.setRowCount(0);
        Object row[];
        _cart = (Cart)cart;
        ImageIcon imageIcon;
        Image image;
        for (Product p : _cart.get_cartList())
        {
            if(!(p.get_id() == 0))
            {
                imageIcon = new ImageIcon(getClass().getResource(p.get_coverImage()));
                image = imageIcon.getImage();
                imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                row = new Object[]{imageIcon, p.get_title(), p.get_price() + " €", p.get_artist().get_name(), p.get_genre(),false};
                _model.addRow(row);
            }
        }
    }
}
