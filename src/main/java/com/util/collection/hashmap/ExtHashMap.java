package com.util.collection.hashmap;

/**
 * @Author: Calvin
 * @Date: 2019/4/3 17:45
 */
public class ExtHashMap<K,V> implements ExtMap<K,V>{

    private int size;
    private Node<K,V> [] table;
    static final float DEFAULT_LOAD_FACTOR = 0.75f; // 负载因子越小，hash（地址）冲突越少。
    static int DEFAULT_INITIAL_CAPACITY = 1 << 4; // table 默认初始容量为16

    private void restSize(){
      Node<K,V>[] newTable = new Node[DEFAULT_INITIAL_CAPACITY << 1];
      for(int i=0; i< table.length; i++){
          Node<K,V> origNode = table[i];
          while(origNode != null){
              // 赋值为null---为了垃圾回收机制能够回收 将之前的node删除
              table[i] = null;
              // 重新计算index
              int newIndex = hash(origNode.key, newTable.length);
              if (origNode.key.equals("22号") || origNode.key.equals("66号")) {
                  System.out.println("日志记录");
              }
              // 为了下一个循环，所以为了临时存储
              Node<K,V> origNodeNext = origNode.next;
              // 原来Node 下标相同的index,以链表进行存储。
              origNode.next = newTable[newIndex];
              // 将老的Node进行重新插入到新数组中。
              newTable[newIndex] = origNode;
              // 进行下一个循环
              origNode = origNodeNext;
          }
      }
        // 3.将newtable赋值给老的table
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newTable.length;
        newTable = null;/// 赋值为null---为了垃圾回收机制能够回收
    }

    public V get(K k){
        Node<K,V> node = table[hash(k, table.length)];
        if(node != null){
            Node<K,V> origNode = node;
            while(origNode != null){
                if(origNode.getKey().equals(k)){
                    return origNode.getValue();
                }
                origNode = origNode.next;
            }
        }
        return null;
    }


    public V put(K k,V v){
        if(table == null){
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        if(size > DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY){
            restSize();
        }
        int index = hash(k, table.length);
        Node<K,V> node = table[index];
        if(node == null ){
            node = new Node(k,v, null);
            size ++;
        }else{
            Node<K,V> origNode = node;
            if (origNode.getKey().equals("22号") || origNode.getKey().equals("66号")) {
                System.out.println("日志记录");
            }
            while(origNode != null){
                if(origNode.getKey().equals(k) || origNode.getKey() == k){
                    return origNode.setValue(v);
                }else{

                    if(origNode.next == null){
                        Node<K,V> conflictNode = new Node(k, v, origNode);
                        node = conflictNode;
                        conflictNode = null;
                        size ++ ;
                    }
                }
                origNode = origNode.next;
            }
        }
        table[index] = node;
        return node.getValue();
    }

    public int hash(K k,int lenght){
        int index = k.hashCode() % lenght;
        return index;
    }

    class Node<K,V> implements Entry<K,V>{
        private K key;
        private V value;
        private Node<K,V> next;

        Node(K k, V v, Node<K,V> next){
            this.key = k;
            this.value = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
             V origValue = this.value;
             this.value = v;
            return origValue;
        }
    }

    public void print() {
        for (int i = 0; i < table.length; i++) {
           Node<K,V> node = table[i];
            System.out.print("下标位置:[" + i + "]");
            while (node != null) {
                System.out.print("[ key:" + node.getKey() + ", value:" + node.getValue() + "]");
                node = node.next;
            }
            System.out.println();
        }
    }


}
