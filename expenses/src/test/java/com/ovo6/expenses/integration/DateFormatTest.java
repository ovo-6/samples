package com.ovo6.expenses.integration;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ovo on 09.03.2017.
 */
public class DateFormatTest {

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = sdf.parse("1900-01-01T00:00:00.000Z");
        assertNotNull(date);
    }
}
