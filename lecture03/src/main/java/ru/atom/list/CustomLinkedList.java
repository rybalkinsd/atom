package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    public int size = 0;
    public ListNode<E> header;


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) >= 0);
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            int counter = 0;

            ListNode<E> current = header;

            @Override
            public E next() {
                if ((current == header) && (counter == 0)) {
                    while (current.prev != null) {
                        current = current.prev;
                    }
                }
                E num = current.element;
                current = current.next;
                counter++;
                return num;
            }


            @Override
            public boolean hasNext() {
                return counter != size;
            }

        };
    }

    @Override
    public boolean add(E e) {

        final ListNode<E> l = header;
        final ListNode<E> newNode = new ListNode<>(e, null, l);
        header = newNode;
        if (l == null)
            header = newNode;
        else
            l.next = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {

        for (ListNode<E> temp = header; temp != null; temp = temp.prev) {
            if (o.equals(temp.element)) {
                if (temp == header) {
                    header = header.prev;
                }
                if (temp.prev != null) {
                    temp.prev.next = temp.next;
                }
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                }
                temp.next = null;
                temp.prev = null;
                temp.element = null;
                size--;

            }
        }
        return true;
    }

    public void clear() {
        size = 0;
        header = null;
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        int index = size;
        for (ListNode<E> temp = header; temp != null; temp = temp.prev) {
            index--;
            if (o.equals(temp.element))
                return index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
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
