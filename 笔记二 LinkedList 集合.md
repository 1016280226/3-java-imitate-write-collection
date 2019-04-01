笔记二 LinkedList 集合

[TOC]

### 1.LinkedList 底层

> 思考问题：
>
> 1. 我们通常怎么使用LinkedList 的？
>    - 答：我们一般是通过new LinkedLis()。

### 1.1 LinkedList 无参构造方法

LinkedList **无参构造方法**的源码分析，如下：

```java
ublic class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable{

    /** 链表的长度 **/
	transient int size = 0;

	/** 链表的第一个节点 **/
	transient Node<E> first;

	/** 链表的最后一个节点 **/
	transient Node<E> last;
        
	public LinkedList() {
	}
    
     private static class Node<E> {
        E item;
        /** 下一个节点 **/ 
        Node<E> next;
        /**  上一个节点 **/
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
```

>  *结论1：*
>
> - LinkedList 集合 -> 实现了 List -> List 继承了Collection集合
> - LinkedList  无参构造函数，是一个空集合。



## 2. LinkedList 是什么数据结构

**观察源码得出分析问题：**

> 1. 从下面源码中可以从英文意思大致可以看出，LinkedList 是由双链表的数据结构实现的。
>
> - /** 链表的第一个节点 **/
>   transient Node<E> first;
>
> - /** 链表的最后一个节点 **/
>   transient Node<E> last;
> -   /** 下一个节点 **/ 
>           Node<E> next;
>           /**  上一个节点 **/
>           Node<E> prev;
>
>   但是，不足于证明有没有被使用为双向链表。
>
> 2. 从上面的无参构造方法中，并没有看出LinkedList 集合是通过什么样的数据结构实现的，
>
>    所以我们通过调用它常用的方法add(E e)来进行分析。



**问题思考：**

> 1. LInkedList 链表定义 first、last 由什么作用？
>    - 答： 我们首先需要知道链表的数据结构是有哪些特点。
> 2. 链表主要有：节点中的头和尾，节点内容，上一个节点内容。



### 2.1 单链表的数据结构

单链表的数据结构图，如下：

![1554036595154](C:\Users\Calvin\AppData\Roaming\Typora\typora-user-images\1554036595154.png)

> 特征：
>
> - 上一个节点保存下个节点内容
> - 头节点和尾节点，节点主要包含：节点内容和上一个节点内容



### 2.2 双链表的数据结构

![1554106268611](C:\Users\Calvin\AppData\Roaming\Typora\typora-user-images\1554106268611.png)

> 特征:
>
> - 头节点的pre是空的，尾节点的next是空的
>
> - 上一个节点保存下一个节点内容，下一个节点保存上一个节点内容



#### 2.2.1 LinkedList中Add(E e) 方法

LinkedList **add(E e)方法**的源码分析，如下：

```java
/** 链表的长度 **/
transient int size = 0;

/** 链表的第一个节点 (作用：用于查询)**/
transient Node<E> first;

/** 链表的最后一个节点（作用：用于插入）**/
transient Node<E> last;

private static class Node<E> {
        E item;
        /** 保存下一个节点 **/ 
        Node<E> next;
        /** 保存上一个节点 **/
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
}

/**
 * 添加元素到链表中去
 * @param e 元素
 */
public boolean add(E e) {
      linkLast(e);
      return true;
}
  
/**
 * 在链表的最后一个节点，添加新元素
 * @param e 元素
 */
void linkLast(E e) {
    // 1.将最后一个节点作为倒数第二个节点
    final Node<E> l = last;
    // 2.将新节点作为最后的节点
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    // 3.如果倒数第二个节点为空，说明了当前没有节点，所以设置新节点为第一个节点。
    if (l == null)
        first = newNode;
    else
        // 否则，倒数第二个节不为空，将新节点保存在倒数第二个节点的下个（next）节点中。
        l.next = newNode;
    // 4.链表长度+1
    size++;
    modCount++;
}
```

![1554108823946](C:\Users\Calvin\AppData\Roaming\Typora\typora-user-images\1554108823946.png)

> 实现思路：
>
> - 1. 将插入的节点放入到最后节点中。
> - 2. 将最后一个节点作为倒数第二个节点。
> - 3. 判断：如果倒数第二个节点点为空，说明当前没有节点，所以设置新节点作为第一个节点。
> - 4. 否则，如果存在第一个节点，将新的节点保存在倒数第二个节点的下个节点中去。
> - 5. 最后，链表长度加 + 1。

自定义LinkedList类中 **add(E e)方法**的，如下：

```java
/**
 * 实现思路:
 * 1. 将插入的元素节点放入到最后节点中。
 * 2. 将最后一个节点作为倒数第二个节点。
 * 3. 如果倒数第二个为空，说明当前没有节点，所以设置新节点作为第一个节点。
 * 4. 否则，如果存在第一个节点，将新的节点保存在倒数第二个节点的下个节点中去。
 * 5. 最后，链表长度加 + 1。
 * @param e 元素
 * @return
 */
@Override
public boolean add(E e) {
    Node<E> last2 = last;
    Node<E> newNode = new Node(last2, e, null);
    last = newNode;
    if(last2 == null){
        first = last;
    }else{
        last2.next = newNode;
    }
    size ++;
    return true;
}
```

> *总结2：*
>
> - 链表的插入是非常快的。



#### 2.2.2 LinkedList中get(int index) 方法

问题思考：

> 编写完添加元素后，我们需要思考怎么样根据下标获取元素。

LinkedList **get(int index) 方法**的源码分析，如下：

```java
public E get(int index) {
       // 1.根据下标检查元素是否越界。
       checkElementIndex(index);
       // 2.根据下标，查询对应的节点，返回指定的元素。
       return node(index).item;
}

private void checkElementIndex(int index) {
    if (!isElementIndex(index))
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}


Node<E> node(int index) {
    // 1.使用了二分法：小于中间的数 index < (size >> 1)
    if (index < (size >> 1)) {
        // 2.第一个节点开始
        Node<E> x = first;
        // 3.根据头节点，查询与index下标节点的上一个节点（根据链表的特性：上一个节点保存下一个节点）
        for (int i = 0; i < index; i++)
            // 4.从头节点开始，查询到头节点next下一个节点，一直往下找直到i<index时。
            x = x.next;
        // 5. 返回元素
        return x;
    } else {
        // 1.使用了二分法：大于中间的数
        // 2.从最后节点往上找 （根据双向链表的特性：下一个节点保存上一个节点）
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        // 3.最用返回想要的元素。
        return x;
    }
}
```



> 实现思路：
>
> - 1. 验证不能超过链表的长度。
> - 2. 使用二分法进行进行查询。
>      - 小于二分法的中间数的，从头节点往下找 （根据链表的特性：上一个节点保存下一个节点））
>      - 大于二分法的中间数的，从尾节点往上找 （根据双链表的特性：下一个节点保存上一个节点
> - 3. 最后返回元素。

自定义LinkedList 实现**get(int index) 方法**的源码分析，如下：

```java
 @Override
 public E get(int index) {
     if (index < 0 || index > size-1){
         throw new IndexOutOfBoundsException("查询下标查过链表的查询范围");
     }
     if (index < (size >> 1)) {
         Node<E> firstNode = this.first;
         for(int i=0; i < index; i++){
             firstNode = firstNode.next;
         }
         return firstNode.item;
     }else{
         Node<E> lastNode = this.last;
         for(int i = size - 1;  i > index ; i--){
             lastNode = lastNode.pre;
         }
         return lastNode.item;
     }
 }
```

> *总结3：*
>
> - 链表的查询非常慢，他的查询O(n)复杂度。从头节点开始查询。



#### 2.2.3 LinkedList中remove(int index) 方法

问题思考：

> 编写完获取元素后，我们需要思考怎么根据下标删除元素。

LinkedList **remove(int index) 方法**的源码分析，如下：

```java
public E remove(int index) {
    // 1. 检验链表是否超过链表长度  
    checkElementIndex(index);
    // 2.找到对应的节点，传入到unlink()方法
    return unlink(node(index));
}

E unlink(Node<E> x) {
    // assert x != null;
    final E element = x.item;
    final Node<E> next = x.next;
    final Node<E> prev = x.prev;

    // 如果上一个节点为空，说明他是头节点
    if (prev == null) {
        // 将头节点的下一个节点作为头节点，例如：node1-> node2, 删除node1后，node2作为头节点
        first = next;
    } else {
        // 不为空，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改上一个节            next节点 例如：node1 -> node2 -> node3，删除node2,node1.next = node2.next
        prev.next = next;
        x.prev = null;
    }

    // 如果下一个节点为空，说明是尾节点
    if (next == null) {
        // 将当前删除节点的上一个节点作为尾节点，例如：node1-> node2->node3, 删除node3后，node2作为			  尾节点
        last = prev;
    } else {
        // 不为空，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改下一个节            prev节点 例如：node1 -> node2 -> node3，删除node2,node3.prev = node1.next
        next.prev = prev;
        x.next = null;
    }
    // 将删除元素设置为空，
    x.item = null;
    // 将链表长度减一
    size--;
    modCount++;
    return element;
}
```

![1554109270721](C:\Users\Calvin\AppData\Roaming\Typora\typora-user-images\1554109270721.png)

> 实现思路：
>
> - 1. 检验链表是否超过链表长度。  
> - 2. 根据下标的查询节点。
>
> - 3. 判断删除的节点是头节点还是尾节点。
>
>   - 如果为头节点，将头节点的下一个节点作为头节点
>   - 如果为尾节点，将尾节点的上一个节点作为尾节点
>
> - 4. 如果不是头节点，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改上一个节next节点
>
> - 5. 如果不是尾节点，不为空，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改下一个节prev节点
>
> - 6. 将链表长度减一。

自定义LinkedList 实现**remove(int index) 方法**的源码分析，如下：

```java
/**
 * 1. 检验链表是否超过链表长度。
 * 2. 根据下标的查询节点。
 * 3. 判断删除的节点是头节点还是尾节点。
 *  - 如果为头节点，将头节点的下一个节点作为头节点
 *  - 如果为尾节点，将尾节点的上一个节点作为尾节点
 * 4. 如果不是头节点，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改上一个		    节next节点
 * 5. 如果不是尾节点，不为空，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修			改下一个节prev节点
 * 6. 将链表长度减一。
 * @param index
 * @return
 */
@Override
public E remove(int index) {
    if (index < 0 || index > size-1){
        throw new IndexOutOfBoundsException("查询下标查过链表的查询范围");
    }
    Node<E> deleteNode = node(index);
    if(deleteNode.pre == null){
        first =  deleteNode.next;
    }else{
        deleteNode.pre.next = deleteNode.next;
        deleteNode.pre = null;
    }
    if(deleteNode.next == null){
        last = deleteNode.pre;
    }else{
         deleteNode.next.pre = deleteNode.pre;
         deleteNode.next = null;
    }
    E e = deleteNode.item;
    deleteNode.item = null;
    size --;
    return e;
}
```

> *总结 4*
>
> - 链表删除速度相对于添加来说相对较慢，因为它需要先查询出来，后进行删除。



#### 2.2.4 LinkedList中add(int index, E e) 方法

LinkedList **add(int index, E e)方法**的源码分析，如下：

```java
public void add(int index, E element) {
        // 1. 验证链表是否超过链表长度
        checkPositionIndex(index);
        // 2.下标 == 链表长度，表示在最后插入新的节点
        if (index == size)
            linkLast(element);
        else
        // 3.否则，在原来的节点上插入替换元素。    
            linkBefore(element, node(index));
}

/**
 * 在链表最后插入
 */
void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
/**
 * 在链表之前插入过的地方插入。
 */
void linkBefore(E e, Node<E> succ) {
    // 4.查询出来节点的上一个节点
    final Node<E> pred = succ.prev;
    // 5.构建新节点：将原来节点的上一个节点作为新节点的上一个节点，放入元素，将原来节点作为下一个节点。
    final Node<E> newNode = new Node<>(pred, e, succ);
    // 6.将原来节点的上一个节点为新节点。
    succ.prev = newNode;
    // 7.如果原来的节点为的上一个节点为null，说明改的是头节点
    if (pred == null)
        // 再将新节点作为头节点
        first = newNode;
    else
        // 否则，上一个节点的下一个节作为新节点
        pred.next = newNode;
    // 8.链表长度 +1
    size++;
    modCount++;
}
```



![1554120513470](C:\Users\Calvin\AppData\Roaming\Typora\typora-user-images\1554120513470.png)

> 实现思路：
>
> - 1. 验证链表是否超过链表长度。
> - 2. 判断当前节点是再最后插入, 还是再之前插入。
> - 3. 如果在之前的下标的节点中插入的.
>      - 修改上一个节点的next为新插入的新节点
>      - 修改原来的节点的prev为新插入的新节点
> - 4. 链表长度 + 1.

自定义LinkedList 实现**add(int index, E e)方法**的源码分析，如下：

```java
/**
 * 实现思路：
 *
 * 1. 验证链表是否超过链表长度。
 * 2. 判断当前节点是再最后插入, 还是再之前插入。
 * 3. 如果在之前的下标的节点中插入的.
 *    - 修改上一个节点的next为新插入的新节点
 *    - 修改原来的节点的prev为新插入的新节点
 * 4. 链表长度 + 1.
 * @param index
 * @param element
 */
@Override
public void add(int index, E element) {
    if (index < 0 || index > size-1){
        throw new IndexOutOfBoundsException("查询下标查过链表的查询范围");
    }
    if(index == size){
        add(element);
    }else{
        Node<E> oldNode = node(index);
        Node<E> prevNode = oldNode.prev;
        Node newNode = new Node(prevNode, element, oldNod
        if(oldNode.prev == null){
            first = newNode;
        }else{
            prevNode.next = newNode;
        }
        oldNode.prev = newNode;
        size++;
    }
}
```

> *总结 5：*‘
>
> - 链表中的元素是可以重复的插入。
> - 链表是有序的。



## 3. ArrayList 和  LinkedList 区别

> - ArrayList 查询（O(0)）速度要比 LinkedList查询（O(n)） 速度要快。
> - ArrayList 添加（O(n)）速度要比LinkedList添加（O(0)）速度要慢。
> - ArrayList 和 LinkedList 是有序的。
> - ArrayList 和 LinkedList 元素可以重复插入。
> - ArrayList 底层是实现是通过数组，LinkedList 底层是实现是通过链表。

