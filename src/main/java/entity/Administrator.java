package entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Administrator extends User {
    private String email;
    public Administrator(String username, String password) {
        super(username, password);
    }
    public Administrator(String username, String password, String email){
        this.email = email;
    }

}
