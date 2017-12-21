package fr.ancyen.maps.PackageDAO;

import fr.masterdapm.ancyen.model.Statistics;
import fr.masterdapm.ancyen.model.TimedPosition;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO extends DAO<Statistics> {

    public Statistics create(Statistics statistics) {
        try {
            PreparedStatement prepare = this.connect
                    .prepareStatement(
                            "INSERT INTO statistics VALUES(?, ?, ?)"
                    );
            prepare.setInt(1, statistics.getIdRide());
            prepare.setString(2, statistics.getIdUser());
            prepare.setBytes(3, toByteArray(statistics.getTimedPositions()));

            prepare.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }

    public Statistics find(Object... ids) {
        int idRide = (Integer) ids[0];
        String idUser = (String) ids[1];
        Statistics retour = null;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM statistics WHERE idRide = " + idRide
                            +" AND idUser = " + "'" + idUser + "'"
                    );
            if(result.first())
                retour = new Statistics(
                        idRide,
                        idUser,
                        toTimedPositions(result.getBytes("timedPositions"))
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
        int idRide = (Integer) ids[0];
        int idUser = (Integer) ids[1];
        try {

            this.connect
                    .createStatement()
                    .executeUpdate(
                            "DELETE FROM statistics WHERE idRide = " + idRide
                                    +" AND idUser = " + "'" + idUser + "'"
                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statistics[] getWithEmail(String email) {
        Statistics[] retour = null;
        List<Statistics> statistics = new ArrayList<>();
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM statistics WHERE idUser = " + "'" + email + "'"
                    );
            if(result.first())
                statistics.add(
                        new Statistics(
                                result.getInt("idRide"),
                                email,
                                toTimedPositions(result.getBytes("timedPositions"))
                        ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        retour = new Statistics[statistics.size()];
        for (int i=0; i<statistics.size(); i++){
            retour[i] = statistics.get(i);
        }

        return retour;

    }


    public int count(){
        int c = -1;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT count(*) as count FROM statistics"
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

    public byte[] toByteArray(Object[] o) {
        if (o != null) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] b = null;
            try {
                ObjectOutputStream oos = new ObjectOutputStream(bout);
                for (int i = 0; i < o.length; i++) {
                    oos.writeObject(o[i]);
                }
                b = bout.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
        return null;
    }

    public TimedPosition[] toTimedPositions(byte[] b) {
        if (b!= null) {
            ByteArrayInputStream binp = new ByteArrayInputStream(b);
            List<TimedPosition> timedPositions = new ArrayList<TimedPosition>();
            try {
                ObjectInputStream ois = new ObjectInputStream(binp);
                boolean boucle = true;
                while (boucle) {
                    try {
                        timedPositions.add((TimedPosition) ois.readObject());
                    } catch (EOFException e) {
                        boucle = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            TimedPosition[] p = new TimedPosition[timedPositions.size()];
            for (int i = 0; i < timedPositions.size(); i++) {
                p[i] = timedPositions.get(i);
            }
            return p;
        }
        return null;
    }
}
