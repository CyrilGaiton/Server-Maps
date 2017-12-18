package fr.ancyen.maps.PackageDAO;

import java.sql.Connection;

import fr.ancyen.maps.PackageDAO.PackageConnectionDatabase.ConnectionDatabase;


public abstract class DAO<T>  {

    public Connection connect = ConnectionDatabase.getInstance();


    // Permet de creer un objet dans la base de donnees.
    
    public abstract T create (T obj);

    // Permet de trouver un objet
    
    public abstract T find (Object... ids);

    // Permet de modifier un objet existant dans la base de donnees.

//    public abstract T  update (T obj);

    // Permet de supprimer un objet dans la base de donnees.

    public abstract void delete(Object... obj);


}
