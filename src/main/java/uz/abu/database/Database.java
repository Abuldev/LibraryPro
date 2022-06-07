package uz.abu.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import uz.abu.template.Book;
import uz.abu.template.Library;
import uz.abu.template.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database database = new Database();

    public static Database getDatabase() {
        return database;
    }

    public List<User> users = new ArrayList<>();
    public User currentUser;
    public void getUsers() {
        try {
            File file = new File("src/main/resources/Users.json");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            Gson gson = new Gson();
            Type type = new TypeToken< ArrayList <User> >(){}.getType();
            users = gson.fromJson(reader, type);
            reader.close();

            if(users == null)
                users = new ArrayList<>();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setUsers(){
        try {
            File file = new File("src/main/resources/Users.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            writer.write(gson.toJson(users));
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Library> libraries = new ArrayList<>();
    public void getLibraries() {
        try {
            File file = new File("src/main/resources/Libraries.json");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            Gson gson = new Gson();
            Type type = new TypeToken< ArrayList <Library> >(){}.getType();
            libraries = gson.fromJson(reader, type);
            reader.close();

            if(libraries == null)
                libraries = new ArrayList<>();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setLibraries(){
        try {
            File file = new File("src/main/resources/Libraries.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            writer.write(gson.toJson(libraries));
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> books = new ArrayList<>();
    public void getBooks() {
        for (Library library : libraries) {
            books.addAll(library.getBooks());
        }
    }
    public void setBooks(){
        try {
            File file = new File("src/main/resources/Books.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            writer.write(gson.toJson(books));
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Library getLibraryFromUser(User user){
        for (Library library : libraries) {
            if(library.getManager().getId().equals(user.getId()))
                return library;
        }
        return null;
    }

//    public void getUsers0() {
//        File file = new File("src/main/resources/Users.csv");
//        try (PrintWriter writer = new PrintWriter(file)){
//            String info = null;
//            for (User user : users) {
//                info += user.toString();
//            }
//            writer.write(info);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
