package entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String username;
    private String email;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
