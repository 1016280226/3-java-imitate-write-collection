package com.util.collection.arraylist;

import java.util.Arrays;

/**
 * 实现思路:
 * 1.构造函数:
 *    a.无参:
 *      它是由数组方式实现的，所以为数组赋上初始值为空数组。
 *    b.有参
 *      带有初始值容量。
 *      它是由数组方式实现的，所以为数组为数组赋值 = 初始值容量，但要考虑到下标不能负数
 * 2.实现添加数组元素
 *   a.传入元素，添加到数组中，从第0个开始，存放。
 *   b.假设: 如果使用了构造方法,实例了初始数组长度。例如: CustomerArrayList exampleArrayList = new CustomerArrayList(2);
 *     假设: 添加元素超过了数组的长度,就会抛出数组越界。所以导致了程序崩溃，需要进行扩容。
 *   c.重点如果实现扩容？
 *     方法一: 简单扩容: 添加元素size * 2
 *
 * 3.根据下标，获取指定的元素。
 * 4.根据下标，删除指定的元素。
 *   假设A数组为{1，2，3，4}，删除1 元素，怎样变成{2，3，4}
 *   分析:
 *    a.元素往前移动多少个: 数组长度 - 1 - index
 *    b.删除后，数组长度-1,并且最后一个元素设置为空。
 *
 */
public class CustomerArrayList<E> implements CustomerList<E>{

    // CustomerArrayList 默认是一个空数组
    private static final Object[] DEFAULT_EMPTY_ARRARY_ELEMENT = {};

    // 分配的最大数组空间
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    // CustomerArrayList 底层采用数组存放
    private E[] elementData;

    // 记录数组长度
    private int size;

    /****************************************** 第一步: 构建构造方法 *************************************************/
    /**
     * CustomerArraryList 无参构造方法
     */
    public CustomerArrayList(){
        // 默认是一个空数组
        this.elementData = (E[]) DEFAULT_EMPTY_ARRARY_ELEMENT;
    }

    /**
     * CustomerArraryList 有参构造方法
     * @param initalCapacity 初始容量值
     *
     */
    public CustomerArrayList(int initalCapacity){
        if(initalCapacity < 0 ){
             throw new IllegalArgumentException("Illegal Capacity: " + initalCapacity);
        }
        this.elementData = (E[]) new Object[initalCapacity];
    }

    /***************************************** 第二步: 实现add 和 get 方法 ********************************************/

    public E get(int index) {
        rangeCheck(index);
        E e = elementData[index];
        return e;
    }

    private void rangeCheck(int index){
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException("数组越界");
        }
    }

    /**
     * 方法一: 添加元素放入存放到数组中，当数据容量大的时候，对io操作影响大，不推荐使用
     *
     * @param e 元素
     */
    public void add1(E e){
         /********************* 方法一: 使用简单的扩容。*****************************/
         if(size == elementData.length){
            int newCapacity = size * 2;
             Object[] newArrarys = new Object[newCapacity];
            // 将原来数组的值赋值到新数组里面去
             for(int i=0; i< elementData.length; i++){
                 newArrarys[i] = elementData[i];
             }
             // 在将新素组赋值给源数组，使源数组时原来容量的2倍
             elementData = (E[]) newArrarys;
         }
         // 从第0个开始
        elementData[size++] = e;
    }

    /**
     * 方法二: 在方法一的基础上优化
     *
     * @param e 元素
     */
    public void add2(E e){
        /********************* 方法二: 使用Arrarys.copy进行的扩容。*****************************/
        if(size == elementData.length){
            int newCapacity = size * 2;
            // 在将新素组赋值给源数组，使源数组时原来容量的2倍
            elementData = Arrays.copyOf(elementData,newCapacity);
        }
        // 从第0个开始
        elementData[size++] = e;
    }

    /**
     * 方法三: 原生 ArrayList 底层每次扩容以1.5 倍
     *
     * @param e 元素
     */
    public void add3(E e){
        ensureExplicitCapacity(size + 1);
        // 从第0个开始
        elementData[size++] = e;
    }

    /**
     * 方法四: 根据下标，添加元素
     * A: {1,2,3,4,5}
     * B: {1,2,3,6,4,5}
     *
     * 分析: 1.数组长度＋1 2.根据下标，插入元素后，后面元素和下标往后移。
     * 第一步: 判断当前下标不能为0，并且不能大于数组的长度。由于，数组扩容的原因为1.5倍，所以添加一个元素时会进行扩容1个位置。
     *  if(index > size || index < 0) -> 抛出异常。
     * 第二步: 进行扩容 1 个位置。
     * 第三步: 进行向后移位+1。
     * 第四步: 根据对应下标赋值。
     * 第五步: 数组长度+1。
     *
     *
     * @param e 元素
     */
    public void add4(int index, E e){
        rangeCheck(index);
        // 判断是否扩容
        ensureExplicitCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = e;
        size++;
    }

    private void ensureExplicitCapacity(int minCapacity){
        if(size == elementData.length){
            // 原来数组的容量
            int oldCapacity = elementData.length;
            /****************** 如果初始容量为1的时候，那么他扩容的容量时多少？ 答案: 为2. ************/
            //  oldCapacity >> 1 相当于oldCapacity/2 转化为二进制 1 向右移动1位为0 ，所以 newCapacity = 1+0= 1
            int newCapacity = oldCapacity + (oldCapacity >> 1);

            // 新的容量 - 最小扩容容量 < 0
            if (newCapacity - minCapacity < 0)
                newCapacity = minCapacity; // 最少保证容量和minCapacity一样
            if (newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity); // 最多不能超过最大容量
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    /***************************************** 第三步: 获取数组长度方法 ********************************************/
    public int getSize(){
        return size;
    }

    /***************************************** 第四步: 删除数组 ********************************************/
    public E remove(int index){
       // 使用下标该值是否存在
        E e = this.get(index);
        // 相当于把删除后的后面的元素往前移动
        int numMoved = size - index - 1;
        if(numMoved > 0){
            //  删除原理
            System.arraycopy(elementData,index + 1, elementData, index, numMoved);
        }
        // 将最后一个元素边为空
        elementData[--size] = null;
        return e;
    }

    public boolean remove(E o){
        for(int index = 0; index < elementData.length; index ++) {
            if(o.equals(elementData[index])){
                remove(index);
                return true;
            }
        }
        return false;
    }


}
