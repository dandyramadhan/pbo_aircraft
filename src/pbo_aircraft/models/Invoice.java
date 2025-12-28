package pbo_aircraft.models;

public class Invoice {
    private int id;
    private String customerId;
    private int pesawatId;
    private double totalCost;

    public Invoice(String customerId, int pesawatId, double totalCost) {
        this.customerId = customerId;
        this.pesawatId = pesawatId;
        this.totalCost = totalCost;
    }

    public String getCustomerId() { return customerId; }
    public int getPesawatId() { return pesawatId; }
    public double getTotalCost() { return totalCost; }
}
