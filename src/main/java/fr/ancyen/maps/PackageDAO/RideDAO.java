package fr.ancyen.maps.PackageDAO;

import fr.masterdapm.ancyen.model.Position;
import fr.masterdapm.ancyen.model.Ride;
import fr.masterdapm.ancyen.model.Waypoint;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RideDAO extends DAO<Ride>{
    public Ride create(Ride ride) {
        try {
            PreparedStatement prepare = this.connect
                    .prepareStatement(
                            "INSERT INTO ride VALUES(?, ?, ?, ? ,?, ? ,? ,?, ?, ?, ?, ?)"
                    );
            prepare.setInt(1, count()+1);
            prepare.setString(2, ride.getIdOrganizer());
            prepare.setString(3, ride.getDeparturePlace());
            prepare.setString(4, ride.getDepartureDate());
            prepare.setString(5, ride.getDepartureHour());
            prepare.setString(6, ride.getArrivalPlace());
            prepare.setString(7, ride.getDistance());
            prepare.setString(8, ride.getDuration());
            prepare.setBytes(9, toByteArray(ride.getPositions()));
            prepare.setBytes(10, toByteArray(ride.getWaypoints()));
            prepare.setBytes(11, toByteArray(ride.getAutorisedEmails()));
            prepare.setString(12, ride.getState());

            prepare.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return ride;
    }


    public Ride find(Object... ids) {
        int id = (Integer) ids[0];
        Ride retour = null;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM ride WHERE IDRIDE = " + id
                    );
            if(result.first())
                retour = new Ride(
                        id,
                        result.getString("idOrganiser"),
                        result.getString("departurePlace"),
                        result.getString("departureDate"),
                        result.getString("departureHour"),
                        result.getString("arrivalPlace"),
                        result.getString("distance"),
                        result.getString("duration"),
                        toPositions(result.getBytes("positions")),
                        toWaypoints(result.getBytes("waypoints")),
                        toStrings(result.getBytes("autorisedEmails")),
                        result.getString("state")
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
        int id = (Integer) ids[0];
        try {

            this.connect
                    .createStatement()
                    .executeUpdate(
                            "DELETE FROM ride WHERE idRide = " + id
                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAll() {
        ResultSet result = null;
        try {

            result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM RIDE"
                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Ride[] getWithEmail(String email) {
        List<Ride> rides = new ArrayList<>();
        ResultSet result = getAll();
        try {
            while (result.next()) {
                String[] emails = toStrings(result.getBytes("autorisedEmails"));
                boolean find = false;

                for (String s : emails
                        ) {
                    if (s.equals(email)) find = true;
                }
                if (find) {
                    rides.add(new Ride(
                            result.getInt("idRide"),
                            result.getString("idOrganiser"),
                            result.getString("departurePlace"),
                            result.getString("departureDate"),
                            result.getString("departureHour"),
                            result.getString("arrivalPlace"),
                            result.getString("distance"),
                            result.getString("duration"),
                            toPositions(result.getBytes("positions")),
                            toWaypoints(result.getBytes("waypoints")),
                            toStrings(result.getBytes("autorisedEmails")),
                            result.getString("state")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Ride[] retour = new Ride[rides.size()];
        for (int i=0; i<rides.size(); i++){
            retour[i] = rides.get(i);
        }
        return retour;
    }

    public int count(){
        int c = -1;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT count(*) as count FROM ride"
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

    public Position[] toPositions(byte[] b) {
        if (b != null) {
            ByteArrayInputStream binp = new ByteArrayInputStream(b);
            List<Position> positions = new ArrayList<Position>();
            try {
                ObjectInputStream ois = new ObjectInputStream(binp);
                boolean boucle = true;
                while (boucle) {
                    try {
                        positions.add((Position) ois.readObject());
                    } catch (EOFException e) {
                        boucle = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Position[] p = new Position[positions.size()];
            for (int i = 0; i < positions.size(); i++) {
                p[i] = positions.get(i);
            }
            return p;
        }
        return null;
    }

    public Waypoint[] toWaypoints(byte[] b) {
        if (b != null) {
            ByteArrayInputStream binp = new ByteArrayInputStream(b);
            List<Waypoint> waypoints = new ArrayList<Waypoint>();
            try {
                ObjectInputStream ois = new ObjectInputStream(binp);
                boolean boucle = true;
                while (boucle) {
                    try {
                        waypoints.add((Waypoint) ois.readObject());
                    } catch (EOFException e) {
                        boucle = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Waypoint[] w = new Waypoint[waypoints.size()];
            for (int i = 0; i < waypoints.size(); i++) {
                w[i] = waypoints.get(i);
            }
            return w;
        }
        return null;
    }

    public String[] toStrings(byte[] b) {
        if (b != null) {
            ByteArrayInputStream binp = new ByteArrayInputStream(b);
            List<String> strings = new ArrayList<String>();
            try {
                ObjectInputStream ois = new ObjectInputStream(binp);
                boolean boucle = true;
                while (boucle) {
                    try {
                        strings.add((String) ois.readObject());
                    } catch (EOFException e) {
                        boucle = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String[] s = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                s[i] = strings.get(i);
            }
            return s;
        }
        return null;
    }
}
