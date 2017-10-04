package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E>, Iterable<E> {
    public CustomLinkedList() {
        head = new ListNode<>();
        head.setNext(head);
        head.setPrev(head);
    }

    @Override
    public int size() {
        ListNode<E> tmp = head.next();
        int size = 0;
        while (tmp != head) {
            tmp = tmp.next();
            ++size;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head.next() == head;
    }

    @Override
    public boolean contains(Object o) {
        E item = (E) o;
        ListNode<E> tmp = head.next();
        while (tmp != head) {
            if (tmp.getValue().equals(item)) {
                return true;
            }
            tmp = tmp.next();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return currentNode != head;
            }

            @Override
            public E next() {
                E result = currentNode.getValue();
                currentNode = currentNode.next();
                return result;
            }

            private ListNode<E> currentNode = head.next();
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> lastNode = head.prev();
        ListNode<E> newNode = new ListNode<>(e, lastNode, head);
        head.setPrev(newNode);
        lastNode.setNext(newNode);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E item = (E) o;
        ListNode<E> tmp = head.next();
        while (tmp != head) {
            if (tmp.getValue().equals(item)) {
                tmp.prev().setNext(tmp.next());
                tmp.next().setPrev(tmp.prev());
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        head.setNext(head);
        head.setPrev(head);
    }

    @Override
    public E get(int index) {
        ListNode<E> tmp = head.next();
        while (tmp != head && index > 0) {
            tmp = tmp.next();
            --index;
        }
        return tmp != head ? tmp.getValue() : null;
    }

    @Override
    public int indexOf(Object o) {
        E item = (E) o;
        ListNode<E> tmp = head.next();
        int index = 0;
        while (tmp != head) {
            if (tmp.getValue().equals(item)) {
                return index;
            }
            tmp = tmp.next();
            ++index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = true;
        for (E item : c) {
            flag &= add(item);
        }
        return flag;
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

    private ListNode<E> head;
}
