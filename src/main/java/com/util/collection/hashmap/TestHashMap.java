package com.util.collection.hashmap;

import org.junit.Test;

import java.util.HashMap;

/**
 * @Author: Calvin
 * @Date: 2019/4/1 22:24
 */
public class TestHashMap {


    @Test
    public void test1(){
        HashMap hashMap = new HashMap();
        hashMap.put("A 元素", "1");
    }
    @Test
    public void test2(){
        CustomerHashMap hashMap = new CustomerHashMap<String,String>();
        hashMap.put("1号", "aaaa");
        hashMap.put("2号", "bbbb");
        hashMap.put("3号", "aaaa");
        hashMap.put("4号", "bbbb");
        hashMap.put("5号", "aaaa");
        hashMap.put("6号", "bbbb");
        hashMap.put("7号", "bbbb");
        hashMap.put("14号", "bbbb");
        hashMap.put("3号", "234");
        hashMap.print();


//        hashMap.put("16号", "1");
//        hashMap.put("17号", "1");
//        hashMap.put("18号", "1");
//        hashMap.put("19号", "1");
    }
}
