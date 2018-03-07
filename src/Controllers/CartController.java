//classe che controlla gli oggetti Cart
package Controllers;

import Models.Cart;
import Models.Product;
import SupportClasses.DBConnSingleton;

import java.sql.*;

import static Controllers.CatalogController.getProductFromQuery;

public class CartController {

    private Cart _cart;

    public CartController(Cart c)
    {
        _cart = c;
    }

    public void requestNotify()
    {
        _cart.notifyAllObservers();
    }

    //metodo per aggiungere un carrello vuoto al database (sotto forma di sale)
    public static void newSale(String user){

        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "INSERT INTO sale (username,products) VALUES (?,'{0}');";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //metodo che serve a controllare se è presente il carrello per un dato utente
    public boolean isCartPresent(String user){
        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "SELECT * FROM sale WHERE sale.username ILIKE ? AND saledatetime IS NULL;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return false;

            return true;

        }catch (SQLException e ){
            e.printStackTrace();
        }

        return false;
    }

    //metodo che inizializza un carrello
    public void initCart()
    {
        try
        {
            clearCart();
            setCart();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //metodo che serve per svuotare un carrello
    public void clearCart()
    {
        _cart.clear();
        _cart.set_totalPrice(0);
    }

    //metodo che serve a settare un oggetto di tipo carrello avendo un carrello presente nel database per quel dato utente
    public void setCart() throws SQLException{

        String cartUser = _cart.get_user().get_username();

        if(!isCartPresent(cartUser))
            newSale(cartUser);

        Connection conn = DBConnSingleton.getConn();
        String query = "SELECT s.products FROM sale AS s WHERE s.username ILIKE ?  AND s.saledatetime IS NULL;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString( 1,cartUser);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Array a = rs.getArray(1);
        Integer b []= (Integer[])a.getArray();

        Product p;

        for(int i=0;i<b.length;i++)
        {
                query = "SELECT * FROM products JOIN musician ON products.artist = musician.id WHERE products.id = ? ";
                stmt = conn.prepareStatement(query);
                stmt.setInt( 1,b[i]);
                rs = stmt.executeQuery();

                rs.next();
                if(b[i]!=0) {
                    _cart.addToCart(getProductFromQuery(rs));
                }
        }
    }

    //metodo che aggiunge un certo prodotto in una data quantità al carrello nel database e all'oggetto Cart
    public void addToCart(Product p, int quantity){

        String cartUser = _cart.get_user().get_username();

        try {
                Connection conn = DBConnSingleton.getConn();
                String query = "SELECT products FROM sale WHERE sale.username ILIKE ?  AND saledatetime IS NULL;";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,cartUser);
                ResultSet rs = stmt.executeQuery();

                rs.next();


                Array a = rs.getArray(1);
                Integer b []= (Integer[])a.getArray();
                Integer c[] = new Integer[b.length+quantity];

                for(int i=0;i<b.length;i++){
                    c[i]=b[i];
                }

                for(int j=b.length ; j<b.length+quantity ; j++)
                    c[j]=p.get_id();

                query = "UPDATE sale SET products = '"+conn.createArrayOf(a.getBaseTypeName(),c)+"' WHERE sale.username ILIKE ?  AND saledatetime IS NULL;";
                stmt = conn.prepareStatement(query);
                stmt.setString(1,cartUser);
                stmt.executeUpdate();

                for(int i=0 ; i<quantity ; i++)
                    _cart.addToCart(p);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //metodo che rimuove un prodotto dal carrello nel database e dall'oggetto Cart
    public void removeFromCart(int position){

        Product p = _cart.get_cartList().get(position);
        String cartUser = _cart.get_user().get_username();

        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "SELECT products FROM sale WHERE sale.username ILIKE ? AND saledatetime IS NULL;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            Array a = rs.getArray(1);
            Integer b[]= (Integer[])a.getArray();
            Integer c[] = new Integer[b.length-1];

            int ct=0;

            b[position+1] = 0;
            ct = 0;

            for(int i=0; i<b.length; i++) {
                if (i != 0) {
                    if (b[i] == 0){}
                    else {
                        c[ct] = b[i];
                        ct++;
                    }
                }
                else{
                    c[ct] = b[i];
                    ct++;
                }
            }

            System.out.print("Valori array b: ");
            for(int i=0 ; i<b.length ; i++){
                System.out.print(b[i] + ", ");
            }
            System.out.println("");

            System.out.print("Valori array c: ");
            for(int i=0 ; i<c.length ; i++){
                System.out.print(c[i] + ", ");
            }
            System.out.println("");

            query = "UPDATE sale SET products = '"+conn.createArrayOf(a.getBaseTypeName(),c)+"' WHERE sale.username ILIKE ? AND saledatetime IS NULL;";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            stmt.executeUpdate();

            _cart.removeFromCart(position);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
