package com.util.collection.hashmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 ArrayList 实现 HashMap集合
 * 1. 缺点:
 *    a.添加非常慢,由于数组的原因，他会向右移动。
 *    b.查询时使用每次都for循环或进行二分查询，效率也是不高。
 *
 * @param <Key>
 * @param <Value>
 */
public class CustomerArrayListHashMap<Key, Value> {

    // HashMap 存储容量
    private List<Entry<Key, Value>> tables = new ArrayList<Entry<Key, Value>>();
    // HashMap 实际容量
    private int size;

    /**
     * 添加key、Value
     * 1. 定义hashMap 容器。
     * 2. 将hashMap 容器 存入到ArrayList 集合中去。
     *
     * @param key
     * @param value
     */
    public void put(Key key, Value value) {
        Entry<Key, Value> entryExist = getEntry(key);
        if (value != null) {
            entryExist.value = value;
        } else {
            Entry entry = new Entry(key, value);
            tables.add(entry);
        }
    }

    public Value get(Key key) {
        Entry<Key, Value> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    public Entry<Key, Value> getEntry(Key key) {
        for (Entry<Key, Value> entry : tables) {
            if (entry.key.equals(key))
                return entry;
        }
        return null;
    }
}

class Entry<Key, Value> {
    /**
     * hashMap 存储 key
     **/
    Key key;
    /**
     * hashMap 存储 value
     **/
    Value value;

    public Entry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
