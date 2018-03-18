package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> list;

    @Override
    public int size() {
        int size = 0;
        ListNode<E> listRef = list;
        while (listRef != null) {
            size++;
            listRef = listRef.getNext();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return list == null;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> listRef = list;

            @Override
            public boolean hasNext() {
                return listRef != null;
            }

            @Override
            public E next() {
                E result = listRef.getValue();
                listRef = listRef.getNext();
                return result;
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<E>();
        newNode.setValue(e);
        if (!isEmpty()) {
            ListNode<E> lastNode = list;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
            lastNode.setNext(newNode);
            newNode.setPrev(lastNode);
        } else
            list = newNode;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> listRef = list;
        while (listRef != null) {
            if (listRef.getValue().equals(o)) {
                ListNode<E> prev = listRef.getPrev();
                ListNode<E> next = listRef.getNext();
                if (prev == null && next == null) {
                    clear();
                } else {
                    if (next != null) {
                        next.setPrev(prev);
                    }
                    if (prev != null) {
                        prev.setNext(next);
                    } else
                        list = next;
                }
                return true;
            }
            listRef = listRef.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        list = null;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(index);
        ListNode<E> listRef = list;
        for (int i = 0; i < index; i++) {
            listRef = listRef.getNext();
        }
        return listRef.getValue();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> listRef = list;
        int idx = 0;
        while (listRef != null) {
            if (listRef.getValue().equals(o))
                return idx;
            listRef = listRef.getNext();
            idx++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E elem : c) {
            add(elem);
        }
        return !c.isEmpty();
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
