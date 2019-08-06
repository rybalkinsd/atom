package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private int size;
    private ListNode<E> sentinel;

    public CustomLinkedList() {
        size = 0;
        sentinel = new ListNode<>(null, null, null);
        sentinel.setPrev(sentinel);
        sentinel.setNext(sentinel);
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
        ListNode<E> ptr = sentinel.getNext();
        while (ptr != sentinel) {
            if (ptr.getValue().equals(o)) {
                return true;
            }
            ptr = ptr.getNext();
        }
        return false;
    }

    private class CustomIterator implements Iterator<E> {
        private ListNode<E> current;

        CustomIterator() {
            current = sentinel.getNext();
        }

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E nextItem = current.getValue();
            current = current.getNext();
            return nextItem;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public boolean add(E e) {
        size++;
        ListNode<E> newNode = new ListNode<>(sentinel.getPrev(), e, sentinel);
        sentinel.getPrev().setNext(newNode);
        sentinel.setPrev(newNode);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> ptr = sentinel.getNext();
        while (ptr != sentinel && !ptr.getValue().equals(o)) {
            ptr = ptr.getNext();
        }
        if (ptr == sentinel) {
            return false;
        }
        size--;
        ptr.getPrev().setNext(ptr.getNext());
        ptr.getNext().setPrev(ptr.getPrev());
        return true;
    }

    @Override
    public void clear() {
        size = 0;
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E target = null;
        for (E element : this) {
            if (index == 0) {
                target = element;
                break;
            }
            index--;
        }
        return target;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (E element : this) {
            if (element.equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
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
