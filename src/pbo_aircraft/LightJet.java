package pbo_aircraft;

public class LightJet extends Pesawat {

    public LightJet(int id, String namaPesawat, int kapasitas, double hargaSewa) {
        super(id, namaPesawat, kapasitas, hargaSewa);
    }

    @Override
    public double hitungBiaya(double jam) {
        return hargaSewa * jam;
    }
}
