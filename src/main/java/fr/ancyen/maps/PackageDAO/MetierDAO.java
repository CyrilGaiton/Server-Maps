package fr.ancyen.maps.PackageDAO;

import fr.ancyen.maps.Metier;
import fr.ancyen.maps.Metier2;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetierDAO extends DAO<Metier> {

    public Metier create(Metier metier) {
        try {
            PreparedStatement prepare = this.connect
                    .prepareStatement(
                            "INSERT INTO METIER VALUES(?, ?)"
                    );
            prepare.setInt(1, metier.getId());
            prepare.setBytes(2, toByteArray(metier.getMetier2()));

            prepare.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return metier;
    }


    public Metier find(Object... ids) {
        int id = (Integer) ids[0];
        Metier retour = null;
        try {
            ResultSet result = this.connect
                    .createStatement()
                    .executeQuery(
                            "SELECT * FROM METIER WHERE id = " + id
                    );
            if(result.first())
                retour = new Metier(
                        id,
                        toMetier2(result.getBytes("metier2"))
                );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retour;

    }

    @Override
    public void delete(Object... obj) {

    }

    public byte[] toByteArray(Object[] o) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] b = null;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bout);
            for (int i=0; i<o.length; i++){
                oos.writeObject(o[i]);
            }
            b = bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Metier2[] toMetier2(byte[] b) {
        ByteArrayInputStream binp = new ByteArrayInputStream(b);
        List<Metier2> metiers2 = new ArrayList<Metier2>();
        try {
            ObjectInputStream ois = new ObjectInputStream(binp);
            boolean boucle = true;
            while (boucle){
                try {
                    System.out.println("available");
                    metiers2.add((Metier2) ois.readObject());
                }
                catch (EOFException e){
                    System.out.println("fin");
                    boucle = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Metier2[] m = new Metier2[metiers2.size()];
        for (int i=0; i<metiers2.size(); i++){
            m[i] = metiers2.get(i);
        }
        return m;
    }
}
