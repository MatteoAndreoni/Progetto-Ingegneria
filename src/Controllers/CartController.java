package Controllers;

import Models.Cart;
import Models.Musician;
import Models.Product;
import SupportClasses.DBConnSingleton;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

    private boolean isCartPresent(String user){
        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "select * from sale where sale.username ILIKE ? AND saledatetime is null;";
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

    public void clearCart()
    {
        _cart.clear();
        _cart.set_totalPrice(0);
    }

    public void setCart() throws SQLException{

        String cartUser = _cart.get_user().get_username();

        if(!isCartPresent(cartUser))
            newSale(cartUser);

        Connection conn = DBConnSingleton.getConn();
        String query = "select s.products from sale as s where s.username ILIKE ?  AND s.saledatetime is null;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString( 1,cartUser);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Array a = rs.getArray(1);
        Integer b []= (Integer[])a.getArray();

        Product p;


            for(int i=0;i<b.length;i++)
            {

                query = "select * from products join musician on products.artist = musician.id where products.id = ? ";
                stmt = conn.prepareStatement(query);
                stmt.setInt( 1,b[i]);
                rs = stmt.executeQuery();

                rs.next();
                if(b[i]!=0) {
                    p = new Product();
                    p.set_code(rs.getInt(1));
                    p.set_title(rs.getString(2));
                    Array a1 = rs.getArray(3);
                    ArrayList<String> b1 = new ArrayList(Arrays.asList(a));
                    p.set_trackList(b1);
                    p.set_coverImage(rs.getString(4));
                    p.set_price(rs.getFloat(5));
                    p.set_firstAddedInStore(rs.getTimestamp(6).toLocalDateTime());
                    p.set_description(rs.getString(7));
                    p.set_genre(rs.getString(9));
                    Array c = rs.getArray(10);
                    ArrayList<Musician> d = new ArrayList(Arrays.asList(c));
                    p.set_involvedArtists(d);
                    Array e = rs.getArray(11);
                    ArrayList<String> f = new ArrayList(Arrays.asList(e));
                    p.set_usedInstruments(f);
                    p.set_productStocks(rs.getInt(12));
                    String name = rs.getString(14);
                    String genre = rs.getString(15);
                    LocalDate birthDate = rs.getTimestamp(16).toLocalDateTime().toLocalDate();
                    Array g = rs.getArray(17);
                    ArrayList<String> instruments = new ArrayList(Arrays.asList(g));
                    Musician m = new Musician(name, genre, birthDate, instruments);
                    p.set_artist(m);
                    _cart.addToCart(p);
                }
            }
    }



    public void addToCart(Product p){

        String cartUser = _cart.get_user().get_username();

        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "select products from sale where sale.username ILIKE ?  AND saledatetime is null;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            ResultSet rs = stmt.executeQuery();

            rs.next();


            Array a = rs.getArray(1);
            Integer b []= (Integer[])a.getArray();
            Integer c[] = new Integer[b.length+1];

            for(int i=0;i<b.length;i++){
                c[i]=b[i];
            }

            c[b.length]=p.get_code();

            query = "UPDATE sale SET products = '"+conn.createArrayOf(a.getBaseTypeName(),c)+"' where sale.username ILIKE ?  AND saledatetime is null;";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            stmt.executeUpdate();

            _cart.addToCart(p);


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeFromCart(int position){

        Product p = _cart.get_cartList().get(position);
        String cartUser = _cart.get_user().get_username();

        try {
            Connection conn = DBConnSingleton.getConn();
            String query = "select products from sale where sale.username ILIKE ? AND saledatetime is null;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            ResultSet rs = stmt.executeQuery();

            rs.next();


            Array a = rs.getArray(1);
            Integer b[]= (Integer[])a.getArray();
            Integer c[] = new Integer[b.length-1];

            int ct=0;
            //boolean removed=false;

            /*for(Integer i : b)
            {
                if(p.get_code() != b[i] || removed)
                {
                    c.add(i);
                }
                else
                {
                    removed = true;
                }
            }*/

            /*for(int i=0;i<b.length-1;i++)
            {
                if(p.get_code()!=b[i])
                {
                    c[i] = b[i];
                    ct++;
                }
                else
                {
                    position=i;
                }
            }*/

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

            query = "UPDATE sale SET products = '"+conn.createArrayOf(a.getBaseTypeName(),c)+"' where sale.username ILIKE ? AND saledatetime is null;";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cartUser);
            stmt.executeUpdate();

            _cart.removeFromCart(position);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }



}
