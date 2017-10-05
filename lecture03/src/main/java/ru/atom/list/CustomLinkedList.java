package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head;
    private int size = 0;

    public CustomLinkedList() {
        head = new ListNode<>();
        head.setNext(head);
        head.setPrev(head);
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E v : this)
            if (v == o)
                return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private ListNode<E> currentNode = head.getNext();

            @Override
            public boolean hasNext() {
                return currentNode != head;
            }

            @Override
            public E next() {
                E result = currentNode.getValue();
                currentNode = currentNode.getNext();
                return result;
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> lastNode = head.getPrev();
        ListNode<E> newNode = new ListNode<>(e, lastNode, head);
        head.setPrev(newNode);
        lastNode.setNext(newNode);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> tmp = head.getNext();
        for (E e : this) {
            if (e == o) {
                tmp.getPrev().setNext(tmp.getNext());
                tmp.getNext().setPrev(tmp.getPrev());
                this.size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        head.setValue(null);
        head.setNext(null);
        head.setPrev(null);
        this.size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> tmp = head.getNext();
        while (tmp != head && index > 0) {
            tmp = tmp.getNext();
            index++;
        }
        return tmp != head ? tmp.getValue() : null;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> tmp = head.getNext();
        int index = 0;
        for (E v : this) {
            if (tmp.getValue() == o) {
                return index;
            }
            tmp = tmp.getNext();
            index++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
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
