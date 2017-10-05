package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> last = null;
    private ListNode<E> first = null;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> current = first;
        for (int i = 0; i < size; i++) {
            if (current.getObject() == (E) o) return true;
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<E> {

        private ListNode<E> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E item = current.getObject();
                current = current.getNext();
                return item;
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node = new ListNode<>();
        node.setObject(e);
        if (last == null) {
            node.setPrev(node);
            first = node;
        } else {
            node.setPrev(last);
            last.setNext(node);
        }
        last = node;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = first;
        for (int i = 0; i < size; i++) {
            if (current.getObject() == (E) o) {

                if (current == first) {
                    first = current.getNext();
                    first.setPrev(first);
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.setNext(null);
                }

                if (current.getNext() == null) {
                    last = current.getPrev();
                    last.setNext(null);
                } else {
                    current.getNext().setPrev(current.getPrev());
                    current.setPrev(null);
                }

                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        CustomIterator iterator = new CustomIterator();
        while (iterator.hasNext()) {
            remove(iterator.next());
        }
    }

    @Override
    public E get(int index) {
        ListNode<E> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) return current.getObject();
            current = current.getNext();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> current = first;
        for (int i = 0; i < size; i++) {
            if (current.getObject() == (E) o) return i;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            add(item);
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
