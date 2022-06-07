package uz.abu.service.adminService;

import uz.abu.database.Database;
import uz.abu.exception.InvalidUsername;
import uz.abu.template.Book;
import uz.abu.template.Library;
import uz.abu.utils.Util;

public class BooksMenu {
    private Util util = new Util();
    private Database db = Database.getDatabase();

    public void mainMenu(){
        while (true){
            util.clear();
            System.out.println("Book menu");
            System.out.println("0. Exit");
            System.out.println("1. Add book");
            System.out.println("2. Show book");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.print  ("  ==> ");
            switch (util.numScan.nextInt()){
                case 0:
                    return;
                case 1:
                    addBook();
                    break;
                case 2:
                    util.clear();
                    showBooks();
                    System.out.print(" ==> ");
                    util.numScan.nextInt();
                    break;
                case 3:
                    editBook();
                    break;
                case 4:
                    deleteManager();
                    break;
            }
        }
    }

    private void addBook() {
        util.clear();
        System.out.println("Add Book");
        Book book = new Book();

        util.yellow("Available libraries:");
        System.out.println();
        Library library1 = null;
        for (Library library : db.libraries) {
            System.out.println(" * " + library.getName());
        }
        util.blue("Enter library name: ");
        String libraryName = util.strScan.nextLine();

        for (Library library : db.libraries) {
            if(library.getName().equals(libraryName)){
                library1 = library;
            }
        }

        if(library1 == null){
            try {
                throw new InvalidUsername();
            } catch (InvalidUsername e) {
                e.printStackTrace();
                util.pause();
                return;
            }
        }

        System.out.print("Book name: ");
        book.setName(util.strScan.nextLine());
        System.out.print("Author: ");
        book.setAuthor(util.strScan.nextLine());
        System.out.print("Amount: ");
        book.setAmount(util.numScan.nextInt());

        library1.getBooks().add(book);

        db.books.add(book);
        db.setBooks();

        db.setLibraries();
        util.green("Successfully added");
        util.pause();
    }

    private void showBooks() {
        util.yellow("Books");
        System.out.println();
        util.blue(util.left("   Book name", 30) +
                util.left("Author", 30) +
                util.left("Amount", 20) +
                "Library");
        System.out.println();
        int num = 1;
        for (Library library : db.libraries) {
            for (Book book : library.getBooks()) {
                System.out.println(util.left(num++ + ". " + book.getName(), 30) +
                        util.left(book.getAuthor(), 30) +
                        util.left(String.valueOf(book.getAmount()), 20) +
                        library.getName());
            }
        }
    }

    private void editBook() {
        util.clear();
        showBooks();
        util.red("Enter book name: ");
        String name = util.strScan.nextLine();
        for (Book book: db.books) {
            if(book.getName().equals(name)){
                util.blue("Enter new name: ");
                book.setName(util.strScan.nextLine());
                util.blue("Enter new author: ");
                book.setAuthor(util.strScan.nextLine());
                util.blue("Enter new amount: ");
                book.setAmount(util.numScan.nextInt());

                db.setLibraries();
                db.setBooks();
                util.green("Successfully edited");
                util.pause();
                return;
            }
        }
        try {
            throw new InvalidUsername();
        } catch (InvalidUsername e) {
            e.printStackTrace();
            util.pause();
        }
    }

    private void deleteManager() {
        util.clear();
        showBooks();
        util.red("Enter book name: ");
        String name = util.strScan.nextLine();
        for (Book book: db.books) {
            if(book.getName().equals(name)){

                for (Library library : db.libraries) {
                    library.getBooks().remove(book);
                }

                db.setLibraries();

                db.books.remove(book);

                db.setBooks();
                util.green("Successfully deleted");
                util.pause();
                return;
            }
        }
        try {
            throw new InvalidUsername();
        } catch (InvalidUsername e) {
            e.printStackTrace();
            util.pause();
        }
    }
}
