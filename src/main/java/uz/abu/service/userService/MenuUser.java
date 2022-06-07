package uz.abu.service.userService;

import uz.abu.database.Database;
import uz.abu.exception.FullUser;
import uz.abu.exception.IndexOutOf;
import uz.abu.exception.InvalidUsername;
import uz.abu.template.Book;
import uz.abu.template.Library;
import uz.abu.utils.Util;

public class MenuUser {
    public Util util = new Util();
    public Database db = Database.getDatabase();

    public void mainMenu(){
        if(!db.currentUser.isActive()){
            try {
                throw new InvalidUsername();
            } catch (InvalidUsername e) {
                e.printStackTrace();
                util.pause();
                return;
            }
        }

        a:
        while (true){
            util.clear();
            util.green("User menu");
            System.out.println("0. Exit");
            System.out.println("1. Get book");
            System.out.println("2. Show my books");
            System.out.println("3. Show all books");
            System.out.println("4. Return book");
            System.out.print("  => ");
            switch (util.numScan.nextInt()){
                case 0 -> {
                    return;
                }
                case 1 -> getBook();
                case 2 -> {
                    showMyBooks();
                    util.strScan.nextInt();
                }
                case 3 -> {
                    showAllBooks();
                    util.numScan.nextInt();
                }
                case 4 -> returnBook();
            }
        }
    }

    private void getBook() {
        if(db.currentUser.getBooks().size() == 5){
            try {
                throw new FullUser();
            } catch (FullUser e) {
                e.printStackTrace();
                util.pause();
                return;
            }
        }

        showAllBooks();
        util.blue("Enter book name to get it: ");
        String bookName = util.strScan.nextLine();

        for (Library library : db.libraries) {
            for (Book book : library.getBooks()) {
                if(book.getName().equals(bookName)){
                    if(book.getAmount() == 0){
                        try {
                            throw new IndexOutOf();
                        } catch (IndexOutOf e) {
                            e.printStackTrace();
                            util.pause();
                            return;
                        }
                    }
                    book.setAmount(book.getAmount() - 1);
                    db.currentUser.getBooks().add(book);

                    util.green("Successfully got");
                    util.pause();
                    db.setUsers();
                    db.setLibraries();
                    db.getBooks();
                    db.setBooks();
                    return;
                }
            }
        }
        try {
            throw new InvalidUsername();
        } catch (InvalidUsername e) {
            e.printStackTrace();
            util.pause();
        }
    }

    private void showMyBooks() {
        util.clear();
        util.yellow("My books");
        System.out.println();
        util.blue(util.left("   Book name", 30) +
                util.left("Author", 30) +
                util.left("Amount", 20) +
                "Library");
        System.out.println();
        int num = 1;

        for (Book book : db.currentUser.getBooks()) {

            Library library = new Library();
            for (Library library1 : db.libraries) {
                for (Book book1 : library1.getBooks()) {
                    if(book1.getId().equals(book.getId())){
                        library = library1;
                    }
                }
            }

            System.out.println(util.left(num++ + ". " + book.getName(), 30) +
                    util.left(book.getAuthor(), 30) +
                    util.left(String.valueOf(book.getAmount()), 20) +
                    library.getName());
        }
    }

    private void showAllBooks() {
        util.clear();
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

    private void returnBook() {
        showMyBooks();
        util.blue("Enter book name to return: ");
        String bookName = util.strScan.nextLine();
        for (Library library : db.libraries) {
            for (Book book : library.getBooks()) {
                if(book.getName().equals(bookName)){
                    book.setAmount(book.getAmount() + 1);
                    for (Book currentUserBook : db.currentUser.getBooks()) {
                        if (currentUserBook.getId().equals(book.getId())){
                            db.currentUser.getBooks().remove(currentUserBook);
                            break;
                        }
                    }
                    db.setUsers();
                    db.setLibraries();
                    db.getBooks();
                    db.setBooks();
                    util.green("Successfully returned");
                    util.pause();
                    return;
                }
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
