package uz.abu.template;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String id = UUID.randomUUID().toString();
    private String username;
    private String password;
    private UserRole role;
    private boolean active;
    private boolean haveLibrary;
    private ArrayList<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return  id + ',' +
                username + ',' +
                password + ',' +
                role + ',' +
                active + ',' +
                books + '\n';
    }
}
