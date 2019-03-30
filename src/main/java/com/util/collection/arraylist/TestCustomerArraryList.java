package com.util.collection.arraylist;

import org.junit.Test;

import java.util.ArrayList;

public class TestCustomerArraryList{

    @Test
    public void testAdd1(){
        CustomerArrayList arrayList = new CustomerArrayList(2);
        arrayList.add1("1");
        arrayList.add1("2");
        arrayList.add1("3");
        System.out.println(arrayList.getSize());
    }

    @Test
    public void testAdd2(){
        CustomerArrayList arrayList = new CustomerArrayList(2);
        arrayList.add2("1");
        arrayList.add2("2");
        arrayList.add2("3");
        System.out.println(arrayList.getSize());
    }

    @Test
    public void testAdd3(){
        CustomerArrayList arrayList = new CustomerArrayList(1);
        arrayList.add3("1");
        arrayList.add3("2");
        arrayList.add3("3");
        System.out.println(arrayList.getSize());
    }

    @Test
    public void testAdd4(){
        ArrayList arrayList = new ArrayList(1);
        arrayList.add("1");
        arrayList.add("2");
//        arrayList.add3("3");
        arrayList.add(2,"4");
        System.out.println(arrayList.size());
    }

    @Test
    public void remove1(){
        CustomerArrayList arrayList = new CustomerArrayList(1);
        arrayList.add3("1");
        arrayList.add3("2");
        arrayList.add3("3");
        Object remove1 = arrayList.remove(0);
        Object remove2 = arrayList.remove(2);
        Object remove3 = arrayList.remove(3);
        System.out.println(arrayList.getSize());
    }


    @Test
    public void remove2(){
        CustomerArrayList arrayList = new CustomerArrayList(1);
        arrayList.add3("1");
        arrayList.add3("2");
        arrayList.add3("3");
        Object remove1 = arrayList.remove(0);
        Object remove2 = arrayList.remove(2);
        Object remove3 = arrayList.remove(3);
        System.out.println(arrayList.getSize());
    }

}
