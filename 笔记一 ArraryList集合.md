# 笔记二 ArrayList集合

[TOC]

## 1.纯手写ArrayList

> 问题分析：
>
> 1. 我们平时是怎么样使用ArrayList的？
>    - 答：
>      - ArrayList arrays1 = new ArrayList<>();
>      - ArrayList arrays2 = new ArrayList<>(2);
> 2. ArrayList 底层无参构造函数是通过什么实现的？有参数通过什么实现的？
>    - 答：
>      - 源码查看后是通过**数组**。
>      - 源码查看后，通过**定义数组容量**，将传入的参数赋值初始化数组。
>        - 例如：new ArrayList(2), 2是数组容量，将elementData = new Object[2];
>
> 3. ArrayList 底层有哪些方法我们是常用的，底层是怎么样实现的使用了哪些技术和原理？
>    - 答：
>      - add(E e)  添加元素到集合中存放
>      - add(int index, E e) 根据下标，添加指定的元素到集合中存放
>      - remove(int index) 根据下标，删除元素
>      - get(int index) 根据下标，获取元素
>      - size() 获取数组长度



### 1.1 根据问题1&2，编写无参&有参构造函数

- #### 编写无参构造方法

> 实现思路：
>
> 1. 自定义创建一个ArrayList<E> 泛型类。
> 2. 根据源码查看后，定义一个数组属性为空数组。
> 3. 创建构造方法，初始化elementData为空数组。

查看**ArrayList.class 源码**的**无参构造方法**，如下：

```java
public class ArrayList<E> 
	extends AbstractList<E> 
	implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{    
  // 2.是由数组实现的
  transient Object[] elementData; 
 
  public ArrayList() {
     // 1.看到elementDate
     this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA; 
  } 
}

public interface List<E> extends Collection<E> {...}
```

> *结论1：*
>
> - **ArrayList 底层是由数组实现的，数组名称elementData。**
>
> - **ArrayList -> 实现了List接口 -> List继承了 Collection**。
>
> - **List 集合**
>
>   - **List 集合是有序的** 
>   - **元素可以重复的**
>
>   *分析：因为ArrayList 实现了 List集合接口, ArrayList是**数组实现的**,而数组是根据**下标（索引）**来访问数组的元素, 所以集合是有序并且元素可以重复。*



自定义编写ArrayList 无参构造方法，如下：

```java
public class CustomerArrayList<E>{
    // CustomerArrayList 底层采用数组存放
    private E[] elementData;
   
    public CustomerArrayList(){
        // 默认是一个空数组
        this.elementData = (E[]) DEFAULT_EMPTY_ARRARY_ELEMENT;
    }
}
```



- #### 编写有参构造方法

> 实现思路：
>
> 1. 在构造方法中，如果传入的参数大于0，设置数组的容量为传入的值。
> 2. 否则如果等于0，是一个空数组。
> 3. 否则，抛出异常。

查看**ArrayList.class 源码**的**有参构造方法**，如下：

```java
 /**
  * 第一个：构造方法是通过初始化定义数组的长度。
  * 1.如果传入的参数大于0，设置数组的长度。
  * 2.否则，是一个空数组，
  * 3.否则，抛出异常。
  */
 public ArrayList(int initialCapacity) {
     if (initialCapacity > 0) {
         this.elementData = new Object[initialCapacity];
     } else if (initialCapacity == 0) {
         this.elementData = EMPTY_ELEMENTDATA;
     } else {
         throw new IllegalArgumentException("Illegal Capacity: "+
                                            initialCapacity);
     }
 }
```

自定义编写ArrayList 有参构造方法，如下：

```java
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
```



### 1.2 根据问题3，编写常用的ArrayList 方法

#### 1.2. 1 编写 add(E e) 方法

> 实现思路：
>
> 1. 传入元素，添加到数组中，从第0个下标开始存放元素。
>
> -   *问题 1：在ArrayList 无参构造方法中，存储容量为0，它是怎样将数组存入数组中呢？*
>   - 答：根据源码分析，在add(E e)方法中，使用了Arrays.copyOf扩容技术, 并且无参构造函数时，添加第一个元素，数组扩容初始化值为10.
> - *问题 2：如果ArrayList 有参构造方法中初始容量为1的时候，那么它扩容容量是多少呢？*
>   - 答：**容量是2，当add添加第一个元素时数组容量为1；当添加第二个元素时，进行扩容为2。**   
>
> 2. 方法一:  每次扩容以2 倍。
>
> ​       方法二: 在方法一的基础上优化 ，使用Arrays.copyOf方法进行扩容。
>
> ​       方法三：模仿 ArrayList 底层**每次扩容以1.5 倍**。



查看**ArrayList.class 源码**的**add方法**，如下：

- 当前分析对于无参构造方法：

```java
transient Object[] elementData; 
private int size; // 默认为0

public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}

public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 1.传入参数为1到ensureCapacityInternal方法中去
        elementData[size++] = e;           // 8.将元素放入到对应的下标数组中存放。{1，null, 													null, null, null, null, null....}
        return true;
}

private void ensureCapacityInternal(int minCapacity) {
    // 2.将空数组 和 1 传入到 calculateCapacity方法中去
    ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

private static int calculateCapacity(Object[] elementData, int minCapacity) {
    // 3.判断数组是否为空数组，比较后大小后，将minCapacity赋值为10
    // DEFAULT_CAPACITY = 10， minCapacity =1 
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        return Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    
    return minCapacity;
}

private void ensureExplicitCapacity(int minCapacity) {
    // 只做标记
    modCount++;
    // 4.容量是否大于0（10-0=10）
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // oldCapacity = 0
    int oldCapacity = elementData.length;
    // 5.新容量 = 数组长度 + （数组长度转化二机制后有移动一位）-> （0+0=0） 
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // 6.判断：新容量 - 传入的容量值 是否小于0 ->（0-10)>0  
    if (newCapacity - minCapacity < 0)
        // 7.传入的容量值 -> 赋值给新容量
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    // 8.进行扩容技术后，将新的数组赋值给旧的数组，数组容量为10
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
    MAX_ARRAY_SIZE;
}
```

- 对于有参构造方法，初始值为1的时候

```java
transient Object[] elementData = new Object[1]; 
private int size; // 第二个元素时 -> size=1

// 添加第二个元素时
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 1.传入参数为1到ensureCapacityInternal方法中去
        elementData[size++] = e;           // 8.将元素放入到对应的下标数组中存放。{1，null, 													null, null, null, null, null....}
        return true;
}

private void ensureCapacityInternal(int minCapacity) {
    // 2.将数组 和 1 (第二个元素时为2)传入到 calculateCapacity方法中去
    ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

private static int calculateCapacity(Object[] elementData, int minCapacity) {
    // 3.判断数组是否为空数组,不为空，minCapacity 容量为1 (第二个元素时为2)
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        return Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    
    return minCapacity;
}

private void ensureExplicitCapacity(int minCapacity) {
    // 只做标记
    modCount++;
    // 4.容量是否大于0（1-1 =0）> 0 (第二个元素时为2, 2-1>0)
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // oldCapacity = 0
    int oldCapacity = elementData.length;
    // 5.新容量 = 数组长度为1 + （数组长度转化二机制后有移动一位）-> （1+1=2） 
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // 6.判断：新容量 - 传入的容量值 是否小于0 ->（0-10)>0  
    if (newCapacity - minCapacity < 0)
        // 7.传入的容量值 -> 赋值给新容量 
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    // 8.进行扩容技术后，将新的数组赋值给旧的数组，数组容量为2
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
    MAX_ARRAY_SIZE;
}
```

> *结论2：*
>
> - **ArrayList集合数组初始化值为10。** 
>
>   *分析：根据源码分析，在add(E e)方法中，使用了Arrays.copyOf扩容技术, 并且无参构造函数时，添加第一个元素，数组扩容初始化值为10.*
>
> - **ArrayList 有参构造方法中初始容量为1的时候，那么它扩容容量是2。**



编写自定义**ArrayList**的**add方法**。

方法一: **每次容量扩容以2 倍**。如下：

```java
/**
 * 方法一: 实现2倍扩容（当数据容量大的时候，对io操作影响大，不推荐使用）
 *
 * @param e 元素
 */
public void add1(E e){
     if(size == elementData.length){
        int newCapacity = size * 2;
         Object[] newArrays = new Object[newCapacity];
        // 将原来数组的值赋值到新数组里面去
         for(int i=0; i< elementData.length; i++){
             newArrays[i] = elementData[i];
         }
         // 在将新素组赋值给源数组，使源数组时原来容量的2倍
         elementData = newArrays;
     }
     // 从第0个开始
    elementData[size++] = e;
}
```

方法二: 在方法一的基础上优化 ，使用**Arrays.copyOf方法进行扩容**。 如下：       

```java
public void add2(E e){
    if(size == elementData.length){
        int newCapacity = size * 2;
        // 在将新素组赋值给源数组，使源数组时原来容量的2倍
        elementData = Arrays.copyOf(elementData,newCapacity);
    }
    // 从第0个开始
    elementData[size++] = e;
}
```

方法三：模仿原生 ArrayList 底层**每次扩容以1.5 倍**。 如下：    

```java
public void add3(E e){
      ensureExplicitCapacity(size + 1);
      // 从第0个开始
      elementData[size++] = e;
}

private void ensureExplicitCapacity(int minCapacity){
    if(size == elementData.length){
        // 原来数组的容量
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
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
```

> *总结3：*
>
> - **添加元素放入存放到数组中，当数据容量大的时候，对io操作影响大，不推荐使用 size * 2倍**
> - **ArrayList集合底层每次扩容以1.5 倍**。
> - **ArrayList 集合底层实现扩容技术使用的是 Arrays.copyOf方法进行扩容。**
> - **ArrayList  集合底层add方法存在线程安全问题。**



#### 1.1.2 get(int index)

> 实现思路：
>
> 1. 根据下标，获取数组值

查看**ArrayList.class 源码**的**get方法**，如下：

```java
public E get(int index) {
        rangeCheck(index); // 1. 检查是否存在该数组
        return elementData(index); 
}

// 根据下标获取元素
@SuppressWarnings("unchecked")
E elementData(int index) {
    return (E) elementData[index];
}
```

编写自定义**ArrayList **的**get方法**，如下：

```java
public E get(int index) {
        rangeCheck(index);
        E e = (E) elementData[index];
        return e;
}

private void rangeCheck(int index){
    if(index > size || index < 0){
        throw new IndexOutOfBoundsException("数组越界");
    }
}
```



#### 1.1.3 remove(int index)

> 根据下标，删除指定的元素。
>
> * 问题 1：假设A数组为{1，2，3，4}，删除1元素，怎样变成{2，3，4}
>
>   *观察分析:*
>
>   *a. 删除1后，元素位置发生了变化，都往左移动3次。*
>
>   *b.删除后，数组长度-1。*
>
> 实现思路：
>
> 1. 根据下标删除元素，获取向左移动多少位。
>
>    答：移动位数 = 数组长度-1-下标
>
> 2. 使用System.arraycopy 扩容技术。
>
>    复制多少个元素：数组长度-1-下标
>
>    从元素那几个开始复制：要删除的元素下标后一位开始复制：srcPos = index + 1
>
>    元素左移动目标位置：为删除元素位置，index.
>
> 3. 删除数组最后一位

查看**ArrayList.class 源码**的**remove方法**，如下：

```java
public E remove(int index) {
   rangeCheck(index);

   modCount++;
   E oldValue = elementData(index);

   int numMoved = size - index - 1;
   if (numMoved > 0)
       System.arraycopy(elementData, index+1, elementData, index,
                        numMoved);
   elementData[--size] = null; // clear to let GC do its work

   return oldValue;
}
```

编写自定义**ArrayList**的**remove方法**，如下：

```java
public E remove(int index){
   // 使用下标该值是否存在
    E e = this.get(index);
    // 相当于把删除后的后面的元素往前移动了几次
    int numMoved = size - index - 1;
    if(numMoved > 0){
        // 删除原理
        System.arraycopy(elementData,index + 1, elementData, index, numMoved);
    }
    // 将最后一个元素边为空(删除数组最后一位)
    elementData[--size] = null;
    return e;
}
```

> *总结4：*
>
> - **ArrayList 底层删除元素，效率不高，使用的是往左位移。**
> - **ArrayList 读取速度要比写入和删除速度快。**



#### 1.1.4 add(int index, E e)

> 根据下标，添加指定的元素。
>
> - 问题 1：假设A数组为{1，2，3，4}，添加7元素，怎样变成{1,  2,  7,  3，4}
>
>   *观察分析:*
>
>   *a. 添加 7后，元素位置发生了变化，都往右移动2次。*
>
>   *b.添加后，数组长度+1。*
>
> 实现思路：
>
> 1. 验证是否等于数组长度
>
> 2. 添加元素扩容+1
>
> 3. 使用System.arraycopy 扩容技术。
>
>    复制多少个元素：lenght = 数组长度 - 下标 
>
>    从元素那几个开始复制：从添加元素下标的 srcPos = index 
>
>    元素右移动目标位置：index + 1.
>
> 4. 根据下标赋值。
>
> 5. 数组长度加1。

查看**ArrayList.class 源码** 的**add(int index, E e)** 方法，如下：

```java
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
```

编写自定义**ArrayList**的**add(int index, E e)方法**，如下：

```java
/**
 * 方法四: 根据下标，添加元素
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
```

> *总结5：*
>
> - **ArrayList 底层添加元素，效率不高，使用的是往右位移。**



## 2.数组扩容技术

### 2.1 Arrays.copyOf 方法

```java
/**
 * 数组扩容技术一:
 *   根据 Arrarys.copyOf 在原来的数组上进行新数上的扩容。
 */
@Test
public void arrayExpandMethod1(){
    Object[] arrary = {1,2};
    System.out.println("当前数组长度为:" + arrary.length);

    System.out.println("使用Arrarys.copyOf(原来数组，设置数组长度) 进行扩容");
    Object[] newArrary = Arrays.copyOf(arrary, 10);
    System.out.println("当前新数组长度为:" + newArrary.length);
    Arrays.stream(newArrary).forEach(System.out::println);
}
```

### 2.2 System.arraycopy方法

```java
/**
 * 数组扩容技术二:
 */
@Test
public void arrayExpandMethod2(){
    Object[] arrary = {0,1,2,3,4,5,6};
    System.out.println("使用System.arraycopy(源数组，源数组的开始下标, 目标数组, 目标数组的开始下标, 需要扩容后素组的长度) 进行扩容");
    /**
     * @param      src      源数组
     * @param      srcPos   源数组的开始下标
     * @param      dest     目标数组
     * @param      destPos  目标数组的开始下标
     * @param      length   需要扩容后素组的长度
     */
    System.arraycopy(arrary,0, arrary, 3, 3);
    //  A 源数组为{0, 1, 2, 3, 4, 5, 6}，从下标为0 -> 对应值0 开始, 复制的长度为3 （length）
    //  再将它copy 到 B新的数组为{0，1，2， 3， 4，5，6}，从下标为3 -> 对应值3 开始,将源数组进行粘贴
    //  形成的新数组{0，1，2，0，1，2，6}，将原有的 3，4，5 给覆盖掉了。
    System.out.println("当前新数组长度为:" + arrary.length);
    Arrays.stream(arrary).forEach(System.out::print);
}
```



## 3. Vector 和 ArrayList 区别

ArrayList，Vector主要区别为以下几点： 

> 1. Vector是线程安全的，源码中有很多的synchronized可以看出，而ArrayList不是。导致Vector效率无法和ArrayList相比； 
>
> 2. ArrayList和Vector都采用线性连续存储空间，当存储空间不足的时候，ArrayList默认增加为原来的50%，Vector默认增加为原来的一倍； 
>
> 3. Vector可以设置capacityIncrement，而ArrayList不可以，从字面理解就是capacity容量，Increment增加，容量增长的参数。

​          **private** **void** grow(**int**   minCapacity) {                   //   overflow-conscious code                   **int**   oldCapacity = elementData.length;                     **int**   newCapacity = oldCapacity + (oldCapacity >> 1); //扩充的空间增加原来的50%（即是原来的1.5倍）                   **if**   (newCapacity - minCapacity < 0) //如果容器扩容之后还是不够，那么干脆直接将minCapacity设为容器的大小                       newCapacity = minCapacity;                   **if**   (newCapacity - MAX_ARRAY_SIZE > 0) //如果扩充的容器太大了的话，那么就执行hugeCapacity                       newCapacity = hugeCapacity(minCapacity);                   //   minCapacity is usually close to size, so this is a win:                   elementData = Arrays.copyOf(elementData,   newCapacity);               }     