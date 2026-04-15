public class Candidate extends Person {

    public Candidate(String id, String name) {
        super(id, name);
    }

    public String getDetails() {
        return "Candidate ID: " + id + ", Name: " + name;
    }
}