package uz.abu.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String author;
    private int amount;
}
