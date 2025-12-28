package pbo_aircraft.models;

public class Pesawat {
    protected int id;
    protected String kategori;
    protected String nama;
    protected int kapasitas;
    protected double hargaSewa;

    public Pesawat(int id, String kategori, String nama, int kapasitas, double hargaSewa) {
        this.id = id;
        this.kategori = kategori;
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.hargaSewa = hargaSewa;
    }

    public String getKategori() {
        return kategori;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    // polymorphism siap dipakai
    public double hitungBiaya(double jam) {
        return hargaSewa * jam;
    }
}
