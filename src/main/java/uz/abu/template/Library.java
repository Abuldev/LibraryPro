package uz.abu.template;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Library {
    private User manager;
    private String name;
    private String id = UUID.randomUUID().toString();
    private ArrayList<Book> books = new ArrayList<>();
}
