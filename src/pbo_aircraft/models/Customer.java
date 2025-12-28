package pbo_aircraft.models;

public class Customer {
    private String id;
    private String fullName;
    private String passportNo;

    public Customer(String id, String fullName, String passportNo) {
        this.id = id;
        this.fullName = fullName;
        this.passportNo = passportNo;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
