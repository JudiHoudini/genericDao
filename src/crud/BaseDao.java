/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author °°JUDICAEL°°
 */
public class BaseDao {
    private Connexion connexion;

    public BaseDao(Connexion connexion) {
        this.connexion = connexion;
    }

    public Connexion getConnexion() {
        return connexion;
    }

    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    protected Connection getConnection() throws SQLException {
        if (connexion == null) {
            throw new IllegalStateException("Connexion not initialized.");
        }
        return connexion.connectMysql();
    }
}
