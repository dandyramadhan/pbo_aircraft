package pbo_aircraft;

public class HeavyJet extends Pesawat {

    public HeavyJet(int id, String namaPesawat, int kapasitas, double hargaSewa) {
        super(id, namaPesawat, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return hargaSewa * jam * 1.2;
    }
}
