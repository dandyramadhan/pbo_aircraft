package pbo_aircraft.dao;

import java.sql.*;
import pbo_aircraft.Koneksi;
import pbo_aircraft.models.Airport;

public class AirportDAO {

    public static Airport findByCity(String city) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM airport WHERE city = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, city);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Airport(
                    rs.getString("icao"),
                    rs.getString("city"),
                    rs.getInt("distance"),
                    rs.getDouble("estimasi_waktu"));
            }
        } catch (SQLException e) {
            System.out.println("Error AirportDAO: " + e.getMessage());
        }
        return null;
    }
}
