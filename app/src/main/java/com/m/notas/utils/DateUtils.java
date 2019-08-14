package com.m.notas.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class DateUtils {

    public static String prettyTime(Date date){
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(date);
    }
}
