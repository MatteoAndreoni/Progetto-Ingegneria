//metodo usato per controllare oggetti di tipo Sale
package Controllers;

import Models.Cart;
import Models.Sale;
import SupportClasses.DBConnSingleton;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class SaleController {

    private Sale _sale;

    public SaleController(Sale s){
        _sale=s;
    }

    //metodo usato per comprare un carrello (oggetto di tipo Cart)
    //il metodo inserisce le informazioni mancanti, come data, ip, tipo di pagamento e consegna
    //nella riga della tabella Sale relativa al carrello dell'utente
    public void buyCart(){

        Cart c = _sale.get_cart();
        String cartUser = c.get_user().get_username();
        boolean isPremium = c.get_user().get_isPremium();

        try {
            Connection conn = DBConnSingleton.getConn();

            LocalDateTime localDate = _sale.get_saleDate();

            String query = "SELECT products FROM sale WHERE username = ? AND saledatetime IS NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cartUser);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            Array a = rs.getArray(1);
            Integer b[]= (Integer[])a.getArray();

            System.out.print("Array b: ");
            for(int i=0 ; i<b.length ; i++){
                System.out.print(b[i] + ", ");
            }

            Integer boughtProducts[] = new Integer[b.length-1];


            for(int i=1 ; i<b.length ; i++){
                boughtProducts[i-1] = b[i];
            }

            selectionSort(boughtProducts);



            for(int i=0 ; i<boughtProducts.length ; i++) {
                query = "UPDATE products SET productstocks = productstocks - 1 WHERE id = ?;";
                stmt = conn.prepareStatement(query);
                stmt.setObject(1, boughtProducts[i]);

                stmt.executeUpdate();
            }

            System.out.println("Prodotti comprati: ");
            for(int i=0 ; i<boughtProducts.length ; i++){
                System.out.print(boughtProducts[i] + ", ");
            }
            System.out.println("");

            if(!isPremium){
                if(c.get_totalPrice() > 250){
                    String query2 = "SELECT COUNT(*) FROM sale WHERE username = ? AND price > 250;";
                    stmt = conn.prepareStatement(query2);
                    stmt.setObject(1, cartUser);
                    ResultSet rs2 = stmt.executeQuery();

                    rs2.next();

                    int numAcquisti250 = rs2.getInt(1);

                    System.out.println("Num. acquisti > 250: "+ numAcquisti250);

                    if(numAcquisti250 == 2){
                        //rendo l'utente premium
                        c.get_user().set_isPremium(true);

                        query2 = "UPDATE utente SET ispremium = TRUE WHERE username = ?;";
                        stmt = conn.prepareStatement(query2);
                        stmt.setObject(1, cartUser);
                        stmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Complimenti, il tuo account è diventato premium!\nOra le spese di spedizione sono gratuite e il totale del carrello sarà scontato del 10%.");
                    }
                }
            }

            String query1 = "UPDATE sale SET saledatetime = ? , price = ? , ip = ? , paymenttype = ? , deliverytype = ? WHERE sale.username ILIKE ? AND saledatetime IS NULL;";
            stmt = conn.prepareStatement(query1);
            stmt.setObject(1,localDate);
            stmt.setObject(2,_sale.get_salePrice());
            stmt.setObject(3,"127.0.0.1");
            stmt.setObject(4,_sale.get_paymentType());
            stmt.setObject(5,_sale.get_deliveryType());

            stmt.setObject(6,cartUser);
            stmt.executeUpdate();

            CartController.newSale(_sale.get_cart().get_user().get_username());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void selectionSort(Integer [] array) {

        for(int i = 0; i < array.length-1; i++) {
            int minimo = i; //Partiamo dall' i-esimo elemento
            for(int j = i+1; j < array.length; j++) {

                //Qui avviene la selezione, ogni volta
                //che nell' iterazione troviamo un elemento piú piccolo di minimo
                //facciamo puntare minimo all' elemento trovato
                if(array[minimo]>array[j]) {
                    minimo = j;
                }
            }

            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if(minimo!=i) {
                int k = array[minimo];
                array[minimo]= array[i];
                array[i] = k;
            }
        }
    }
}


