package com.util.collection.hashmap;

import com.util.collection.arraylist.CustomerList;

import java.util.LinkedList;

/**
 * 基于 LinkedList 实现 HashMap集合
 *
 * @param <Key>
 * @param <Value>
 */
public class CustomerLinkedListHashMap<Key, Value> {

    // 存放HasMap 信息
    LinkedList<Entry<Key, Value>>[] tables = new LinkedList[998];

    // HashMap 实际容量
    private int size = tables.length;


    /**
     * hash 算法
     * 公式: 索引地址 = 根据key 哈希值 % 链表集合数组的长度
     * <p>
     * hashCode 两个对象比较的时候，如果hashCode 相同，对象的值是否一定相同?
     * 答: 不一定相同,因为使用的时hase算法(散列式算法)，hash地址 =  hashCode % tables.lenght，
     * 在同tables[hash] = value 就能找到对的值。
     * <p>
     * equals 两个对象比较的时候，如果equals 相同，对象的值是否一定相同?
     * *  答: 相同
     *
     * @return
     */
    private int hash(Key key) {
        // 根据key 哈希值 % 链表集合数组的长度
        int hash = key.hashCode() % size;
        System.out.println(hash);
        return hash;
    }

    /**
     * 添加key、Value
     * 1.将key,value 存入到Entry 中。
     * 2.根据hash算法，查出索引值。
     * 3.根据索引值找到对应的链表。
     * 4.判断链表是否有值:
     *   4_a.链表为空，说明没有冲突，将map容器放入链表中存储。
     *   4_b.否则，链表有值，使用for循环遍历,判断链表的Key值是否相等于传入来的key值:
     *     4_b_1.相同,value值进行覆盖。
     *     4_b_2.不相同，但有可能hash 索引值碰撞冲突问题,就将entryMap 添加到链表后的一个节点
     *
     * @param key
     * @param value
     */
    public void put(Key key, Value value) {
        // 1. 将key,value 存入到Entry 中。
        Entry newEntry = new Entry(key, value);
        // 2.根据hash算法，查出索引值。
        int index = hash(key);
        // 3.根据索引值找到对应的链表。
        LinkedList<Entry<Key, Value>> linkedList = tables[index];


        // 4.判断链表是否有值:
        if (linkedList == null) {
            linkedList = new LinkedList<>();
            // 4_a.链表为空，说明没有冲突，将map容器放入链表中存储。
            linkedList.add(newEntry);
        }else{
           // 4_b.否则，链表有值，使用for循环遍历。
            for(Entry entry : linkedList){
                // 判断链表的Key值是否相等于传入来的key值:
                if(entry.getKey().equals(key)){
                    // 4_b_1.相同,value值进行覆盖
                    entry.value = value;
                }else{
                    /******************* 解决: hash 索引值冲突 ***********************/
                    // 4_b_2.不相同，但有可能hash 索引值碰撞冲突问题,就将entryMap 添加到链表后的一个节点。
                    linkedList.add(entry);
                }
            }
        }
    }


    /**
     * 根据Key值，获取Value值
     * 1.使用Hash算法
     *
     * @param key
     * @return
     */
    public Value get(Key key) {
        int index = hash(key);
        LinkedList<Entry<Key,Value>> linkedList = tables[index];
        for(Entry<Key,Value> entry : linkedList){
            if(entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CustomerLinkedListHashMap linkedListHashMap = new CustomerLinkedListHashMap();
        linkedListHashMap.put("a", "aaa");
        linkedListHashMap.put("b", "bbb");
        linkedListHashMap.put("c", "ccc");
        linkedListHashMap.put("d", "ddd");
        linkedListHashMap.put("e", "eee");
        linkedListHashMap.put("f", "fff");
        linkedListHashMap.put("g", "ggg");
        linkedListHashMap.put("a1", "aaa");
        linkedListHashMap.put("b1", "bbb");
        linkedListHashMap.put("c1", "ccc");
        linkedListHashMap.put("d1", "ddd");
        linkedListHashMap.put("e1", "eee");
        linkedListHashMap.put("f1", "fff");
        linkedListHashMap.put("g1", "ggg");

    }
}




