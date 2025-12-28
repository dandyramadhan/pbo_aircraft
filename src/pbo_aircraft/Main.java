package pbo_aircraft;

public class Main {
    public static void main(String[] args) {
        // cek koneksi sekali saja
        Koneksi.getConnection();

        // jalankan menu
        Menu.run();
    }
}
