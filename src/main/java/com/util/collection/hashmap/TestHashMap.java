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
    @Test
    public void test3(){
        // 基于什么原则 后进选出 非公平锁与公平锁
        ExtHashMap extHashMap = new ExtHashMap<String, String>();
        extHashMap.put("1号", "1号");// 0
        extHashMap.put("2号", "1号");// 1
        extHashMap.put("3号", "1号");// 2
        extHashMap.put("4号", "1号");// 3
        extHashMap.put("6号", "1号");// 4
        extHashMap.put("7号", "1号");
        extHashMap.put("14号", "1号");

        extHashMap.put("22号", "1号");
        extHashMap.put("26号", "1号");
        extHashMap.put("27号", "1号");
        extHashMap.put("28号", "1号");
        extHashMap.put("66号", "66");
        extHashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        extHashMap.print();
        System.out.println("扩容后数据....");
        extHashMap.put("31号", "1号");
        extHashMap.put("66号", "123466666");
        extHashMap.print();
        // 修改3号之后
        System.out.println(extHashMap.get("66号"));
    }
}
