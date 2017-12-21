package fr.ancyen.maps;

import fr.ancyen.maps.PackageDAO.MetierDAO;
import fr.ancyen.maps.PackageDAO.RideDAO;
import fr.ancyen.maps.PackageDAO.StatisticsDAO;
import fr.ancyen.maps.PackageDAO.UserDAO;
import fr.masterdapm.ancyen.model.*;

public class Test {
    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        userDAO.create(new User("mj3@laposte.net", "mdp28"
//                , "marie", "Jeanne"));
//
//        User u = userDAO.find("fd@gmail.com");
//        System.out.println(u.getFirstName());
//        userDAO.delete("mj@laposte.net");


//        Position[] positions = {new Position(1, 2, 3),
//        new Position(4, 5, 6)};
//        Waypoint[] waypoints = {new Waypoint(1, 2, 3,"foret"),
//        new Waypoint(4, 5, 6, "lac")};
//        String[] autoEmails = {"lol@", "mdr@"};
//
//        RideDAO rideDAO = new RideDAO();
//        rideDAO.create(new Ride(1, "jd@gmail.com", "depart"
//                , "depardate", "depar hour",
//                "arric place", "28km", "3h38",
//                positions, waypoints, autoEmails));
//
//        Ride r = rideDAO.find(1);
//        System.out.println(r.getAutorisedEmails()[1]);
//        rideDAO.delete(1);


//        TimedPosition[] timedPositions = {new TimedPosition(1, 2, 3, "10h30"),
//                new TimedPosition(4, 5, 6, "10h32")};
//
        StatisticsDAO statisticsDAO = new StatisticsDAO();
//        statisticsDAO.create(new Statistics(0, "jd@gmail.com", null));
        Object[] o = {0, "jd@gmail.com"};
        Statistics s = statisticsDAO.find(o);
        System.out.println(s.getTimedPositions());


//        MetierDAO metierDAO = new MetierDAO();
//        Metier2[] metier2s = {new Metier2(54), new Metier2(55)};
//        metierDAO.create(new Metier(0, metier2s));

//        Metier m = metierDAO.find(0);

    }
}
