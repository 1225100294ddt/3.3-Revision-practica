package utng.gitd232.ddt;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    public static Connection conectar() {
        try {
           
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/inventario", "postgres", "linux");
        } catch (Exception e) { 
            e.printStackTrace(); 
            return null; 
        }
    }
}
