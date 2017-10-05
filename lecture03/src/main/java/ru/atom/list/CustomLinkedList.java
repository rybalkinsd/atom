package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> next;
    private ListNode<E> prev;

    @Override
    public int size() {
        ListNode<E> current = new ListNode<E>();
        current = next;
        int count = 0;
        while (current != null) {
            current = current.prev;
            count++;
        }
        return (count);
    }

    @Override
    public boolean isEmpty() {
        return next.next == next;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> current = next;
        E obj = (E) o;
        while (current != null) {
            if (current.object.equals(obj)) {
                return true;
            }
            current = current.prev;
        }
        return (false);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E result = current.object;
                current = current.prev;
                return result;
            }

            ListNode<E> current = next;
        };
    }

    @Override
    public boolean add(E e) {

        ListNode<E> current = new ListNode<E>();
        if (next == null) {
            next = current;
            prev = current;
        } else {
            prev.prev = current;
            prev = current;
        }
        current.object = e;
        return true;

    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = next;
        E obj = (E) o;
        while (current != null) {
            if (current.object.equals(obj)) {
                if (current.next != null) {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    current.next = current.prev = null;
                    current.object = null;
                    if (current.prev == null) {
                        prev = current.next;
                    }
                } else {
                    next = next.prev;
                    current.next = current.prev = null;
                    current.object = null;
                    if (next == null) {
                        prev = null;
                    }
                }
                return true;
            }
            current = current.prev;
        }
        return false;
    }

    @Override
    public void clear() {
        ListNode<E> current = next;
        while (current != null) {
            remove(current.object);
            current = next;
        }
    }

    @Override
    public E get(int index) {
        ListNode<E> current = next.prev;
        while (current != next && index > 0) {
            current = current.prev;
            index--;
        }
        return current != next ? current.object : null;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> current = next;
        E obj = (E) o;
        int index = 0;
        while (current != null) {
            if (current.object.equals(obj)) {
                return index;
            }
            current = current.prev;
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = true;
        for (E obj : c) {
            flag &= add(obj);
        }
        return flag;
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
