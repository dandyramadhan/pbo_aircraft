package pbo_aircraft.models;

import java.time.LocalDateTime;

public class Invoice {
    private String customerId;
    private int pesawatId;
    private String originIcao;
    private String destIcao;
    private int jarak;
    private double estimasiWaktu;
    private int jumlahPenumpang;
    private double totalBiaya;
    private LocalDateTime jadwalBerangkat;

    public Invoice(
            String customerId,
            int pesawatId,
            String originIcao,
            String destIcao,
            int jarak,
            double estimasiWaktu,
            int jumlahPenumpang,
            double totalBiaya,
            LocalDateTime jadwalBerangkat
    ) {
        this.customerId = customerId;
        this.pesawatId = pesawatId;
        this.originIcao = originIcao;
        this.destIcao = destIcao;
        this.jarak = jarak;
        this.estimasiWaktu = estimasiWaktu;
        this.jumlahPenumpang = jumlahPenumpang;
        this.totalBiaya = totalBiaya;
        this.jadwalBerangkat = jadwalBerangkat;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getPesawatId() {
        return pesawatId;
    }

    public String getOriginIcao() {
        return originIcao;
    }

    public String getDestIcao() {
        return destIcao;
    }

    public int getJarak() {
        return jarak;
    }

    public double getEstimasiWaktu() {
        return estimasiWaktu;
    }

    public int getJumlahPenumpang() {
        return jumlahPenumpang;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public LocalDateTime getJadwalBerangkat() {
        return jadwalBerangkat;
    }
}
