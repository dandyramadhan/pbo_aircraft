package pbo_aircraft;

public class MidJet extends Pesawat {

    public MidJet(int id, String namaPesawat, int kapasitas, double hargaSewa) {
        super(id, namaPesawat, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return hargaSewa * jam * 1.1; // contoh beda
    }
}
