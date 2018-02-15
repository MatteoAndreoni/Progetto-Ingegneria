package SupportClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnSingleton {

    private static DBConnSingleton _instance = null;
    public static Connection _conn;

    private DBConnSingleton(){
        try
        {
             _conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/store", "postgres","a");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static DBConnSingleton getInstance(){

        if(_instance==null)
            _instance = new DBConnSingleton();

        return _instance;
    }

    public static Connection getConn()
    {
        return _conn;
    }

}