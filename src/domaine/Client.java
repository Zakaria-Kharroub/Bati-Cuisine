package domaine;

public class Client {

    private int id;
    private String name;
    private String phone;
    private String address;
    private boolean isProfessional;

    public Client(String name, String phone, String address, boolean isProfessional) {
//        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.isProfessional = isProfessional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsProfessional() {
        return isProfessional;
    }

    public void setIsProfessional(boolean professional) {
        isProfessional = professional;
    }
}
