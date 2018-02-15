package SupportClasses;

import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationManager {


    //returns -1 if already registred, 0 if error, 1 if success
    public static int registerUser(User u) {

        if (checkRegistration(u))
            return -1;

        try {

            String q = "INSERT INTO Utente(cf,username,password,name,surname,address,telephone,cellphone,isemployee) VALUES (?,?,?,?,?,?,?,?,?);";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, u.get_CF());
            pst.setString(2, u.get_username());
            pst.setString(3, u.get_password());
            pst.setString(4, u.get_name());
            pst.setString(5, u.get_surname());
            pst.setString(6, u.get_address());
            pst.setString(7, u.get_telephone());
            pst.setString(8, u.get_cellphone());
            pst.setBoolean(9, u.get_isEmployee());

            return pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static boolean checkRegistration(User u){

        try {


            String q = "SELECT * FROM utente AS u WHERE u.username ILIKE ?;";
            PreparedStatement pst = DBConnSingleton.getConn().prepareStatement(q);
            pst.setString(1, u.get_username());

            ResultSet rs = pst.executeQuery();

            if(rs.next())
                return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}
