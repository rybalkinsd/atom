package ru.atom.list;

import java.util.*;


public class CustomLinkedList<E> implements List<E> {

    private int size = 0;
    private ListNode<E> head;
    private ListNode<E> tail;

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
        ListNode<E> ptr = head;
        while (ptr != null) {
            if (ptr.value.equals(o)) {
                return true;
            }
            ptr = ptr.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private ListNode<E> ptr = head;

            @Override
            public boolean hasNext() {
                return ptr != null;
            }

            @Override
            public E next() {
                if (ptr == null) {
                    throw new NoSuchElementException();
                }
                E nextVal = ptr.value;
                ptr = ptr.next;
                return nextVal;
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (isEmpty()) {
            head = new ListNode<>(e, null, null);
            tail = head;
        } else {
            tail.next = new ListNode<>(e, tail, null);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        } else if (head.value.equals(o)) {
            head = head.next;
            size--;
            if (size == 0) {
                tail = null;
            }
            return true;
        }
        ListNode<E> ptr = head;
        while (ptr.next != null) {
            if (ptr.next.value.equals(o)) {
                if (tail == ptr.next) {
                    tail = tail.prev;
                }
                ptr.next = ptr.next.next;
                if (ptr.next.next != null) {
                    ptr.next.next.prev = ptr;
                }
                size--;
                return true;
            }
            ptr = ptr.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }
        return ptr.value;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> ptr = head;
        for (int i = 0; i < size; i++) {
            if (ptr.value.equals(o)) {
                return i;
            }
            ptr = ptr.next;
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
                return false;
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
