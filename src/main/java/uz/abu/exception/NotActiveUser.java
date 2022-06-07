package uz.abu.exception;

public class NotActiveUser extends Exception{
    public NotActiveUser(){
        super("You are not active. Please report boss admin");
    }
}
