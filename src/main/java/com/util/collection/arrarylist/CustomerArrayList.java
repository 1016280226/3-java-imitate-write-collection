package com.util.collection.arrarylist;

/**
 * 实现思路:
 * 1.构造函数:
 *    a.无参:
 *      它是由数组方式实现的，所以为数组赋上初始值为空数组。
 *    b.有参
 *      带有初始值容量。
 *      它是由数组方式实现的，所以为数组为数组赋值 = 初始值容量，但要考虑到下标不能负数
 * 2.实现添加 add 方法
 *   a.传入元素，添加到数组中，从第0个开始，存放。
 *   b.假设: 如果使用了构造方法,实例了初始数组长度。例如: CustomerArrayList exampleArrayList = new CustomerArrayList(2);
 *     假设: 添加元素超过了数组的长度,就会抛出数组越界。所以导致了程序崩溃，需要进行扩容。
 *   c.重点如果实现扩容？
 *     方法一: 简单扩容: 添加元素size * 2
 *
 * 3.实现获取 get 方法
 *
 */
public class CustomerArrayList {

    // CustomerArrayList 默认是一个空数组
    private static final Object[] DEFAULT_EMPTY_ARRARY_ELEMENT = {};

    // CustomerArrayList 底层采用数组存放
    private Object[] elementData;

    // 记录数组长度
    private int size;

    /****************************************** 第一步: 构建构造方法 *************************************************/
    /**
     * CustomerArraryList 无参构造方法
     */
    public CustomerArrayList(){
        // 默认是一个空数组
        this.elementData = DEFAULT_EMPTY_ARRARY_ELEMENT ;
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
        this.elementData = new Object[initalCapacity];
    }



    /***************************************** 第二步: 实现add 和 get 方法 ********************************************/
    /**
     * 添加元素放入存放到数组中
     *
     * @param e 元素
     */
    public void add(Object e){

         /********************* 方法一: 使用简单的扩容*****************************/
         if(size == elementData.length){
            int newCapacity = size * 2;
            Object[] newArrarys = new Object[newCapacity];
            // 将原来数组的值赋值到新数组里面去
             for(int i=0; i< elementData.length; i++){
                 newArrarys[i] = elementData[i];
             }
             elementData = newArrarys;
         }

         // 从第0个开始
        elementData[size++] = e;

    }

    /**
     * 根据元素下标，获取对应的元素
     * @param index
     * @return
     */
    public Object get(int index){
        return elementData[index];
    }

    public static void main(String[] args) {

        CustomerArrayList arrayList = new CustomerArrayList(2);
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");

        System.out.println(arrayList.size);


    }

}
