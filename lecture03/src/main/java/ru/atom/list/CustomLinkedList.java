package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {


    private ListNode<E> first = null;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> list = first;
        while (list != null) {
            if (list.getElement().equals(o)) {
                return true;
            }
            list = list.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<E>(first);
    }

    @Override
    public boolean add(E e) { //добавляет элемент в конец списка и в случае удачного добавления возвращает true

        if (first == null) {
            first = new ListNode<E>(e, null, null);
            size++;
            return true;
        }
        ListNode<E> list = first;
        ListNode<E> prev = null;
        while (list != null) {
            prev = list;
            list = list.getNext();
        }
        prev.setNext(new ListNode<E>(e, prev, null));
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> list = first;
        while (list != null) {
            if (list.getElement().equals(o)) {
                size --;
                return deleteList(list);
            }
            list = list.getNext();
        }
        return false;
    }

    public boolean deleteList(ListNode<E> list) {
        ListNode<E> prev = list.getPrev();
        ListNode<E> next = list.getNext();
        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> list = first;
        int count = 0;
        while (list != null) {
            if (count == index) {
                break;
            }
            list = list.getNext();
            count++;
        }
        return list.getElement();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
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
