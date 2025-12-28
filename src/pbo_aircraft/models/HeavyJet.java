package pbo_aircraft.models;

public class HeavyJet extends Pesawat {

    public HeavyJet(int id, String nama, int kapasitas, double hargaSewa) {
        super(id, "Heavy Jet", nama, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return super.hitungBiaya(jam);
    }
}
