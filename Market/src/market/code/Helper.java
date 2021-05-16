package market.code;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Helper {
    public static void incorrectNumber()  {
        System.out.println("Please enter correct value!");
        sleep();
    }
    public static boolean isPositive(int num){
        return num >= 0;
    }
    public static boolean isPositive(float num){
        return !(num < 0);
    }
    public static void sleep()  {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void titlePrint(String title){
        StringBuilder stringBuilder = new StringBuilder(title);
        int c=76-title.length();
        for (int i=0;i<(c/2);i++){
            stringBuilder.insert(0,"-");
        }
        for (int i=0;i<(c/2);i++){
            stringBuilder.insert(stringBuilder.length(),"-");
        }
        if(stringBuilder.length()<76){
            stringBuilder.insert(stringBuilder.length(),"-");
        }
        printLine();
        System.out.println(stringBuilder);
        printLine();

    }
    public static void printLine(){
        System.out.println("----------------------------------------------------------------------------");
    }
    public static Scanner generateScanner(){
      return new Scanner(System.in);
    }
    public static boolean checkDate(String str){
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            format.parse(str);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}
