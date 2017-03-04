package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.atom.list.ListNode;

public class CustomLinkedList<E> implements List<E> {
    public ListNode<E> head;
    public int listSize = 0;
    private Class<E> type = E;

    @Override
    public int size() {
        return this.listSize;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null || this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null || this.isEmpty()) return false;
        if (!(type.isInstance(o))) {
            return false;
        }
        try {
            E e = (E) o;
        } catch (Exception exc) {
            return false;
        }

        ListNode<E> i = this.head;
        while (i.next != null) {
            if (i.elem.equals(e)) {
                return true;
            }
            i = i.next;
        return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        throw new NotImplementedException();
    }

    @Override
    public boolean add(E e) {
        if (this.isEmpty()) {
            this.head = new ListNode<>(e);
            this.listSize += 1;
            return true;
        } else {
            ListNode<E> i = this.head;
            while (i.next != null) {
                i = i.next;
            }
            i.next = new ListNode<E>(e);
            this.listSize += 1;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        this.listSize = 0;
        this.head = null;
    }

    @Override
    public E get(int index) {
        if (index >= this.size()) {
            return null;
        };
        int i = 0;
        ListNode<E> tmp = this.head;
        while ((i <= index) && (tmp.next != null) ) {
            if (i == index) {
                return tmp.elem;
            }
            i += 1;
            tmp = tmp.next;
        }
        return tmp.elem;
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new NotImplementedException();
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
