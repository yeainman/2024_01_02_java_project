package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String getNowDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        return dateFormat.format(today);
    }
}