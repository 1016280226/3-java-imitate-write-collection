package com.util.collection.hashmap;

/**
 * @Author: Calvin
 * @Date: 2019/4/3 17:37
 */
public interface ExtMap<K, V> {

    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V v);
    }
}
