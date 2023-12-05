package entity;

public class Administrator extends User {
    public Administrator(String username, String password) {
        super(username, password);
    }
    public Administrator(String username, String password,String email) {
        super(username, password,email);
    }

}
