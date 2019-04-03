package com.util.collection.hashmap;

/**
 * @Author: Calvin
 * @Date: 2019/4/2 19:15
 */
public interface CustomerMap<K, V> {

    public V put(K k, V v);

    public V get(K k);

    public int size();

    /**
     * 该Entry 作为Node 节点
     *
     * @param <K>
     * @param <V>
     */
   interface Entry<K,V>{
        K getKey();
        V getValue();
        V setValue(V value);
        int hashCode();
    }


}
