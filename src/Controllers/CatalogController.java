package Controllers;

import Models.Catalog;
import Models.Musician;
import Models.Product;
import SupportClasses.DBConnSingleton;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CatalogController
{
    private Catalog _catalog;

    public CatalogController(Catalog catalog)
    {
        _catalog = catalog;
        try
        {
            setProductList();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setProductList() throws SQLException
    {
        String q = "select * from products as p join musician as m on p.artist = m.id;";
        PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
        setProductList(pst);
    }

    public void setProductList(PreparedStatement pst) throws SQLException
    {

        ResultSet rs = pst.executeQuery() ;
        _catalog.clear();

        Product p;
        while(rs.next())
        {
            p = new Product();
            p.set_code(rs.getInt(1));
            p.set_title(rs.getString(2));
            Array a = rs.getArray(3);
            ArrayList<String> b = new ArrayList(Arrays.asList(a));
            p.set_trackList(b);
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
            _catalog.add(p);
        }
    }

    public void getProductByGenre(String genre) throws SQLException
    {
            String q = "select * from products as p join musician as m on p.artist = m.id where p.genre ilike ?;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, "%" + genre + "%");
            setProductList(pst);
    }

    public void getProductByPrice(String price) throws SQLException
    {
            String q = "select * from products as p join musician as m on p.artist = m.id where p.price <= ?;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setBigDecimal(1, new BigDecimal(price));
            setProductList(pst);
    }

    public void getProductByTitle(String title) throws SQLException
    {
            String q = "select * from products as p join musician as m on p.artist = m.id where p.title ilike ?;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, "%" + title + "%");
            setProductList(pst);

    }

    public void getProductByArtist(String name) throws SQLException
    {
            String q = "select * from products as p join musician as m on p.artist = m.id where m.name ilike ? or array_to_string(involvedartists, ?) ilike ?";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1,"%" + name + "%");
            pst.setString(2,",");
            pst.setString(3,"%" + name + "%");
            setProductList(pst);

    }

    public static void addProduct(String titolo, String artista, String genere, String prezzo, String pezzi ){

        LocalDateTime t = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        String s = "";
        ArrayList<String> l= new ArrayList<String>(Arrays.asList(s));
        String string[] = new String[]{};
        Musician m;
        m= new Musician(artista,genere, localDate, l);
        ArrayList<Musician> mm = new ArrayList<Musician>(Arrays.asList(m));

        String q = "Insert into products (title, coverimage,price,firstadded,artist,genre,productstocks) values (?,?,?,?,?,?,?)";
        String b = "Insert into musician (name,genre,birthdate,instruments) values (?,?,?,?)";
        try{
            try {
                PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(b);
                pst.setString(1, artista);
                pst.setString(2, genere);
                pst.setObject(3,localDate);
                pst.setObject(4, DBConnSingleton.getConn().createArrayOf("VARCHAR",string));
                pst.executeUpdate();
            }finally {
                PreparedStatement pst2 = DBConnSingleton.getConn().prepareStatement("SELECT id FROM musician WHERE name=? AND genre=?");
                pst2.setString(1, artista);
                pst2.setString(2, genere);
                ResultSet rs = pst2.executeQuery();
                if (rs.next()) {
                    Integer id = rs.getInt("id");
                    String cover = "resources/gear.png";
                    PreparedStatement pst3 = DBConnSingleton.getConn().prepareStatement(q);
                    pst3.setString(1, titolo);
                    pst3.setString(2, cover);
                    pst3.setFloat(3, Float.valueOf(prezzo.toString()));
                    pst3.setObject(4, localDate);
                    pst3.setInt(5, id);
                    pst3.setString(6, genere);
                    pst3.setInt(7, Integer.valueOf(pezzi));
                    pst3.executeUpdate();

                    PreparedStatement pst4 = DBConnSingleton.getConn().prepareStatement("SELECT id FROM products WHERE title=? AND artist=? AND genre=?");
                    pst4.setString(1, titolo);
                    pst4.setInt(2, id);
                    pst4.setString(3, genere);
                    ResultSet rs2 = pst4.executeQuery();
                    if (rs2.next()) {
                        Integer id2 = rs2.getInt("id");

                        Product pr;
                        pr = new Product(id2, titolo, l, "", Float.valueOf(prezzo.toString()), t, s, m, genere, mm, l, Integer.valueOf(pezzi));
                    }
                    JOptionPane.showMessageDialog(null, "Inserimento prodotto avvenuto con successo");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void modifyProduct(String nome, String prezzo, String pezzi){
        String s ="update products set price = ?, productstocks = ? where title ilike ?";
        try {
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(s);
            pst.setFloat(1, Float.valueOf(prezzo.toString()));
            pst.setInt(2, Integer.valueOf(pezzi.toString()));
            pst.setString(3, nome);
            pst.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(String nome){
        String s ="delete from products where title ilike ?";
        try {
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(s);
            pst.setString(1,nome);
            pst.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

