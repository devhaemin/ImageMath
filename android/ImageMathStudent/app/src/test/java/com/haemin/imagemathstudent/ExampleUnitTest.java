package com.haemin.imagemathstudent;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        long dateTime = System.currentTimeMillis();
        Date date = new Date(dateTime);
        String[] calHeader = {"일","월","화","수","목","금","토"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String time = (calendar.get(Calendar.MONTH)+1)+". "+calendar.get(Calendar.DATE)+" "+calHeader[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        System.out.println(time);
        assertEquals("2. 22 토",time);
    }
}