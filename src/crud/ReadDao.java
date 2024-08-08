/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import annotation.TableAnnotation;
import annotation.ColumnAnnotation;

/**
 *
 * @author °°JUDICAEL°°
 */
public class ReadDao<T> extends BaseDao {
    private Class<T> clazz;

    public ReadDao(Connexion connexion, Class<T> clazz) {
        super(connexion);
        this.clazz = clazz;
    }

    public List<T> findAll() throws SQLException {
        List<T> results = new ArrayList<>();
        String tableName = getTableNameFromClass(clazz);

        String sql = "SELECT * FROM " + tableName;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                T instance = createInstance();
                populateInstance(instance, rs);
                results.add(instance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while reading data from database.", e);
        }

        return results;
    }

    private String getTableNameFromClass(Class<T> clazz) {
        TableAnnotation tableAnnotation = clazz.getAnnotation(TableAnnotation.class);
        if (tableAnnotation == null) {
            System.out.println("Classe " + clazz.getName() + " n'est pas annotée avec @TableAnnotation");
            throw new RuntimeException("Classe " + clazz.getName() + " n'est pas annotée avec @TableAnnotation");
        }
        System.out.println("Nom de la table: " + tableAnnotation.nom());
        return tableAnnotation.nom();
    }


    private T createInstance() throws SQLException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new SQLException("Error creating instance of class " + clazz.getName(), e);
        }
    }

    private void populateInstance(T instance, ResultSet rs) throws SQLException {
        for (Field field : clazz.getDeclaredFields()) {
            ColumnAnnotation columnAnnotation = field.getAnnotation(ColumnAnnotation.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.nom();
                field.setAccessible(true);
                try {
                    Object value = rs.getObject(columnName);
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error setting field value in instance", e);
                }
            }
        }
    }
}