package com.m.notas.utils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {

    public static String toString(Date date){
        SimpleDateFormat sdf;
        if(Locale.getDefault().getDisplayLanguage().equals("português")){
            sdf = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT_PT);
        } else {
            sdf = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT_DEFAULT);
            System.out.println(Locale.getDefault().getDisplayLanguage());
        }

        return sdf.format(date);
    }
}
