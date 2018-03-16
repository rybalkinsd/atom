package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private int size;
    private ListNode<E> head;
    private ListNode<E> zer;

    public CustomLinkedList() {
        this.head = new ListNode<E>();
        this.zer = new ListNode<E>();
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    private class MyIterator implements Iterator<E> {

        ListNode<E> cur;
        boolean f;

        public MyIterator() {
            this.cur = CustomLinkedList.this.zer;
            f = false;// голова списка
        }

        @Override
        public boolean hasNext() {
            //return cur!=zer;
            if (f) {
                return cur != zer;
            } else {
                f = true;
                return CustomLinkedList.this.size != 0;
            }
        }

        @Override
        public E next() {
            E val = cur.getData();
            cur = cur.getNext();
            return val;
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public boolean add(E e) {
        if (size == 0) {
            ListNode<E> nw = new ListNode<E>();
            nw.setData(e);
            nw.setNext(nw);
            nw.setPrev(nw);
            head = nw;
            zer = nw;
        } else {
            ListNode<E> nw = new ListNode<E>();
            nw.setData(e);
            nw.setNext(zer);
            nw.setPrev(head);
            zer.setPrev(nw);
            if (size == 1) {
                zer.setNext(nw);
            }
            head.setNext(nw);
            head = nw;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) return false;
        ListNode<E> del = zer;
        for (int i = 0; i < size; i++) {
            if (del.getData().equals(o)) {
                del.getPrev().setNext(del.getNext());
                del.getNext().setPrev(del.getPrev());
                if (i == 0) {
                    zer = del.getNext();

                }
                if (i == size - 1) {
                    head = del.getPrev();
                }
                size--;
                return true;
            }
            del = del.getNext();

        }

        return false;
    }

    @Override
    public void clear() {
        head = null;
        zer = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (size == 0 || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> curr = zer;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    @Override
    public int indexOf(Object o) {
        if (size == 0) return -1;
        ListNode<E> curr = zer;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(o)) {
                return i;
            }
            curr = curr.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E el : c) {
            add(el);
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
