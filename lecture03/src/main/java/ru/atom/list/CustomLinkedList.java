package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> base;
    private ListNode<E> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return base == null;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            ListNode<E> current = base;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = current.getTmp();
                current = current.getNext();
                return value;
            }
        };

    }

    @Override
    public boolean add(E e) {
        ListNode<E> nov = new ListNode<>(e);

        if (base == null) {
            base = nov;
        } else {
            ListNode<E> last = base;

            while (last.getNext() != null) last = last.getNext();
            last.setNext(nov);
            nov.setPrev(last);
        }
        size++;
        return true;

    }

    @Override
    public boolean remove(Object o) {

        if (base == null) return false;
        if (o == null) return true;

        ListNode<E> deleted = base;

        while (deleted != null) {


            if (deleted.getTmp().equals(o)) {

                if (deleted.getPrev() != null) {

                    deleted.getPrev().setNext(deleted.getNext());
                    deleted.getNext().setPrev(deleted.getPrev());
                    size --;
                } else {

                    if (size != 0) {
                        base = base.getNext();
                        size --;
                        if (size == 0) base = null;
                        else base.setPrev(null);
                    }

                }
                return true;

            }

            deleted = deleted.getNext();
        }

        return false;

    }

    @Override
    public void clear() {
        base = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (base == null || index < 0 || index >= size) throw new IndexOutOfBoundsException();
        ListNode<E> current = base;
        for (int i = 0; i < index; i++) current = current.getNext();
        return current.getTmp();
    }

    @Override
    public int indexOf(Object o) {
        if (base == null) return -1;
        int num = 0;
        if (o != null) {
            for (ListNode<E> current = base; current != null; current = current.getNext(), num++) {
                if (current.getTmp().equals(o)) return num;
            }
        } else {
            for (ListNode<E> current = base; current != null; current = current.getNext(), num++) {
                if (current.getTmp() == null) return num;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        for (E e: c) {
            add(e);
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
