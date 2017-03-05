package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private int len;
    private ListNode<E> first;
    private ListNode<E> last;

    public CustomLinkedList() {
        len = 0;
        first = null;
        last = null;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public boolean isEmpty() {
        return len == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> node = first;
        while (node != null) {
            if (node.element.equals(o)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomLinkedListIterator<E>(first);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<E>();
        newNode.element = e;
        newNode.prev = last;
        if (last == null) first = newNode;
        else last.next = newNode;
        last = newNode;
        len++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = first;
        while (node != null) {
            if (!node.element.equals(o)) {
                node = node.next;
                continue;
            }
            removeNode(node);
            return true;
        }
        return false;
    }
    
    private boolean removeNode(ListNode<E> node) {
        if (node.next != null) node.next.prev = node.prev;
        if (node == first) first = node.next;
        else node.prev.next = node.next;
        len--;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        len = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= len) throw new IndexOutOfBoundsException();
        ListNode<E> node = first;
        while (index-- > 0) node = node.next;
        return node.element;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> node = first;
        int result = 0;
        while (node != null) {
            if (node.element.equals(o)) return result;
            result++;
            node = node.next;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) add(e);
        return true;
    }






    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */

    /**
     * Do not implement
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public void add(int index, E element) {
    }

    /**
     * Do not implement
     */
    @Override
    public E remove(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Do not implement
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public E set(int index, E element) {
        return null;
    }
}
