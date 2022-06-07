package uz.abu.service.adminService;

import uz.abu.database.Database;
import uz.abu.exception.InvalidUsername;
import uz.abu.template.Library;
import uz.abu.template.User;
import uz.abu.template.UserRole;
import uz.abu.utils.Util;

public class ManagersMenu {
    private Util util = new Util();
    private Database db = Database.getDatabase();

    public void mainMenu(){
        while (true){
            util.clear();
            System.out.println("Manager menu");
            System.out.println("0. Exit");
            System.out.println("1. Add user");
            System.out.println("2. Show manager");
            System.out.println("3. Edit manager");
            System.out.println("4. Delete manager");
            System.out.print  ("  ==> ");
            switch (util.numScan.nextInt()){
                case 0:
                    return;
                case 1:
                    addManager();
                    break;
                case 2:
                    util.clear();
                    showManagers();
                    System.out.print(" ==> ");
                    util.numScan.nextInt();
                    break;
                case 3:
                    editManager();
                    break;
                case 4:
                    deleteManager();
                    break;
            }
        }
    }

    private void addManager() {
        util.clear();
        System.out.println("Add manager");
        User user = new User();
        System.out.print("Username: ");
        user.setUsername(util.strScan.nextLine());
        System.out.print("Password: ");
        user.setPassword(util.strScan.nextLine());
        user.setActive(true);
        user.setRole(UserRole.MANAGER);
        user.setHaveLibrary(false);
        db.users.add(user);
        db.setUsers();

        util.green("Successfully added");
        util.pause();
    }

    private void showManagers() {
        util.yellow("Managers");
        System.out.println();
        util.blue(util.left("   Username", 20) +
                util.left("Password", 20) +
                util.left("Active", 20) +
                "Library");
        System.out.println();
        int num = 1;
        for (User user : db.users) {
            System.out.print(util.left(num++ + ". " + user.getUsername(), 20) +
                    util.left(user.getPassword(), 20) +
                    util.left(String.valueOf(user.isActive()), 20));

            boolean s = false;
            for (Library library : db.libraries) {
                if(library.getManager().equals(user)){
                    System.out.println(library.getName());
                    s = true;
                }
            }
            if(!s){
                System.out.println("No library");
            }
        }
    }

    private void editManager() {
        util.clear();
        showManagers();
        util.red("Enter username: ");
        String username = util.strScan.nextLine();
        for (User user : db.users) {
            if(user.getUsername().equals(username)){
                util.blue("Enter new username: ");
                user.setUsername(util.strScan.nextLine());
                util.blue("Enter new password: ");
                user.setPassword(util.strScan.nextLine());

                db.setUsers();
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
        showManagers();
        util.red("Enter username: ");
        String username = util.strScan.nextLine();
        for (User user : db.users) {
            if(user.getUsername().equals(username)){

                for (Library library : db.libraries) {
                    if(library.getManager().equals(user)){
                        db.libraries.remove(library);
                        db.setLibraries();
                    }
                }

                db.users.remove(user);
                db.setUsers();
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
