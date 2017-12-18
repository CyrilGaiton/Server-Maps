package fr.ancyen.maps.PackageDAO.PackageConnectionDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {

    private static String url = "jdbc:h2:tcp://localhost/~/maps";
    private static String user = "sa";
    private static String password = "";

    private static Connection connect = null;


    public static Connection getInstance(){
        if (connect == null) {
            try {
                connect = DriverManager.getConnection(url, user, password);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connect;
    }


}
