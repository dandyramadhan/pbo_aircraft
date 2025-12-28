package pbo_aircraft.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pbo_aircraft.Koneksi;
import pbo_aircraft.models.HeavyJet;
import pbo_aircraft.models.LightJet;
import pbo_aircraft.models.MidSizeJet;
import pbo_aircraft.models.Pesawat;

public class PesawatDAO {

    // tampilkan semua (menu 2)
    public static void tampilkanSemua() {
        try {
            Connection conn = Koneksi.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pesawat");

            System.out.println("==============================================================================");
            System.out.printf("%-3s | %-12s | %-28s | %-3s | %-12s%n",
                    "ID", "KATEGORI", "NAMA PESAWAT", "PAX", "HARGA/JAM");
            System.out.println("==============================================================================");

            while (rs.next()) {
                System.out.printf(
                    "%-3d | %-12s | %-28s | %-3d | Rp %,12.0f%n",
                    rs.getInt("id"),
                    rs.getString("kategori"),
                    rs.getString("nama_pesawat"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa")
                );
            }

            System.out.println("==============================================================================");

        } catch (SQLException e) {
            System.out.println("Error PesawatDAO: " + e.getMessage());
        }
}


    // filter berdasarkan kapasitas
    public static List<Pesawat> findByKapasitas(int pax) {
        List<Pesawat> list = new ArrayList<>();
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM pesawat WHERE kapasitas >= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pax);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pesawat(
                    rs.getInt("id"),
                    rs.getString("kategori"),
                    rs.getString("nama_pesawat"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error PesawatDAO: " + e.getMessage());
        }
        return list;
    }

    // cari pesawat by ID
public static Pesawat findById(int id) {
    try {
        Connection conn = Koneksi.getConnection();
        String sql = "SELECT * FROM pesawat WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            String kategori = rs.getString("kategori");
            String nama = rs.getString("nama_pesawat");
            int kapasitas = rs.getInt("kapasitas");
            double harga = rs.getDouble("harga_sewa");

            if (kategori.equalsIgnoreCase("Light Jet")) {
                return new LightJet(id, nama, kapasitas, harga);
            } else if (kategori.equalsIgnoreCase("Mid-size Jet")) {
                return new MidSizeJet(id, nama, kapasitas, harga);
            } else {
                return new HeavyJet(id, nama, kapasitas, harga);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error PesawatDAO: " + e.getMessage());
    }
    return null;
}

}
