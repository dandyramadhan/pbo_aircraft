package pbo_aircraft;
import java.sql.*;

public class Koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String URL = "jdbc:mysql://localhost:3306/aircraft";
                String USER = "root";
                String PASS = "";
                
                koneksi = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Koneksi ke database BERHASIL");
            } catch (SQLException e) {
                System.out.println("Koneksi GAGAL");
            }
        }
        return koneksi;
    }
}