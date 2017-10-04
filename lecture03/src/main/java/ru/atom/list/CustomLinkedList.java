package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head;
    private int size;

    CustomLinkedList() {
        head = new ListNode<E>(null,null,null);
        head.next = head.prev = head;
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
        ListNode<E> node = head.next;
        for (int i = 0; i < size; i++) {
            if (node.data == o) {
                return true;
            }
            node = node.next;
        }
        return false;

    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> current = head;

            @Override
            public boolean hasNext() {
                return current.next != head;
            }

            @Override
            public E next() throws IndexOutOfBoundsException {
                E result = current.data;
                if (hasNext()) {
                    current = current.next;
                    return current.data;
                }
                throw new IndexOutOfBoundsException("End of list.");
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node = new ListNode<E>(e, head, head.prev);
        node.prev.next = node;
        node.next.prev = node;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = head.next;
        for (int i = 0; i < size; i++) {
            if (node.data == o) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.next = node.prev = null;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public void clear() {
        for (ListNode<E> node = head.next; node != head; ) {
            node = node.next;
            node.data = null;
            node.next = null;
            node.prev = null;
        }
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> node = head.next;
        int index = -1;
        while (node != head) {
            index++;
            if (node == o) {
                return index;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E el: c) {
            add(el);
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
