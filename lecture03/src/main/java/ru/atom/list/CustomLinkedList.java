package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {
    private int size = 0;
    private ListNode<E> head;


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
        for (E e : this) {
            if (e == o) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> curNode = head;
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter != size;
            }

            @Override
            public E next() {
                E temp = curNode.getElement();
                curNode = curNode.getNext();
                counter++;
                return temp;
            }
        };

    }

    @Override
    public boolean add(E e) {
        if (size == 0) {
            head = new ListNode(e);
        } else {
            ListNode<E> newNode = new ListNode(e, head, head.getPrev());
            head.getPrev().setNext(newNode);
            head.setPrev(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode curNode = head;
        for (E e : this) {
            if (e == o) {
                size--;
                if (curNode == head) {
                    head = curNode.getNext();
                    head.setPrev(curNode.getPrev());
                    curNode.getPrev().setNext(head);
                } else {
                    curNode.getPrev().setPrev(curNode.getNext());
                    curNode.getNext().setPrev(curNode.getPrev());
                }
                return true;
            }
            curNode = curNode.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        head.setElement(null);
        head.setNext(null);
        head.setPrev(null);
        size = 0;
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
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
