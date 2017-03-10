package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E> {
    private int size;
    private ListNode<E> header = new ListNode<E>();

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
        ListNode node = header.getNext();
        while (node != header) {
            if (node.getElement().equals(o)) return true;
            node = node.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        if (isEmpty()) {
            return Collections.<E>emptyList().iterator();
        }
        return new Iterator<E>() {
            ListNode<E> currentNode = header;

            @Override
            public boolean hasNext() {
                return currentNode.getNext() != header;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    currentNode = currentNode.getNext();
                    return currentNode.getElement();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode newListNode = new ListNode<E>();
        newListNode.setElement(e);
        if (size == 0) {
            header.setNext(newListNode);
            header.setPrev(newListNode);
            newListNode.setPrev(header);
            newListNode.setNext(header);
        } else if (size > 0) {
            ListNode last = header.getPrev();
            header.setPrev(newListNode);
            last.setNext(newListNode);
            newListNode.setPrev(last);
            newListNode.setNext(header);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode node = header.getNext();
        while (node != header) {
            if (node.getElement().equals(o)) {
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object i : c) {
            if (!contains(i)) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        header.setNext(null);
        header.setPrev(null);
        size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> node = header.getNext();
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getElement();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        ListNode node = header;
        while (node.getNext() != header) {
            index++;
            if (node.getElement().equals(o)) return index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E i : c) add(i);
        return true;
    }






    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */

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
