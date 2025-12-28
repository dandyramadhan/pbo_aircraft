package pbo_aircraft.models;

public class MidSizeJet extends Pesawat {

    public MidSizeJet(int id, String nama, int kapasitas, double hargaSewa) {
        super(id, "Mid-size Jet", nama, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return super.hitungBiaya(jam);
    }
}
