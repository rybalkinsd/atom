package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> first;
    private ListNode<E> last;
    private int size = 0;

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
        ListNode<E> curr = first;
        for (int i = 0; i < size; i++) {
            if (curr.getContent().equals(o)) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator<>(this, first);
    }

    @Override
    public boolean add(E e) {
        if (first == null) {
            first = new ListNode<>(e, null);
            last = first;
        } else {
            ListNode<E> newLast = new ListNode<>(e, last);
            last.setNext(newLast);
            last = newLast;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> curr = first;
        for (int i = 0; i < size; i++) {
            if (curr.getContent().equals(o)) {
                ListNode<E> prev = curr.getPrev();
                ListNode<E> next = curr.getNext();
                if (prev == null) {
                    first = next;
                } else {
                    prev.setNext(next);
                }
                if (next == null) {
                    last = prev;
                } else {
                    next.setPrev(prev);
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            return null;
        }
        ListNode<E> curr = first;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getContent();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> curr = first;
        for (int i = 0; i < size; i++) {
            if (curr.getContent().equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E elem : c) {
            add(elem);
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
