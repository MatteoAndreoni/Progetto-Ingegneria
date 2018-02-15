package Controllers;

import Models.Cart;
import Models.Sale;
import SupportClasses.DBConnSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

            String query = "UPDATE sale SET saledatetime = ? , price = ? , ip = ? , paymenttype = ? , deliverytype = ? where sale.username ILIKE ? AND saledatetime is null;;";
            PreparedStatement stmt = conn.prepareStatement(query);
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



}
