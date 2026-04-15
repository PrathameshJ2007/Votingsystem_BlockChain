public class Voter extends Person{
    private String password;
    private boolean voted;
    public Voter(String id, String name, String password) {
        super(id, name);
        this.password = password;
        this.voted = false;
    }

    public boolean login(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }
    public boolean hasVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public String getPassword() {
        return password;
    }
}