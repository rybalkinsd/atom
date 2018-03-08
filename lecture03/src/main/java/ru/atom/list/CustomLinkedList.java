package ru.atom.list;

import java.util.*;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header;
    private int size;

    CustomLinkedList() {
        this.header = new ListNode<E>(null, null, null);
        this.header.setNext(this.header);
        this.header.setPrev(this.header);
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> cur = this.header.getNext();
        while (cur != this.header) {
            if (cur.getElement().equals(o))
                return true;
            cur = cur.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            ListNode<E> cur = header.getNext();

            @Override
            public boolean hasNext() {
                return cur != header;
            }

            @Override
            public E next() {
                E curValue = cur.getElement();
                cur = cur.getNext();
                return curValue;
            }
        };
    }


    @Override
    public boolean add(E e) {
        ListNode<E> last = this.header.getPrev();
        ListNode<E> newNode = new ListNode<E>(e, this.header, last.getPrev());
        last.setNext(newNode);
        this.header.setPrev(newNode);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> cur = this.header.getNext();
        while (cur != this.header && !cur.getElement().equals(o)) {
            cur = cur.getNext();
        }
        if (cur != this.header) {
            cur.getPrev().setNext(cur.getNext());
            cur.getNext().setPrev(cur.getPrev());
            this.size--;
            return true;
        } else
            return false;

    }

    @Override
    public void clear() {
        this.header.setPrev(this.header);
        this.header.setNext(this.header);
        this.size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> cur = this.header.getNext();
        int i = 0;
        while (i < index) {
            cur = cur.getNext();
            i++;
        }
        return cur.getElement();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> cur = this.header.getNext();
        int i = 0;
        while (cur != this.header) {
            if (cur.getElement().equals(o))
                return i;
            cur = cur.getNext();
            i++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c)
            add(element);
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
