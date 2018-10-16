package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private int size = 0;
    private ListNode<E> firstNode = null;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public boolean contains(Object o) {

        if (o == null) {
            for (ListNode<E> x = firstNode; x != null; x = x.next) {
                if (x.value == null)
                    return true;
            }
        } else {
            for (ListNode<E> x = firstNode; x != null; x = x.next) {
                if (o.equals(x.value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {

            ListNode<E> currentNode = firstNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public E next() {

                E resValue = currentNode.value;
                currentNode = currentNode.next;
                return resValue;
            }
        };
    }

    @Override
    public boolean add(E e) {

        if (firstNode == null) {
            firstNode = new ListNode<>(null, null, e);
            size++;
            return true;
        }

        // finding last node and add element e to him
        // We look for the last node and add an element there.

        ListNode<E> currentNode;
        ListNode<E> currentNext = firstNode;

        do {
            currentNode = currentNext;
            currentNext = currentNode.next;
        } while (currentNext != null);

        currentNode.next = new ListNode<>(null, currentNode, e);
        size++;

        return true;
    }

    private E unlink(ListNode<E> x) {
        // assert x != null;
        final E element = x.value;
        final ListNode<E> next = x.next;
        final ListNode<E> prev = x.prev;

        if (prev == null) {
            firstNode = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next != null) {
            next.prev = prev;
            x.next = null;
        }

        x.value = null;
        size--;
        return element;
    }


    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> x = firstNode; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (ListNode<E> x = firstNode; x != null; x = x.next) {
                if (o.equals(x.value)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        for (E item : c) {
            add(item);
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
