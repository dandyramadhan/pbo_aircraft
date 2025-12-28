package pbo_aircraft;

public abstract class Pesawat {
    protected int id;
    protected String namaPesawat;
    protected int kapasitas;
    protected double hargaSewa;

    public Pesawat(int id, String namaPesawat, int kapasitas, double hargaSewa) {
        this.id = id;
        this.namaPesawat = namaPesawat;
        this.kapasitas = kapasitas;
        this.hargaSewa = hargaSewa;
    }

    public int getId() {
        return id;
    }

    public String getNamaPesawat() {
        return namaPesawat;
    }

    // POLYMORPHISM
    public abstract double hitungBiaya(double jam);
}
