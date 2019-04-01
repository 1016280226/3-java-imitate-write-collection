package com.util.collection.linkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author: Calvin
 * @Date: 2019/3/31 19:41
 */
public class CusotmerLinkedList<E> implements List<E> {
    /** 初始化链表的长度**/
    private int size;

    /** 初始化链表的头节点**/
    private Node<E> first;

    /** 初始化链表的尾节点**/
    private Node<E> last;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

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

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    /**
     * 实现思路:
     *   1. 验证不能超过链表的长度。
     *   2. 使用二分法进行进行查询。
     *      - 小于二分法的中间数的，从头节点往下找 （根据链表的特性：上一个节点保存下一个节点））
     *      - 大于二分法的中间数的，从尾节点往上找 （根据双链表的特性：下一个节点保存上一个节点）
     *  3. 最后返回元素。
     * @param index
     * @return
     */
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
                lastNode = lastNode.prev;
            }
            return lastNode.item;
        }
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

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
            Node newNode = new Node(prevNode, element, oldNode);

            if(oldNode.prev == null){
                first = newNode;
            }else{
                prevNode.next = newNode;
            }
            oldNode.prev = newNode;
            size++;
        }
    }

    private Node<E> node(int index){
        if (index < (size >> 1)) {
            Node<E> firstNode = this.first;
            for(int i=0; i < index; i++){
                firstNode = firstNode.next;
            }
            return firstNode;
        }else{
            Node<E> lastNode = this.last;
            for(int i = size - 1;  i > index ; i--){
                lastNode = lastNode.prev;
            }
            return lastNode;
        }
    }

    /**
     * 1. 检验链表是否超过链表长度。
     * 2. 根据下标的查询节点。
     * 3. 判断删除的节点是头节点还是尾节点。
     *  - 如果为头节点，将头节点的下一个节点作为头节点
     *  - 如果为尾节点，将尾节点的上一个节点作为尾节点
     * 4. 如果不是头节点，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改上一个节next节点
     * 5. 如果不是尾节点，不为空，当前删除节点的上一个节点 和 当前删除节点的下一个节点进行关联，也就是修改下一个节prev节点
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
        if(deleteNode.prev == null){
            first =  deleteNode.next;
        }else{
            deleteNode.prev.next = deleteNode.next;
            deleteNode.prev = null;
        }
        if(deleteNode.next == null){
            last = deleteNode.prev;
        }else{
            deleteNode.next.prev = deleteNode.prev;
            deleteNode.next = null;
        }
        E e = deleteNode.item;
        deleteNode.item = null;
        size --;
        return e;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * 1. 创建节点
     * @param <E>
     */
    private static class Node<E>{
        /** 用于保存节点元素**/
        E item;
        /** 保存上一个节点**/
        Node<E> prev;
        /** 保存下一个节点**/
        Node<E> next;

        Node(Node<E> prev, E element, Node<E> next){
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

}
