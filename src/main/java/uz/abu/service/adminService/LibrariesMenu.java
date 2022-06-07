package uz.abu.service.adminService;

import uz.abu.database.Database;
import uz.abu.exception.InvalidUsername;
import uz.abu.template.Library;
import uz.abu.template.User;
import uz.abu.template.UserRole;
import uz.abu.utils.Util;

import java.util.ArrayList;

public class LibrariesMenu {
    private Util util = new Util();
    private Database db = Database.getDatabase();

    public void mainMenu(){
        while (true){
            util.clear();
            System.out.println("Library menu");
            System.out.println("0. Exit");
            System.out.println("1. Add library");
            System.out.println("2. Show library");
            System.out.println("3. Edit library");
            System.out.println("4. Delete library");
            System.out.print  ("  ==> ");
            switch (util.numScan.nextInt()){
                case 0:
                    return;
                case 1:
                    addLibrary();
                    break;
                case 2:
                    util.clear();
                    showLibraries();
                    System.out.print(" ==> ");
                    util.numScan.nextInt();
                    break;
                case 3:
                    editLibrary();
                    break;
                case 4:
                    deleteManager();
                    break;
            }
        }
    }

    private void addLibrary() {
        util.clear();
        System.out.println("Add Library");
        Library library = new Library();
        util.blue("Name: ");
        library.setName(util.strScan.nextLine());
        util.yellow("Available managers: ");
        System.out.println();
        for (User user : db.users) {
            if(user.getRole().equals(UserRole.MANAGER) && !user.isHaveLibrary()){
                System.out.println(" * " + user.getUsername());
            }
        }
        util.blue("Enter username to upgrade as manager for admin: ");
        String username = util.strScan.nextLine();

        for (User user : db.users) {
            if(user.getRole().equals(UserRole.MANAGER) && !user.isHaveLibrary()
                    && user.getUsername().equals(username)){
                library.setManager(user);
                library.setBooks(new ArrayList<>());
                db.libraries.add(library);
                db.setLibraries();

                user.setHaveLibrary(true);
                db.setUsers();
                util.green("Successfully added");
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

    private void showLibraries() {
        util.yellow("Libraries");
        System.out.println();
        util.blue(util.left("   Name", 20) +
                util.left("Manager", 20) +
                "Number of books");
        System.out.println();
        int num = 1;
        for (Library library : db.libraries) {
            System.out.println(util.left(num++ + ". " + library.getName(), 20) +
                    util.left(library.getManager().getUsername(), 20) +
                    library.getBooks().size()
            );
        }
    }

    private void editLibrary() {
        util.clear();
        showLibraries();
        util.red("Enter library name: ");
        String name = util.strScan.nextLine();
        for (Library library : db.libraries) {
            if(library.getName().equals(name)){
                for (User user : db.users) {
                    if(user.getUsername().equals(library.getManager().getUsername())){
                        user.setHaveLibrary(false);
                        db.setUsers();
                    }
                }

                util.blue("Enter new name: ");
                library.setName(util.strScan.nextLine());

                util.yellow("Available managers: ");
                System.out.println();
                for (User user : db.users) {
                    if(user.getRole().equals(UserRole.MANAGER) && !user.isHaveLibrary()){
                        System.out.println(" * " + user.getUsername());
                    }
                }
                util.blue("Enter username to upgrade as manager for admin: ");
                String username = util.strScan.nextLine();

                for (User user : db.users) {
                    if(user.getRole().equals(UserRole.MANAGER) && !user.isHaveLibrary()
                            && user.getUsername().equals(username)){
                        library.setManager(user);
                        user.setHaveLibrary(true);
                        db.setUsers();
                    }
                }

                db.setLibraries();
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
        showLibraries();
        util.red("Enter library name: ");
        String name = util.strScan.nextLine();
        for (Library library : db.libraries) {
            if(library.getName().equals(name)){
                for (User user : db.users) {
                    if(user.getUsername().equals(library.getManager().getUsername())){
                        user.setHaveLibrary(false);
                        db.setUsers();
                    }
                }

                db.libraries.remove(library);

                db.setLibraries();
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
