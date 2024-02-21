package org.example;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date today = new Date();

        System.out.println("포맷 지정 후2: " + dateFormat.format(today));
    }
}