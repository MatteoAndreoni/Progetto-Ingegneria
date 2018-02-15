package Controllers;

import Models.Cart;
import Models.Sale;
import SupportClasses.DBConnSingleton;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class SaleController {

    private Sale _sale;

    public SaleController(Sale s){

        _sale=s;

    }


    public void buyCart(){

        Cart c = _sale.get_cart();
        String cartUser = c.get_user().get_username();

        try {
            Connection conn = DBConnSingleton.getConn();

            LocalDateTime localDate = _sale.get_saleDate();

            String query = "SELECT products FROM sale WHERE saledatetime IS null";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            Array a = rs.getArray(1);
            Integer b[]= (Integer[])a.getArray();

            /*System.out.println("Prodotti comprati: ");
            for(int i=0 ; i<b.length ; i++){
                System.out.print(b[i] + ", ");
            }*/
            Integer prodottiComprati[] = new Integer[b.length-1];
            Integer occorrenze[][] = new Integer[b.length][];
            int ct1, ct2 = 0;

            for(int i=1 ; i<b.length ; i++){
                prodottiComprati[i-1] = b[i];
            }

            selectionSort(prodottiComprati);

            for(int i=0 ; i<prodottiComprati.length ; i++){

            }



            /*for(int i=0 ; i<prodottiComprati.length ; i++){
                for(int j=0 ; j<prodottiComprati.length ; j++){
                    if(prodottiComprati[j] == prodottiComprati[i])
                        occorrenze[i]++;
                }
            }*/

            System.out.println("Prodotti comprati: ");
            for(int i=0 ; i<prodottiComprati.length ; i++){
                System.out.print(prodottiComprati[i] + ", ");
            }
            System.out.println("");

            /*System.out.println("occorrenze: ");
            for(int i=0 ; i<occorrenze.length ; i++){
                System.out.print(occorrenze[i] + ", ");
            }*/


            String query1 = "UPDATE sale SET saledatetime = ? , price = ? , ip = ? , paymenttype = ? , deliverytype = ? where sale.username ILIKE ? AND saledatetime is null;;";
            stmt = conn.prepareStatement(query1);
            stmt.setObject(1,localDate);
            stmt.setObject(2,_sale.get_salePrice());
            stmt.setObject(3,"127.0.0.1");
            stmt.setObject(4,_sale.get_paymentType());
            stmt.setObject(5,_sale.get_deliveryType());

            stmt.setObject(6,cartUser);
            stmt.executeUpdate();



            //CartController.newSale(cartUser);

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


