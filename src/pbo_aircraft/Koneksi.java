package pbo_aircraft;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/aircraft",
                        "root",
                        ""
                );
                System.out.println("Koneksi database berhasil");
            }
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}
