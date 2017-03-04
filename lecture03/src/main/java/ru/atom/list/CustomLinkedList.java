package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> extends ListNode implements List<E> {

    private int size;
    private ListNode<E> header = new ListNode<>();

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
            if (node.getValue() == o) {
                return true;
            } else {
                node = node.getNext();
            }
        }
        return size == 0 ? header.getNext().getValue() == o : false;
    }

    @Override
    public Iterator<E> iterator() {
        if (isEmpty()) {
            return Collections.<E>emptyList().iterator();
        } else {
            return new Iterator<E>() {

                private ListNode currentNode = header;

                @Override
                public boolean hasNext() {
                    return currentNode.getNext() != header;
                }

                @Override
                public E next() {
                    if (hasNext()) {
                        currentNode = currentNode.getNext();
                        return (E) currentNode.getValue();
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }
    }

    @Override
    public boolean add(E e) {
        ListNode<E> nodeToAdd = new ListNode<>();
        nodeToAdd.setValue(e);
        if (size >= 1) {
            nodeToAdd.setNext(header);
            ListNode lastNode = header.getPrev();
            lastNode.setNext(nodeToAdd);
            header.setPrev(nodeToAdd);
            nodeToAdd.setPrev(lastNode);
        } else {
            header.setPrev(nodeToAdd);
            header.setNext(nodeToAdd);
            nodeToAdd.setPrev(header);
            nodeToAdd.setNext(header);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode node = header.getNext();
        while (node != header) {
            if (node.getValue() == o) {
                ListNode prevNode = node.getPrev();
                ListNode nextNode = node.getNext();
                prevNode.setNext(nextNode);
                nextNode.setPrev(prevNode);
                size--;
                return true;
            } else {
                node = node.getNext();
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object i: c) {
            if (!contains(i)) {
                return false;
            }
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
        return (E) header.getPrev().getValue();
    }

    @Override
    public int indexOf(Object o) {
        int potentialIndex = 0;
        ListNode node = header.getNext();
        while (node != header) {
            if (node.getValue() == o) {
                return potentialIndex;
            } else {
                node = node.getNext();
                potentialIndex++;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E i : c) {
            add(i);
        }
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
