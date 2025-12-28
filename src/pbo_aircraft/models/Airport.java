package pbo_aircraft.models;

public class Airport {
    private String icao;
    private String city;
    private int jarak;
    private double estimasiWaktu;

    public Airport(String icao, String city, int jarak, double estimasiWaktu) {
        this.icao = icao;
        this.city = city;
        this.jarak = jarak;
        this.estimasiWaktu = estimasiWaktu;
    }

    public String getIcao() {
        return icao;
    }

    public String getCity() {
        return city;
    }

    public int getJarak() {
        return jarak;
    }

    public double getEstimasiWaktu() {
        return estimasiWaktu;
    }
}
