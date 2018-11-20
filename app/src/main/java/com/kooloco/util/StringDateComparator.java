package com.kooloco.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class StringDateComparator implements Comparator<String> {
    //Mon Mar 05 05:53:30 GMT+00:00 2018 //Mar 5, 2018 7:26:41 AM

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");

    public int compare(String lhs, String rhs) {
        try {
            return dateFormat.parse(rhs).compareTo(dateFormat.parse(lhs));
        } catch (ParseException e) {
            return -1;
        }
    }
}