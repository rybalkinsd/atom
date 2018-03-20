package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head;
    private int size;

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
        return new LinkedListIterator<>(head);
    }

    @Override
    public boolean add(E e) {
        if (head == null) {
            head = new ListNode<>(e, null, null);
        } else {
            ListNode<E> tmp = head;
            while (tmp.getNext() != null)
                tmp = tmp.getNext();
            tmp.setNext(new ListNode<>(e, tmp, null));
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> tmp = head;
        for (int i = 0; i < size; i++) {
            if (tmp.getItem().equals(o)) {
                if (tmp.getNext() == null) {
                    ListNode<E> prev = tmp.getPrev();
                    prev.setNext(null);
                    tmp.setPrev(null);
                } else if (tmp.getPrev() == null) {
                    ListNode<E> next = tmp.getNext();
                    head = next;
                    next.setPrev(null);
                    tmp.setNext(null);
                    tmp.setItem(null);
                } else {
                    ListNode<E> prev = tmp.getPrev();
                    ListNode<E> next = tmp.getNext();
                    prev.setNext(next);
                    next.setPrev(prev);
                    // It's unnecessary, but explicit
                    tmp.setNext(null);
                    tmp.setPrev(null);
                    tmp.setItem(null);
                }
                size--;
                return true;
            }
            tmp = tmp.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        for (ListNode<E> tmp = head; head != null; ) {
            ListNode<E> next = tmp.getNext();
            tmp.setNext(null);
            tmp.setPrev(null);
            tmp.setItem(null);
            tmp = next;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException("Index should be between 0 and size");
        ListNode<E> tmp = head;
        for (int i = 0; i < index; i++) tmp = tmp.getNext();
        return tmp.getItem();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (ListNode<E> tmp = head; tmp != null; tmp = tmp.getNext()) {
            if (tmp.getItem().equals(o))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c)
            add(item);
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
