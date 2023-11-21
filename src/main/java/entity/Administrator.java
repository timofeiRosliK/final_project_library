package entity;

import lombok.*;

@Getter
@Setter
@ToString
public class Administrator extends User {
    public Administrator(String username, String password) {
        super(username, password);
    }

}
