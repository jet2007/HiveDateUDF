package com.jet.hive.udf.date;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataDeltaTest {


    @Test
    public void inputNonTest() throws Exception {
        //无参数时
        DateDelta ddl = new DateDelta();
        Text input1 = null;
        Text res = ddl.evaluate();
        System.out.println(res);
    }


    @Test
    public void inputNullTest() throws Exception {
        DateDelta ddl = new DateDelta();

        // INPUT1=NULL, 测试

//        1个参数时
        Text res = null;
        Text input1, input2, input3;

        input1 = null;
        res = ddl.evaluate(input1);
        System.out.println(res);

        //2个参数
        input2 = new Text("+3");
        res = ddl.evaluate(input1, input2);
        System.out.println(res);

        //3个参数
        input2 = new Text("+3");
        input3 = new Text("yyyy-MM-dd");
        res = ddl.evaluate(input1, input2, input3);
        System.out.println(res);

    }



    @Test
    public void inputBlankTest() throws Exception {
        DateDelta ddl = new DateDelta();

        // INPUT1="", 测试
//        1个参数时
        Text res = null;
        Text input1, input2, input3;

        input1 = new Text(" ");;
        res = ddl.evaluate(input1);
        System.out.println("[" + res + "]"); //[]

        //2个参数
        input2 = new Text("+3");
        res = ddl.evaluate(input1, input2);
        System.out.println("[" + res + "]"); //[]

        //3个参数
        input2 = new Text("+3");
        input3 = new Text("yyyy-MM-dd");
        res = ddl.evaluate(input1, input2, input3);
        System.out.println("[" + res + "]"); //[]
    }



    @Test
    public void Test1() throws Exception {
        DateDelta ddl = new DateDelta();

        // INPUT1="", 测试
//        1个参数时
        Text res = null;
        Text input1, input2, input3;

        input1 = new Text("20240927");;


        //2个参数
        input2 = new Text("w=1");
        res = ddl.evaluate(input1, input2);
        System.out.println("[" + res + "]"); //[]


        // "20240927" "d=-1,m-1,y+2" "20260826"
        // "20240927" "-1" "20240926"
        // "20240927" "-1" "20240926"


    }






}
