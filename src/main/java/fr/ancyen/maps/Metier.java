package fr.ancyen.maps;

import java.io.Serializable;

public class Metier implements Serializable{
    private int id;
    private Metier2[] metier2;

    public Metier(int id, Metier2[] metier2) {
        this.id = id;
        this.metier2 = metier2;
    }

    public int getId() {
        return id;
    }

    public Metier2[] getMetier2() {
        return metier2;
    }
}
