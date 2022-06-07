package uz.abu.utils;

import java.util.Scanner;

public class Util {
//    BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
//    RED	\u001B[31m	RED_BACKGROUND	\u001B[41m
//    GREEN	\u001B[32m	GREEN_BACKGROUND	\u001B[42m
//    YELLOW	\u001B[33m	YELLOW_BACKGROUND	\u001B[43m
//    BLUE	\u001B[34m	BLUE_BACKGROUND	\u001B[44m
//    PURPLE	\u001B[35m	PURPLE_BACKGROUND	\u001B[45m
//    CYAN	\u001B[36m	CYAN_BACKGROUND	\u001B[46m
//    WHITE	\u001B[37m	WHITE_BACKGROUND	\u001B[47m
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public Scanner numScan = new Scanner(System.in);
    public Scanner strScan = new Scanner(System.in);

    public String right(String str, int width) {
        for (int x = str.length(); x < width; ++x)
        {
            str = ' ' + str;
        }
        return str;
    }
//    public String middle(String str) {
//        return "   ***   " + str + "   ***   ";
//    }
    public String left(String str, int width) {
        for (int x = str.length(); x < width; ++x)
        {
            str += ' ';
        }
        return str;
    }

    public void pause(){
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(){
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public void black(String str){
        System.out.print(BLACK + str + WHITE);
    }
    public void red(String str){
        System.out.print(RED + str + WHITE);
    }
    public void green(String str){
        System.out.print(GREEN + str + WHITE);
    }
    public void yellow(String str){
        System.out.print(YELLOW + str + WHITE);
    }
    public void blue(String str){
        System.out.print(BLUE + str + WHITE);
    }
    public void purple(String str){
        System.out.print(PURPLE + str + WHITE);
    }
    public void cyan(String str){
        System.out.print(CYAN + str + WHITE);
    }
    public void white(String str){
        System.out.print(WHITE + str + WHITE);
    }

}
