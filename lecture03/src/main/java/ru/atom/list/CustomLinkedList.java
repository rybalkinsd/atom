package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> head;
    private int size;

    public CustomLinkedList() {
        head = new ListNode<>();
        size = 0;
    }

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
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(head.getNext());
    }

    private final class Itr implements Iterator<E> {
        private ListNode<E> item;
        private int curPos = 0;

        public Itr(ListNode<E> item) {
            this.item = item;
        }

        @Override
        public boolean hasNext() {
            return curPos < size;
        }

        @Override
        public E next() {
            E value = item.getValue();
            item = item.getNext();
            curPos++;
            return value;
        }
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("This list does not permit null elements");
        }
        ListNode<E> tmp = new ListNode<E>();
        tmp.setValue(e);
        if (head.getPrev() == null) {
            head.setPrev(tmp);
            head.setNext(tmp);
            tmp.setNext(head);
            tmp.setPrev(head);
        } else {
            ListNode<E> oldTail = head.getPrev();
            head.setPrev(tmp);
            oldTail.setNext(tmp);
            tmp.setPrev(oldTail);
            tmp.setNext(head);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("This list does not permit null elements");
        }
        ListNode<E> tmp = head.getNext();
        for (int i = 0; i < size; i++) {
            if (tmp.getValue().equals(o)) {
                ListNode<E> old = tmp.getPrev();
                ListNode<E> next = tmp.getNext();
                old.setNext(next);
                size--;
                return true;
            }
            tmp = tmp.getNext();
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item :c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = new ListNode<>();
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        ListNode<E> tmp = head.getNext();
        for (int i = 0; i < index; i++) {
            tmp = tmp.getNext();
        }
        return tmp.getValue();
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            throw new NullPointerException("This list does not permit null elements");
        }
        ListNode<E> tmp = head.getNext();
        for (int i = 0; i < size; i++) {
            if (tmp.getValue().equals(o)) {
                return i;
            }
            tmp = tmp.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.forEach(this::add);
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
