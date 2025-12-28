package pbo_aircraft.models;

public class LightJet extends Pesawat {

    public LightJet(int id, String nama, int kapasitas, double hargaSewa) {
        super(id, "Light Jet", nama, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return super.hitungBiaya(jam); // bisa ditambah diskon nanti
    }
}
