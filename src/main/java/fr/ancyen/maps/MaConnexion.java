package fr.ancyen.maps;

import fr.masterdapm.ancyen.model.Statistics;

import java.io.*;
import java.net.*;
import java.sql.SQLException;


public class MaConnexion implements Runnable {

    private Socket client; //Liaison avec le client
    private Facade facade;

    public MaConnexion(Socket client, Facade facade) {

        this.client = client;
        this.facade = facade;

        new Thread(this).start();
    }


    public void run() {

        boolean running = true;
        try {

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            while (running) {


                // on switch en fonction du premier string
                String s = (String) ois.readObject();

                if (s.equals("checkUser")) {
                    String email = (String) ois.readObject();
                    String password = (String) ois.readObject();
                    oos.writeObject(1);

                } else if (s.equals("addUser")) {
                    facade.addUser(ois);

                } else if (s.equals("addRide")) {
                    facade.addRide(ois);

                } else if (s.equals("addStatistics")) {
                    facade.addStatistics(ois);

                } else if (s.equals("getStatisticsWithEmail")) {
                    facade.gatStatisticsWithEmail(ois, oos);

                } else if (s.equals("getRidesWithEmail")) {
                    facade.getRidesWithEmail(ois, oos);

                } else if (s.equals("test serial")) {
                    Statistics statistics = (Statistics) ois.readObject();
                    System.out.println(statistics.getTimedPositions()[1].getTime());

                } else if (s.equals("close")) {
                    running= false;
                }
            }

        } catch (IOException e) {
            running = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();}

        stop();
    }

    public void stop() {


        try {
            System.out.println("Connexion fermée: " + client.getRemoteSocketAddress());
            client.close();
        } catch (IOException e) {
            System.out.println("Exception a la fermeture d'une connexion"+e);
        }

    }
}
