package com.jet.utils;



import com.jet.utils.date.DateUtils;
import org.junit.Test;



public class DateUtilsTest {



    @Test
    public void offsetsStr2ArrayTest() throws Exception {
        String s="+1";

        String[] res = DateUtils.offsetsStr2Array(s);



        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }


    }
}
