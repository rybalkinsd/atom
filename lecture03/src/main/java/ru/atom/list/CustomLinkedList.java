package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> first;

    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            ListNode<E> cur = first;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public E next() {
                E val = cur.getValue();
                cur = cur.getNext();
                return val;
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> temp = new ListNode<>(e);
        if (first == null) {
            first = temp;
        } else {
            ListNode<E> last = first;
            while (last.hasNext()) last = last.getNext();
            last.setNext(temp);
            temp.setPrev(last);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (first == null) return false;
        ListNode<E> toDelete = first;
        if (o == null)
            while (toDelete.hasNext() && !(toDelete.getValue() == null)) toDelete = toDelete.getNext();
        else
            while (toDelete.hasNext() && !o.equals(toDelete.getValue())) toDelete = toDelete.getNext();

        if ((o != null && toDelete.getValue().equals(o)) || (toDelete.getValue() == null && o == null)) {
            if (toDelete.hasNext()) {
                toDelete.getNext().setPrev(toDelete.getPrev());
            }
            if (toDelete.hasPrev()) {
                toDelete.getPrev().setNext(toDelete.getNext());
            } else {
                first = toDelete.getNext();
                first.setPrev(null);
            }
            size--;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (first == null || index < 0 || index >= size) throw new IndexOutOfBoundsException();
        ListNode<E> cur = first;
        for (int i = 0; i < index; i++)
            cur = cur.getNext();
        return cur.getValue();
    }

    @Override
    public int indexOf(Object o) {
        if (first == null) return -1;
        ListNode<E> cur = first;
        if (o == null) {
            for (int i = 0; cur != null; cur = cur.getNext())
                if (cur.getValue() == null) return i;
        } else {
            for (int i = 0; cur != null; cur = cur.getNext())
                if (o.equals(cur.getValue())) return i;
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
