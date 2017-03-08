package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header;
    private int size;

    public CustomLinkedList() {
        this.header = new ListNode<E>();
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        E value = (E) o;
        ListNode<E> tmp = header.getNext();
        while (tmp.getNext() != null) {
            if (tmp.getElement().equals(value))
                return true;
            tmp = tmp.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator(header);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> tmp = header;
        while (tmp.getNext() != null) {
            tmp = tmp.getNext();
        }
        ListNode<E> newElement = new ListNode<E>();
        newElement.setElement(e);
        newElement.setPrev(tmp);
        //newElement.setNext(null);
        tmp.setNext(newElement);
        tmp.getNext().setNext(null);
        size ++;
        return  true;
    }

    @Override
    public boolean remove(Object o) {

        if (! this.contains(o)) return false;
        ListNode tmp = header.getNext();
        while (! tmp.getElement().equals(o)) {
            tmp = tmp.getNext();
            if (tmp.getNext() == null) return  false;
        }
        while (tmp.getNext() != null) {
            tmp.setElement(tmp.getNext().getElement());
            tmp = tmp.getNext();
        }
        tmp.setNext(null);
        this.size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] collect = c.toArray();
        for (Object e: collect) {
            if (! this.contains(e))
                return false;
        }
        return true;
    }

    @Override
    public void clear() {
        ListNode tmp = header.getNext();
        while (tmp.getNext() != null) {
            tmp.getPrev().setElement(null);
            tmp.getPrev().setPrev(null);
            tmp.getPrev().setNext(null);
            tmp = tmp.getNext();
        }
        tmp.setPrev(null);
        tmp.setElement(null);
        this.size = 0;
    }

    @Override
    public E get(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        E el = (E) o;
        ListNode tmp = header.getNext();
        while (tmp.getNext() != null) {
            if (tmp.getElement().equals(el))
                return index;
            tmp = tmp.getNext();
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] objects = c.toArray();
        for (Object o: objects) {
            E el = (E) o;
            this.add(el);
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
