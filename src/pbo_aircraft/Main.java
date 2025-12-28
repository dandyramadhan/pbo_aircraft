package pbo_aircraft;

import java.sql.*;
import java.util.Scanner;
import pbo_aircraft.models.Customer;

public class Main {

    static Scanner sc = new Scanner(System.in);

public static void main(String[] args) {

    System.out.println("PROGRAM SEWA JET PRIBADI");

    Connection conn = Koneksi.getConnection();
    if (conn == null) return;

    // LOGIN
    System.out.print("\nMasukkan Passport: ");
    String passport = sc.nextLine();
    Customer customer = loginOrRegister(conn, passport);

    if (customer == null) return;

    System.out.println("Selamat datang, " + customer.getFullName());

    // ===== MENU UTAMA =====
    int menu;
    do {
        System.out.println("\n====== MENU UTAMA ======");
        System.out.println("1. Lihat Data Pesawat");
        System.out.println("2. Booking Jet");
        System.out.println("3. Lihat Invoice");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");

        menu = sc.nextInt();
        sc.nextLine(); // WAJIB

        switch (menu) {
            case 1:
                lihatPesawat(conn);
                break;
            case 2:
                bookingJet(conn, customer);
                break;
            case 3:
                System.out.println("\n[MENU] Lihat Invoice");
                break;
            case 0:
                System.out.println("Terima kasih.");
                break;
            default:
                System.out.println("Menu tidak tersedia.");
        }
    } while (menu != 0);
}


        static Customer loginOrRegister(Connection conn, String passport) {
        try {
            // CEK CUSTOMER
            String cek = "SELECT * FROM customers WHERE passport_no = ?";
            PreparedStatement ps = conn.prepareStatement(cek);
            ps.setString(1, passport);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // SUDAH ADA → LOGIN
                System.out.println("Customer ditemukan.");
                return new Customer(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("passport_no")
                );
            } else {
                // BELUM ADA → REGISTER
                System.out.print("Nama Lengkap: ");
                String nama = sc.nextLine();

                String id = "C" + (int)(Math.random() * 1000);

                String insert = "INSERT INTO customers VALUES (?,?,?)";
                ps = conn.prepareStatement(insert);
                ps.setString(1, id);
                ps.setString(2, nama);
                ps.setString(3, passport);
                ps.executeUpdate();

                System.out.println("Registrasi berhasil.");

                return new Customer(id, nama, passport);
            }

        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return null;
    }

    static void lihatPesawat(Connection conn) {
    try {
        String sql = "SELECT * FROM pesawat";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("\n=== DAFTAR PESAWAT ===");
        System.out.printf("%-3s %-25s %-15s %-10s %-15s\n",
                "ID", "Nama Pesawat", "Kategori", "Kapasitas", "Harga/Jam");

        while (rs.next()) {
            System.out.printf("%-3d %-25s %-15s %-10d Rp %-15.0f\n",
                    rs.getInt("id"),
                    rs.getString("nama_pesawat"),
                    rs.getString("kategori"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa"));
        }

    } catch (Exception e) {
        System.out.println("Gagal mengambil data pesawat: " + e.getMessage());
    }
}

static void bookingJet(Connection conn, Customer customer) {
    try {
        System.out.print("Jumlah penumpang: ");
        int pax = sc.nextInt();
        sc.nextLine();

        System.out.print("Durasi terbang (jam): ");
        double jam = sc.nextDouble();
        sc.nextLine();

        String sql = """
            SELECT * FROM pesawat
            WHERE kapasitas >= ?
            ORDER BY kapasitas
            LIMIT 1
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pax);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            System.out.println("Tidak ada pesawat yang sesuai.");
            return;
        }

        // ================= OOP =================
        Pesawat pesawat;
        String kategori = rs.getString("kategori");

        if (kategori.equalsIgnoreCase("Light Jet")) {
            pesawat = new LightJet(
                    rs.getInt("id"),
                    rs.getString("nama_pesawat"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa")
            );
        } else if (kategori.equalsIgnoreCase("Mid-size Jet")) {
            pesawat = new MidJet(
                    rs.getInt("id"),
                    rs.getString("nama_pesawat"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa")
            );
        } else {
            pesawat = new HeavyJet(
                    rs.getInt("id"),
                    rs.getString("nama_pesawat"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("harga_sewa")
            );
        }

        // POLYMORPHISM
        double total = pesawat.hitungBiaya(jam);

        //sementaraa
        String originIcao = "WIII"; // dummy (Soekarno-Hatta)
        String destIcao   = "WAAA"; // dummy (Hasanuddin)
    
        // SIMPAN INVOICE
        String insert = """
            INSERT INTO invoices (id_customers, id_pesawat, origin_icao, dest_icao, total_cost)
            VALUES (?, ?, ?, ?, ?)
        """;

            ps = conn.prepareStatement(insert);
            ps.setString(1, customer.getId());
            ps.setInt(2, pesawat.getId());
            ps.setString(3, originIcao);
            ps.setString(4, destIcao);
            ps.setDouble(5, total);
            ps.executeUpdate();


        // OUTPUT
        System.out.println("\n=== BOOKING BERHASIL ===");
        System.out.println("Pesawat   : " + pesawat.getNamaPesawat());
        System.out.println("Kategori  : " + kategori);
        System.out.println("Durasi    : " + jam + " jam");
        System.out.println("Total     : Rp " + total);

    } catch (Exception e) {
        System.out.println("Error booking: " + e.getMessage());
    }
}



}