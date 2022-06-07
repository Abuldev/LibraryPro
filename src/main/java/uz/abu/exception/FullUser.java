package uz.abu.exception;

public class FullUser extends Exception {
    public FullUser(){
        super("You have the maximum number of books (5)");
    }
}
