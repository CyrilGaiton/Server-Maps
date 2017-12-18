package fr.ancyen.maps;

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

                if (s.equals("getEvenement")) {
                    Integer idEvenement = (Integer) ois.readObject();
                } else if (s.equals("coucou cyril")) {
                    System.out.println("JKVJHVJYFCHGVKHGCKHCYTCFHGCFKG");

                } else if (s.equals("close")) {
                    running= false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();}
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        stop();
    }

    public void stop() {


        try {
            System.out.println("Connexion fermée: " + client.getLocalAddress());
            client.close();
        } catch (IOException e) {
            System.out.println("Exception a la fermeture d'une connexion"+e);
        }

    }
}
