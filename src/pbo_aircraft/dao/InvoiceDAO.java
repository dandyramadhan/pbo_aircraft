package pbo_aircraft.dao;

import java.sql.*;
import pbo_aircraft.Koneksi;
import pbo_aircraft.models.Invoice;

public class InvoiceDAO {

    // simpan invoice (booking)
    public static void simpan(Invoice i) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = """
                INSERT INTO invoices
                (customer_id, pesawat_id, origin_icao, dest_icao,
                 jarak, estimasi_waktu, jumlah_penumpang,
                 total_biaya, jadwal_berangkat)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, i.getCustomerId());
            ps.setInt(2, i.getPesawatId());
            ps.setString(3, i.getOriginIcao());
            ps.setString(4, i.getDestIcao());
            ps.setInt(5, i.getJarak());
            ps.setDouble(6, i.getEstimasiWaktu());
            ps.setInt(7, i.getJumlahPenumpang());
            ps.setDouble(8, i.getTotalBiaya());
            ps.setTimestamp(9, Timestamp.valueOf(i.getJadwalBerangkat()));

            ps.executeUpdate();
            System.out.println("Invoice berhasil disimpan");
        } catch (SQLException e) {
            System.out.println("Error InvoiceDAO: " + e.getMessage());
        }
    }

    // tampilkan semua invoice
    public static void tampilkanSemua() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql =
                "SELECT i.id, c.full_name, p.nama_pesawat, " +
                "i.origin_icao, i.dest_icao, " +
                "i.jumlah_penumpang, i.total_biaya, i.jadwal_berangkat " +
                "FROM invoices i " +
                "JOIN customers c ON i.customer_id = c.id " +
                "JOIN pesawat p ON i.pesawat_id = p.id";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("==============================================================================================================");
            System.out.printf("%-3s | %-18s | %-27s | %-11s | %-3s | %-15s | %-16s%n",
                    "ID", "CUSTOMER", "PESAWAT", "RUTE", "PAX", "TOTAL BIAYA", "JADWAL");
            System.out.println("==============================================================================================================");

            while (rs.next()) {
                System.out.printf(
                    "%-3d | %-18s | %-27s | %-11s | %-3d | Rp %,13.0f | %-16s%n",
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("nama_pesawat"),
                    rs.getString("origin_icao") + " - " + rs.getString("dest_icao"),
                    rs.getInt("jumlah_penumpang"),
                    rs.getDouble("total_biaya"),
                    rs.getTimestamp("jadwal_berangkat").toLocalDateTime()
                            .toString().replace("T", " ")
                );
            }

            System.out.println("==============================================================================================================");

        } catch (SQLException e) {
            System.out.println("Error InvoiceDAO: " + e.getMessage());
        }
}

}
