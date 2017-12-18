package fr.ancyen.maps.PackageDAO;

import fr.masterdapm.ancyen.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO extends DAO<User> {

    public User create(User user) {
        try {
            PreparedStatement prepare = this.connect
                    .prepareStatement(
                            "INSERT INTO USER VALUES(?, ?, ?, ?)"
                    );
            prepare.setString(1, user.getEmail());
            prepare.setString(2, user.getPassword());
            prepare.setString(3, user.getLastName());
            prepare.setString(4, user.getFirstName());

            prepare.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User find(Object... ids) {
        String id = (String) ids[0];
        User retour = null;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM USER WHERE email = " + "'" + id + "'"
                    );
            if(result.first())
                retour = new User(
                        id,
                        result.getString("password"),
                        result.getString("lastname"),
                        result.getString("firstname")
                );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retour;

    }


//    public User update(User user) {
//        try {
//
//            this.connect
//                    .createStatement()
//                    .executeUpdate(
//                            "UPDATE date SET date = " + date.getDate()
//                                    + " WHERE idDate = " + date.getIdDate()
//                    );
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return date;
//    }


    public void delete(Object... ids) {
        String id = (String) ids[0];
        try {

            this.connect
                    .createStatement()
                    .executeUpdate(
                            "DELETE FROM user WHERE email = " + "'" + id + "'"
                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int count(){
        int c = -1;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT count(*) as count FROM user"
                    );
            if(result.first()){
                c = result.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int getNextId(){
        return count();
    }

}
