package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    private int id;
    private String title;
    private Author author;
    private Genre genre;
    private BookType bookType;
}