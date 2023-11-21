package entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private int id;
    private String name;
    private String surname;
}
