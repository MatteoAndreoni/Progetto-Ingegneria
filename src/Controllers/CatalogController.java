//classe che controlla gli oggetti Catalog
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

    //metodo usato per settare una query che restituisce tutti i prodotti in join con i musicisti
    //la query verrà poi passata al metodo successivo
    public void setProductList() throws SQLException
    {
        String q = "SELECT * FROM products AS p JOIN musician AS m ON p.artist = m.id ORDER BY p.id";
        PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
        setProductList(pst);
    }

    //metodo che setta un oggetto Catalog in base al tipo di query che riceve
    public void setProductList(PreparedStatement pst) throws SQLException
    {
        ResultSet rs = pst.executeQuery() ;
        _catalog.clear();

        //Product p;
        while(rs.next())
        {
            _catalog.add(getProductFromQuery(rs));
            /*String name = rs.getString(14);
            String genre = rs.getString(15);
            LocalDate birthDate = rs.getTimestamp(16).toLocalDateTime().toLocalDate();
            Array g = rs.getArray(17);
            ArrayList<String> instruments = new ArrayList(Arrays.asList(g));
            Musician artist = new Musician(name, genre, birthDate, instruments);

            p = new Product(
                    rs.getInt(1), //id
                    rs.getString(2), //title
                    new ArrayList(Arrays.asList(rs.getArray(3))), //tracklist
                    rs.getString(4), //coverimage
                    rs.getFloat(5), //price
                    rs.getTimestamp(6).toLocalDateTime(), //firstAddedInStore
                    rs.getString(7), //description
                    artist, //musician
                    rs.getString(9), //genre
                    new ArrayList(Arrays.asList(rs.getArray(10))), //involvedArtist
                    new ArrayList(Arrays.asList(rs.getArray(11))), //usedInstruments
                    rs.getInt(12) //productStocks
            );*/
            /*p.set_code(rs.getInt(1));
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

            p.set_artist(m);*/
            //_catalog.add(p);
        }
    }

    //metodo che setta una query di ricerca dei prodotti per genere
    public void getProductByGenre(String genre) throws SQLException
    {
            String q = "SELECT * FROM products AS p JOIN musician AS m ON p.artist = m.id WHERE p.genre ILIKE ? ORDER BY p.id;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, "%" + genre + "%");
            setProductList(pst);
    }

    //metodo che setta una query di ricerca dei prodotti per prezzo minore al prezzo ricevuto come input
    public void getProductByPrice(String price) throws SQLException
    {
            String q = "SELECT * FROM products AS p JOIN musician AS m ON p.artist = m.id WHERE p.price <= ? ORDER BY p.id;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setBigDecimal(1, new BigDecimal(price));
            setProductList(pst);
    }

    //metodo che setta una query di ricerca dei prodotti per titolo
    public void getProductByTitle(String title) throws SQLException
    {
            String q = "SELECT * FROM products AS p JOIN musician AS m ON p.artist = m.id WHERE p.title ILIKE ? ORDER BY p.id;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, "%" + title + "%");
            setProductList(pst);

    }

    //metodo che setta una query di ricerca dei prodotti per nome dell'artista
    public void getProductByArtist(String name) throws SQLException
    {
            String q = "SELECT * FROM products AS p JOIN musician AS m ON p.artist = m.id WHERE m.name ILIKE ? OR array_to_string(involvedartists, ?) ILIKE ? ORDER BY p.id;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1,"%" + name + "%");
            pst.setString(2,",");
            pst.setString(3,"%" + name + "%");
            setProductList(pst);

    }

    //metodo che aggiunge un prodotto con tutte le sue informazioni al database
    //percorso della copertina e descrizione del prodotto sono opzionali
    public static void addProduct(String titolo, String artista, String genere, String prezzo, String pezzi, String percorsoCopertina, String descrizione ){

        LocalDateTime t = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        String s = "";
        ArrayList<String> l= new ArrayList<>(Arrays.asList(s));
        String string[] = new String[]{};
        Musician mainArtist;
        mainArtist = new Musician(artista,genere, localDate, l);
        ArrayList<Musician> involvedArtists = new ArrayList<>(Arrays.asList(mainArtist));

        String productQuery = "INSERT INTO products (title, coverimage,price,firstadded,description, artist,genre,productstocks) VALUES (?,?,?,?,?,?,?,?)";
        String musicianQuery = "INSERT INTO musician (name,genre,birthdate,instruments) VALUES (?,?,?,?)";
        try{
            try {
                PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(musicianQuery);
                pst.setString(1, artista);
                pst.setString(2, genere);
                pst.setObject(3, localDate);
                pst.setObject(4, DBConnSingleton.getConn().createArrayOf("VARCHAR",string));
                pst.executeUpdate();
            }finally {
                PreparedStatement pst2 = DBConnSingleton.getConn().prepareStatement("SELECT id FROM musician WHERE name=? AND genre=?");
                pst2.setString(1, artista);
                pst2.setString(2, genere);
                ResultSet rs = pst2.executeQuery();
                if (rs.next()) {
                    Integer id = rs.getInt("id");
                    String cover = "";
                    if(percorsoCopertina.equals(""))
                        cover = "resources/gear.png";
                    else
                        cover = percorsoCopertina;
                    PreparedStatement pst3 = DBConnSingleton.getConn().prepareStatement(productQuery);
                    pst3.setString(1, titolo);
                    pst3.setString(2, cover);
                    pst3.setFloat(3, Float.valueOf(prezzo.toString()));
                    pst3.setObject(4, localDate);
                    pst3.setString(5, descrizione);
                    pst3.setInt(6, id);
                    pst3.setString(7, genere);
                    pst3.setInt(8, Integer.valueOf(pezzi));
                    pst3.executeUpdate();

                    PreparedStatement pst4 = DBConnSingleton.getConn().prepareStatement("SELECT id FROM products WHERE title=? AND artist=? AND genre=?");
                    pst4.setString(1, titolo);
                    pst4.setInt(2, id);
                    pst4.setString(3, genere);
                    ResultSet rs2 = pst4.executeQuery();
                    if (rs2.next()) {
                        Integer id2 = rs2.getInt("id");

                        Product pr;
                        pr = new Product(id2, titolo, l, "", Float.valueOf(prezzo.toString()), t, s, mainArtist, genere, involvedArtists, l, Integer.valueOf(pezzi));
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

    //metodo che serve per modificare la quantità di un prodotto presente nel magazzino
    public static void updateProduct(int id, int stock) {
        try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "Prodotto non presente");
            } else {
                String s = "UPDATE products SET productstocks = ? WHERE id = ?";
                try {
                    pst = DBConnSingleton.getConn().prepareStatement(s);
                    pst.setInt(1, stock);
                    pst.setInt(2, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modifica effettuata con successo");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //metodo che serve per eliminare un prodotto dal database
    public static void deleteProduct(int id){

        try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(!rs.isBeforeFirst()){
                JOptionPane.showMessageDialog(null, "Prodotto non presente");
            }
            else{
                String s ="DELETE FROM products WHERE id = ?";
                try {
                    pst = DBConnSingleton.getConn().prepareStatement(s);
                    pst.setInt(1, id);
                    pst.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //metodo che estrapola un prodotto dal risultato di una query (product+musician)
    //usato anche nel CartController
    public static Product getProductFromQuery(ResultSet rs) throws SQLException{
        String name = rs.getString(14);
        String genre = rs.getString(15);
        LocalDate birthDate = rs.getTimestamp(16).toLocalDateTime().toLocalDate();
        Array g = rs.getArray(17);
        ArrayList<String> instruments = new ArrayList(Arrays.asList(g));
        Musician artist = new Musician(name, genre, birthDate, instruments);

        Product p = new Product(
                rs.getInt(1), //id
                rs.getString(2), //title
                new ArrayList(Arrays.asList(rs.getArray(3))), //tracklist
                rs.getString(4), //coverimage
                rs.getFloat(5), //price
                rs.getTimestamp(6).toLocalDateTime(), //firstAddedInStore
                rs.getString(7), //description
                artist, //musician
                rs.getString(9), //genre
                new ArrayList(Arrays.asList(rs.getArray(10))), //involvedArtist
                new ArrayList(Arrays.asList(rs.getArray(11))), //usedInstruments
                rs.getInt(12) //productStocks
        );

        return p;
    }

        //metodo usato per avvertire gli impiegati della scarsa disponibilità di certi prodotti
        public void emptyAlert(){
        //controllo il database per vedere se ci sono prodotti con meno di due rimanenze in magazzino
        String query = "SELECT * FROM products WHERE productstocks < 2;";

        try{
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            ArrayList<Product> almostEmptyProducts = new ArrayList();

            while(rs.next()){
                Product p = new Product(0, "");
                p.set_code(rs.getInt("id"));
                p.set_title(rs.getString("title"));
                almostEmptyProducts.add(p);
            }
            String noProducts = "";

            System.out.println("Almost empty products: ");
            if(almostEmptyProducts.size() != 0) {
                for (int i = 0; i < almostEmptyProducts.size(); i++) {
                    System.out.println(almostEmptyProducts.get(i).get_id() + " - " + almostEmptyProducts.get(i).get_title());
                    noProducts = noProducts + almostEmptyProducts.get(i).get_id() + " - " + almostEmptyProducts.get(i).get_title() + "\n";
                }

                JOptionPane.showMessageDialog(null, "I seguenti prodotti hanno zero o una sola rimanenza in magazzino:\n\n" + noProducts);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

