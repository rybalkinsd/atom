package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {

    ListNode<E> header;
    ListNode<E> prev;
    ListNode<E> next;

    public CustomLinkedList() {
        header = new ListNode<>();
        header.prev = header;
        header.next = header;
    }

    @Override
    public int size() {
        ListNode<E> temp = header;
        int sz = 0;
        while (temp.next != header) {
            sz++;
            temp = temp.next;
        }
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return (header.next == header);
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> temp = header.next;
        E o1 = (E) o;
        while (temp != header) {
            if (o1.equals(temp.value))
                return true;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            ListNode<E> temp = header.next;

            @Override
            public boolean hasNext() {
                return (temp != header);
            }

            @Override
            public E next() {
                E tmp = temp.value;
                temp = temp.next;
                return tmp;
            }
        };

        return it;
    }

    @Override
    public boolean add(E e) {
        ListNode<E> temp = new ListNode<E>(e,header,header.prev);
        header.prev.next = temp;
        header.prev = temp;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> temp = header.next;
        E o1 = (E) o;
        while (temp != header) {
            if (o1.equals(temp.value)) {
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public void clear() {
        header.next = header;
        header.prev = header;
    }

    @Override
    public E get(int index) {
        ListNode<E> temp = header.next;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return temp.value;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> temp = header.next;
        E o1 = (E) o;
        int index = 0;
        while (temp != header) {
            if (o1.equals(temp.value))
                return index;
            else {
                index++;
                temp = temp.next;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E temp : c) {
            add(temp);
        }
        return true;
    }


    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return true;
            }
        }
        return true;
    }

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