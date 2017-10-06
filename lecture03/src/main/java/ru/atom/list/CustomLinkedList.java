package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private final ListNode<E> list = new ListNode<E>(null, null, null);

    @Override
    public int size() {
        int size = 0;
        ListNode<E> current = list;
        while (current.getNext() != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (list.getNext() == null);
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> current = list;
        while (current.getNext() != null) {
            current = current.getNext();
            if (current.getObject().equals(o))
                return true;

        }
        return false;

    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<E>(list);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> current = list;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        ListNode<E> newNode = new ListNode<>(current, e, null);
        current.setNext(newNode);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = list;
        boolean toRemove = false;
        while (current.getNext() != null) {
            current = current.getNext();
            if (current.getObject().equals(o)) {
                toRemove = true;
                break;
            }
        }
        if (!toRemove) return false;
        ListNode<E> prev = current.getPrev();
        ListNode<E> next = current.getNext();
        prev.setNext(next);
        if (next != null) {
            next.setPrev(prev);
        }
        return true;
    }

    @Override
    public void clear() {
        list.setNext(null);
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> current = list;
        int count = -1;
        while (current.getNext() != null) {
            current = current.getNext();
            count++;
            if (count == index) {
                return current.getObject();
            }
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        ListNode<E> current = list;
        while (current.getNext() != null) {
            current = current.getNext();
            index++;
            if (current.getObject().equals(o)) return index;

        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) return false;
        for (E object : c) {
            add(object);
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
