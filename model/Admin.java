package model;

public class Admin extends Person {

    private String password;

    public Admin(String id, String name, String password) {
        super(id, name);
        this.password = password;
    }

    public boolean login(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }
}