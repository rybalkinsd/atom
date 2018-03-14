package ru.atom.list;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> begin;
    private ListNode<E> end;
    private int size;

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
        return indexOf(o) >= 0;
    }

    private class ListIter implements Iterator<E> {
        ListNode<E> curElem;

        ListIter(CustomLinkedList<E> list) {
            curElem = list.begin;
        }

        // The next three methods implement Iterator.
        @Override
        public boolean hasNext() {
            return curElem != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E value = curElem.getValue();
            curElem = curElem.getNext();
            return value;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIter(this);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<>(e);
        if (size > 0) {
            newNode.setPrev(end);
            end.setNext(newNode);
            end = newNode;
            size ++;
        } else {
            begin = newNode;
            end = newNode;
            size = 1;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> curElem = begin; curElem != null; curElem = curElem.getNext()) {
                if (curElem.getValue() == null) {
                    unlink(curElem);
                    return true;
                }
            }
        } else {
            for (ListNode<E> curElem = begin; curElem != null; curElem = curElem.getNext()) {
                if (curElem.getValue().equals(o)) {
                    unlink(curElem);
                    return true;
                }
            }
        }
        return false;
    }

    private void unlink(ListNode<E> node) {

        final E element = node.getValue();
        final ListNode<E> next = node.getNext();
        final ListNode<E> prev = node.getPrev();

        if (prev == null) {
            begin = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            end = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        node.setValue(null);
        size--;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0)
            return null;
        ListNode<E> curElem = begin;
        for (int i = 0; i < index; i ++) {
            curElem = curElem.getNext();
        }
        return curElem.getValue();
    }

    @Override
    public int indexOf(Object o) {
        int num = 0;
        if (o != null) {
            for (ListNode<E> curElement = begin; curElement != null; curElement = curElement.getNext(), num++) {
                if (curElement.getValue().equals(o))
                    return num;
            }
        } else {
            for (ListNode<E> curElement = begin; curElement != null; curElement = curElement.getNext(), num++) {
                if (curElement.getValue() == null)
                    return num;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        int amount = c.toArray().length;
        if (amount == 0)
            return false;

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
