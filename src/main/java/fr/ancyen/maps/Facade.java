package fr.ancyen.maps;

import fr.ancyen.maps.PackageDAO.RideDAO;
import fr.ancyen.maps.PackageDAO.StatisticsDAO;
import fr.ancyen.maps.PackageDAO.UserDAO;
import fr.masterdapm.ancyen.model.Ride;
import fr.masterdapm.ancyen.model.Statistics;
import fr.masterdapm.ancyen.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class Facade{
    private UserDAO userDAO = new UserDAO();
    private RideDAO rideDAO = new RideDAO();
    private StatisticsDAO statisticsDAO = new StatisticsDAO();


    public void addUser(ObjectInputStream ois) {
        User user = null;
        try {
            user = (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        userDAO.create(user);
    }

    public void addRide(ObjectInputStream ois){
        Ride ride = null;
        try {
            ride = (Ride) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rideDAO.create(ride);
    }

    public void addStatistics(ObjectInputStream ois){
        Statistics statistics = null;
        try {
            statistics = (Statistics) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        statisticsDAO.create(statistics);
    }

    public void gatStatisticsWithEmail(ObjectInputStream ois, ObjectOutputStream oos){
        String email = null;
        try {
            email = (String) ois.readObject();
        Statistics[] statistics = statisticsDAO.getWithEmail(email);
        for (Statistics s:statistics
                ) {
                oos.writeObject("statistics");
                oos.writeObject(s);
        }
            oos.writeObject("close");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getRidesWithEmail(ObjectInputStream ois, ObjectOutputStream oos){
        String email = null;
        try {
            email = (String) ois.readObject();
        Ride[] rides = rideDAO.getWithEmail(email);
        for (Ride r:rides
                ) {
                oos.writeObject("ride");
                oos.writeObject(r);
        }
            oos.writeObject("close");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void getUser(ObjectInputStream ois, ObjectOutputStream oos){
//        String email = null;
//        try {
//            email = (String) ois.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        User user = userDAO.getWithEmail(email);
//        try {
//            if (user != null) {
//                oos.writeObject("OK");
//                oos.writeObject(user);
//            }
//            else {
//                oos.writeObject("KO");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
