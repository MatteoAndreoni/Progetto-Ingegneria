package Views;

import Controllers.CartController;
import Controllers.CatalogController;
import Models.*;
import SupportClasses.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;


public class MainView extends Observer{
    private JPanel mainPanel;
    private JButton cartButton;
    private JFormattedTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JTable catalogTable;
    private JToolBar mainToolbar;
    private JLabel titleLabel;
    private JScrollPane scrollPane;
    private JComboBox comboBox;
    private JTextField searchText;
    private JButton searchButton;
    private JButton registerButton;
    private JLabel premiumAcc;
    private Catalog _catalog;
    private CatalogController _catalogController;
    private DefaultTableModel _model;
    private LoginManager _loginManager;
    private Cart _cart;
    private CartController _cartController;
    private User _user;
    private TableFactory _factory;

    public MainView()
    {
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(900, 600 );
        SetupDBConn();
        SetupView();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void SetupView()
    {
        _factory = new TableFactory();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("resources/cart.png"));
        Image image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        final ImageIcon cartIcon = imageIcon;

        imageIcon = new ImageIcon(getClass().getResource("resources/gear.png"));
        image = imageIcon.getImage();
        imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        final ImageIcon gearIcon = imageIcon;

        cartButton.setIcon(cartIcon);
        cartButton.setSize(30, 100);
        cartButton.setText("");

        comboBox.addItem("");
        comboBox.addItem("Artista");
        comboBox.addItem("Genere");
        comboBox.addItem("Prezzo");
        comboBox.addItem("Nome CD/DVD");

        premiumAcc.setEnabled(false);
        premiumAcc.setVisible(false);

        searchButton.setFocusPainted(false);

        usernameText.setSize(40,300);
        passwordText.setSize(40,300);

        usernameText.setText("username");
        passwordText.setText("password");

        LineBorder lineBorder = new LineBorder(Color.white, 2, true);
        lineBorder.getRoundedCorners();

        usernameText.setBorder(lineBorder);

        passwordText.setBorder(lineBorder);

        cartButton.setFocusPainted(false);

        registerButton.setFocusPainted(false);
        registerButton.setBorder(lineBorder);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(lineBorder);
        _model = _factory.getTableModel("MainView");
        catalogTable = new JTable(_model)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        catalogTable.setRowHeight(60);
        _catalog = new Catalog();
        _catalog.attach(this);
        _catalogController = new CatalogController(_catalog);
        catalogTable.setVisible(true);
        scrollPane.setViewportView(catalogTable);

        catalogTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = catalogTable.rowAtPoint(evt.getPoint());
                new ProductView(_catalog.getCatalogProducts().get(row), _cartController);
            }
        });

        usernameText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameText.setText("");
            }
        });

        passwordText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordText.setText("");
            }
        });


        cartButton.addActionListener(e -> onCartButtonClicked());

        //TODO gestire evento per login

        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                makeLogin();
                if(_user!=null) {
                    loginButton.setEnabled(false);
                    loginButton.setVisible(false);
                    registerButton.setText("Logout");
                    usernameText.setEnabled(false);
                    usernameText.setVisible(false);
                    passwordText.setEnabled(false);
                    passwordText.setVisible(false);

                }
                else
                {
                    loginButton.setEnabled(true);
                    loginButton.setVisible(true);
                }

            }

            //loginButton.setEnabled(true);
        }
        );


        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                if(_user!=null) {
                    logout();
                    cartButton.setIcon(cartIcon);

                }
                else {
                    new RegistrationView();
                    }

            }
        });

        searchButton.addActionListener(e -> onSearchButtonClicked());
    }

    public static void SetupDBConn()
    {
        DBConnSingleton.getInstance();
    }

    public void onCartButtonClicked()
    {
        if(_user!=null)
        {
            if(_user.get_isEmployee())
            {
                //TODO vista della schermata gestione employee
                new ModifyView();
            }
            else
            {
                new CartView(_cart, this);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "E' necessario essere loggati per accedere al carrello");
        }
    }

    public void logout(){

        _user=null;
        loginButton.setEnabled(true);
        loginButton.setVisible(true);
        registerButton.setText("REGISTRATI");
        usernameText.setEnabled(true);
        usernameText.setVisible(true);
        passwordText.setEnabled(true);
        passwordText.setVisible(true);
        if(premiumAcc.isEnabled() == true){
            premiumAcc.setEnabled(false);
            premiumAcc.setVisible(false);
        }


    }

    public void makeLogin()
    {
        if(LoginManager.checkUser(usernameText.getText(), passwordText.getText()))
        {
            _user = LoginManager.getUser();

            if(!_user.get_isEmployee()) {
                _cart = new Cart(_user);
                _cartController = new CartController(_cart);
                if(!_cartController.isCartPresent(_user.get_username()))
                    CartController.newSale(_user.get_username());
                if(_user.get_isPremium()){
                    premiumAcc.setEnabled(true);
                    premiumAcc.setVisible(true);
                }
                JOptionPane.showMessageDialog(null, "Loggato come Cliente");
            }
            else
            {
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("resources/gear.png"));
                Image image = imageIcon.getImage();
                imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                cartButton.setIcon(imageIcon);
                JOptionPane.showMessageDialog(null, "Loggato come Impiegato");
                _catalogController.emptyAlert();
            }

        }
        else
        {
            JOptionPane.showMessageDialog(null, "username o password non validi");
        }
    }

    public void onSearchButtonClicked()
    {
        try {
            switch ((String) comboBox.getSelectedItem()) {
                case "":
                    _catalogController.setProductList();
                    break;
                case "Artista":
                    _catalogController.getProductByArtist(searchText.getText());
                    break;
                case "Genere":
                    _catalogController.getProductByGenre(searchText.getText());
                    break;
                case "Prezzo":
                    _catalogController.getProductByPrice(searchText.getText());
                    break;
                case "Nome CD/DVD":
                    _catalogController.getProductByTitle(searchText.getText());
                    break;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Subject obj)
    {
        _model.setRowCount(0);
        Object row[];
        _catalog = (Catalog)obj;
        ImageIcon imageIcon;
        Image image;
        for (Product p : _catalog.getCatalogProducts())
        {
            imageIcon = new ImageIcon(getClass().getResource(p.get_coverImage()));
            image = imageIcon.getImage();
            imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            row = new Object[]{imageIcon,p.get_title(), p.get_price() + " €", p.get_artist().get_name(), p.get_genre()};
            _model.addRow(row);
        }
    }

}
