package pbo_aircraft;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hasilnya: ");
        Connection conn = Koneksi.getKoneksi();
    }
}
