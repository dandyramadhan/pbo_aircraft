package pbo_aircraft;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import pbo_aircraft.dao.*;
import pbo_aircraft.models.*;

public class Menu {

    private static Scanner sc = new Scanner(System.in);
    private static Customer customerAktif;

    public static void run() {
        int pilih;
        do {
            System.out.println("\n=== SISTEM SEWA JET PRIBADI ===");
            System.out.println("1. Login Customer");
            System.out.println("2. Lihat Daftar Pesawat");
            System.out.println("3. Booking Penerbangan");
            System.out.println("4. Lihat Semua Invoice");
            System.out.println("0. Exit");
            System.out.print("Pilih menu: ");
            pilih = sc.nextInt();
            sc.nextLine();
            cls();

            switch (pilih) {
                case 1 -> login();
                case 2 -> PesawatDAO.tampilkanSemua();
                case 3 -> booking();
                case 4 -> InvoiceDAO.tampilkanSemua();
                case 0 -> System.out.println("Program selesai");
                default -> System.out.println("Menu tidak valid");
            }
        } while (pilih != 0);
    }

    // ======================
    // LOGIN
    // ======================
    private static void login() {
        System.out.print("Masukkan Passport No: ");
        String passport = sc.nextLine();

        Customer c = CustomerDAO.findByPassport(passport);

        // JIKA SUDAH TERDAFTAR
        if (c != null) {
            customerAktif = c;
            System.out.println("Login berhasil (Customer: " + c.getFullName()+")");
            return;
        }

        // JIKA BELUM TERDAFTAR
        System.out.println("Customer belum terdaftar.");
        System.out.print("Apakah ingin melakukan registrasi? (y/n): ");
        String pilih = sc.nextLine();

        if (pilih.equalsIgnoreCase("y")) {
            System.out.print("Masukkan Nama Lengkap: ");
            String nama = sc.nextLine();

            Customer baru = CustomerDAO.insert(nama, passport);
            if (baru != null) {
                customerAktif = baru;
                System.out.println("Login berhasil. Welcome " + baru.getFullName());
            }
        } else {
            System.out.println("Login dibatalkan.");
        }
    }


    // ======================
    // BOOKING
    // ======================
    private static void booking() {

        if (customerAktif == null) {
            System.out.println("Silakan input Customer terlebih dahulu");
            return;
        }

        // origin selalu JMOK
        String originIcao = "JMOK";

        System.out.print("Masukkan kota tujuan: ");
        String kotaTujuan = sc.nextLine();

        Airport tujuan = AirportDAO.findByCity(kotaTujuan);
        if (tujuan == null) {
            System.out.println("Kota tidak ditemukan");
            return;
        }

        System.out.print("Jumlah penumpang: ");
        int pax = sc.nextInt();
        sc.nextLine();

        // tampilkan pesawat sesuai kapasitas
        List<Pesawat> listPesawat = PesawatDAO.findByKapasitas(pax);
        if (listPesawat.isEmpty()) {
            System.out.println("Tidak ada pesawat yang sesuai");
            return;
        }

        System.out.println("\n=====================================================================");
        System.out.println("REKOMENDASI PESAWAT UNTUK PENERBANGAN ANDA");
        System.out.println("Tujuan      : " + tujuan.getCity() + " (" + tujuan.getIcao() + ")");
        System.out.println("Durasi      : " + tujuan.getEstimasiWaktu() + " jam");
        System.out.println("Penumpang   : " + pax + " orang");
        System.out.println("=====================================================================");

        System.out.printf("%-3s | %-12s | %-28s | %-3s | %-12s%n",
"ID", "KATEGORI", "NAMA PESAWAT", "PAX", "HARGA/JAM");
        System.out.println("=====================================================================");

        for (Pesawat p : listPesawat) {
            System.out.printf(
                "%-3d | %-12s | %-28s | %-3d | Rp %,12.0f%n",
                p.getId(),
                p.getKategori(),
                p.getNama(),
                p.getKapasitas(),
                p.getHargaSewa()
            );
        }

        System.out.println("=====================================================================");
        System.out.println("* Pilih ID pesawat untuk melanjutkan booking");


        System.out.print("Pilih ID pesawat: ");
        int pesawatId = sc.nextInt();
        sc.nextLine();

        Pesawat pesawat = PesawatDAO.findById(pesawatId);
        if (pesawat == null) {
            System.out.println("Pesawat tidak valid");
            return;
        }

        double totalBiaya = pesawat.hitungBiaya(tujuan.getEstimasiWaktu());

        System.out.print("Jadwal berangkat (yyyy-MM-dd HH:mm): ");
        String waktu = sc.nextLine();
        LocalDateTime jadwal = LocalDateTime.parse(
                waktu, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Invoice invoice = new Invoice(
                customerAktif.getId(),
                pesawat.getId(),
                originIcao,
                tujuan.getIcao(),
                tujuan.getJarak(),
                tujuan.getEstimasiWaktu(),
                pax,
                totalBiaya,
                jadwal
        );

        InvoiceDAO.simpan(invoice);
    }

    private static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Terjadi kesalahan saat membersihkan layar.");
        }
    }

}
