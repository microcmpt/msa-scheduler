package com.msa.scheduler.support;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date parse util.
 * @author sxp
 */
public class DateParseUtil {
    /**
     * Date 2 string string.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String date2String(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
