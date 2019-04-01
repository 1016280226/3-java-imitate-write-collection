package com.util.collection.linkedlist;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @Author: Calvin
 * @Date: 2019/3/31 19:42
 */
public class TestLinkedList {

    @Test
    public void test1(){
        System.out.println("1.添加链表元素有:");
        LinkedList linkedList = new LinkedList();
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("e");
        linkedList.add(0, "d");
        linkedList.add(0, "e");
        System.out.println(linkedList.size());

        linkedList.forEach(
              e -> System.out.print(e)
        );
        int index = 4;
        Object e = linkedList.get(index);
        System.out.println("\n 2.根据下标获取链表元素:" + index + " 元素为: " + e);

        int index2 = 0;
        System.out.println("\n 删除前元素为:" +  linkedList.remove(index2));
        System.out.println("删除后元素为:" + linkedList.get(0));

    }

    @Test
    public void test2(){
        System.out.println("1.添加链表元素有:");
        CusotmerLinkedList<String> linkedList = new CusotmerLinkedList<String>();
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        for(int i = 0; i < linkedList.size(); i ++){
            System.out.println(linkedList.get(i));
        }
        int index = 0;
        Object e = linkedList.get(index);
        System.out.println("\n 2.根据下标获取链表元素:" + index + " 元素为: " + e);
        System.out.println("\n 删除前元素为:" +  linkedList.remove(index));
        System.out.println("删除后元素为:" + linkedList.get(0));

    }
}
