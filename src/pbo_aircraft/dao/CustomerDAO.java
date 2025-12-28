package pbo_aircraft.dao;

import java.sql.*;
import pbo_aircraft.Koneksi;
import pbo_aircraft.models.Customer;

public class CustomerDAO {

    public static Customer findByPassport(String passport) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM customers WHERE passport_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, passport);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getString("id"),
                    rs.getString("full_name"),
                    rs.getString("passport_no")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error CustomerDAO: " + e.getMessage());
        }
        return null;
    }

    public static Customer insert(String fullName, String passportNo) {
    try {
        Connection conn = Koneksi.getConnection();

        // generate ID otomatis (C011, C012, dst)
        String sqlId = "SELECT COUNT(*) FROM customers";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sqlId);
        rs.next();
        int nomor = rs.getInt(1) + 1;

        String id = String.format("C%03d", nomor);

        String sql = "INSERT INTO customers (id, full_name, passport_no) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, fullName);
        ps.setString(3, passportNo);
        ps.executeUpdate();

        System.out.println("Registrasi berhasil. ID Customer: " + id);
        return new Customer(id, fullName, passportNo);

    } catch (SQLException e) {
        System.out.println("Error registrasi customer: " + e.getMessage());
    }
    return null;
}

}
