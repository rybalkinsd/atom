package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Collections;


public class CustomLinkedList<E> implements List<E> {
    private int size = 0;
    private ListNode<E> header = new ListNode<E>();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.next().equals(o)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Iterator<E> iterator() {
        if (isEmpty()) {
            return Collections.<E>emptyList().iterator();
        }

        return new Iterator<E>() {
            ListNode<E> current = header;

            @Override
            public boolean hasNext() {
                return current.next != header;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    current = current.next;
                    return current.element;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> el = new ListNode<E>();
        el.element = e;
        if (size == 0) {
            el.next = header;
            el.prev = header;
            header.next = el;
            header.prev = el;
        } else {
            header.prev.next = el;
            el.prev = header.prev;
            header.prev = el;
            el.next = header;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = header.next;
        while (current.next != header) {
            if (current.element.equals(o)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        header.next = header;
        header.prev = header;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int count = 0;
        Iterator it = iterator();
        while (it.hasNext() && count != index) {
            count++;
            it.next();
        }
        return (E)it.next();
    }

    @Override
    public int indexOf(Object o) {
        Iterator it = iterator();
        int index = 0;
        while (it.hasNext()) {
            if (it.next().equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            throw new NullPointerException();
        }
        for (E e: c) {
            add(e);
        }
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
