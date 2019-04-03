package com.util.collection.hashmap;

/**
 * 纯手写HashMap集合，基于JDK1.7 数组+ 单链表
 * <p>
 * 实现思路:
 * 1. 第一步我们先要做什么？
 * 思考分析:
 * a. 在我们不知道的情况下，我们先看下Java8 HashMap 底层源码的构造方法做了什么？
 *     答:从构造方法中我们看到定义的是负载因子。从而不知道怎么实现，所以我们需要看到HashMap.put 底层源码的实现原理.
 * b. 从put 源码中我们可以看到Node 节点，需要思考的是怎么将Key、Value 放入容器中？
 *     答: 由于我们知道在HashMap1.7 中的实现是通过 数组+ 单链表实现的，所以我们需要的是将元素装进容器，使用Node.
 * c. 怎样实现Put方法,意思就是怎样将keyValue放入到容器中?
 *     答: 通过hashMap的特性，就是将KeyValue值放入Node然后存入到数组中。
 * d. 为什要进行扩容？扩容数组有什么影响？
 *     答: 1.因为链表节点多越长，查询效率低。最好减少hash碰撞，最好使用hash地址查找法找到对应的地址。
 *         2.hashCode相同，但是扩容数组之后的长度发生了变化，需要重新计算hash地址。
 * 第一步: 了解HashMap，将Key、Value,存入到容器中，所以需要定义Node。
 * 第二步: 定义数组存放hashMap的keyValue数组元素、定义数组的存储容量、定义负载因子。
 * 第三步: 实现Put方法
 * 第四步: 实现Get方法
 * 第五步: 实现扩容数组，实现res
 *
 * @Author: Calvin
 * @Date: 2019/4/2 19:14
 */
public class CustomerHashMap<K, V> implements CustomerMap<K, V> {

    /********** 第二步: 定义数组存放hashMap的keyValue数组元素、定义数组的存储容量、定义负载因子。 *********/
    Node<K, V>[] table = null; // 默认没有初始化容器，懒加载（也就是懒汉模式)
    int size;
    static final float DEFAULT_LOAD_FACTOR = 0.75f; // 负载因子越小，hash（地址）冲突越少。
    static int DEFAULT_INITIAL_CAPACITY = 1 << 4; // table 默认初始容量为16


    /********** 第一步: 定义Node *********/
    class Node<K, V> implements Entry<K, V> {

        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
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

        /**
         * 获取老的值
         *
         * @param value
         * @return
         */
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }


    /**
     * 第三步: PUT 添加
     * 1.判断table 数组是否为空。
     * 2.判断数组是否需要扩容。
     *     实际存储大小 = 负载因子(DEFAULT_LOAD_FACTOR ) * 初始容量(DEFAULT_INITIAL_CAPACITY) -> 0.75 * 16 = 12
     *     如果size >= 12 时候开始扩容数组，扩容数组大小的2倍。
     * 3.计算hashCode值指定的下标，根据下标获取table中的数组元素（Node）。
     * 4.根据数组元素，判断是否存在hash地址冲突。
     * a.如果Node元素为空，表示不存在hash地址冲突,就要将新的Node节点保存到数组中，并且数组存储容量加1;
     * b.如果Node元素不为空，表示存在hash地址冲突。
     * b_1:判断是Key值覆盖，还是hash冲突。为覆盖时，进行覆盖对应的value值，否则将冲突的的元素放入到链表的第一个（后进先出）数组存储容量+1.
     * 5.将元素放入到数组中存储。
     *
     * @param k
     * @param v
     * @return
     */
    @Override
    public V put(K k, V v) {
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        if(size > (DEFAULT_INITIAL_CAPACITY * DEFAULT_INITIAL_CAPACITY)){
            restSize();
        }
        int index = hash(k, table.length);
        Node<K, V> node = table[index];
        if (node == null) {
            node = new Node<>(k, v, null);
            size++;
        } else {

            // 表示该Node
            Node<K, V> newNode = node;
            while (newNode != null) {
                // hashCode是相同的 || 对象是相同的, 覆盖key 中的value 值。
                if (newNode.getKey().equals(k) || newNode.getKey() == k) {
                    newNode.value = v;
                    // 返回老的值
                    return newNode.setValue(v);
                } else {
                    // Node 元素没有节点，说明遍历的时最后一个
                    if (newNode.next == null){
                        /** 发生hash地址冲突(hash地址相同，但是对象不同) **/
                        // 后进先出
                        newNode = new Node<K, V>(k, v, newNode);
                        size++;
                    }
                }
                newNode = node .next;
            }
        }
        table[index] = node;
        return node.getValue();
    }

    /**
     * 第四步: 根据Get 的获取Value
     * 1.进行hash地址查找法，查到对应的地址。
     * 2.根据下标查询对应的Node。
     * 3.判断:Node 是否为空，不为空的时候，判读key对象是否相同如果相同进行，返回对应的Value。
     * 4.如果不相同的话，从Node.next 的找（因为hash冲突的原因，所以有多个节点）。
     * @param k
     * @return
     */
    @Override
    public V get(K k) {
        Node<K, V> node = getNode(table[hash(k,table.length)], k);
        return node == null ? null : node.value;
    }

    /**
     * 对数组table 进行扩容
     *   1.扩容需要生成table 的2 倍
     *   2. 重新计算hash 地址，存放到table中存储。
     *   3. 将新的table 覆盖 旧的table 中去。
     *
     * @return
     */
    public void restSize(){
       Node<K,V>[] newTable =  new Node[DEFAULT_INITIAL_CAPACITY << 1];
       for(int i =0; i< table.length; i++){
           Node<K,V> oldNode = table[i];
           while(oldNode != null){
               table[i] = null; // 为了垃圾回收机制。
               K oldK = oldNode.key;
               int index = hash(oldK,newTable.length); // 重新计算老的key 的index
               // 原来的Node next
               Node<K,V> oldNext = oldNode.next;
               // 如果index下标在新的newTable发生index相同的时候，以链表进行存储。(原来Node的下一个是最新的 == 原来的node存放在新的Node的下一个)
               oldNode.next = newTable[index];

               // 将之前的Node 赋值给新的newTable
               newTable[index] = oldNode;
               // 判断是否继续循环遍历
               oldNode = oldNext;
           }
       }
       // 将newTable 赋值个老的table;
        table = newTable;
       DEFAULT_INITIAL_CAPACITY = newTable.length;
       newTable = null; // 为了垃圾回收机制。
    }


    /**
     * hash 地址查找法
     * 算法: hashCode(哈希值) %  table.lenght(存储的数组的长度）= hash地址（数组下标）
     *
     * @param k
     * @param lenght
     * @return
     */
    public int hash(K k, int lenght) {
        int hashCode = k.hashCode();
        int index = hashCode % lenght;
        return index;
    }



    public Node<K,V> getNode(Node<K,V> node, K k){
        while(node != null){
            if(node.getKey().equals(k)){
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return table.length;
    }


    public void print() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置:[" + i + "]");
            while (node != null) {
                System.out.print("[ key:" + node.getKey() + ", value:" + node.getValue() + "]");
                node = node.next;
            }
            System.out.println();
        }
    }

}
