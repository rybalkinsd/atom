package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    ListNode<E> node;
    CustomListIterator iterator;

    CustomLinkedList() {
        node = new ListNode<>(null);
        node.setNext(node);
        node.setPrevious(node);
        iterator = new CustomListIterator(node);
    }

    @Override
    public int size() {
        ListNode<E> tempNode = node.getNext();
        int size = 0;
        while (!tempNode.equals(node)) {
            size++;
            tempNode = tempNode.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return node.getNext().equals(node);
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> tempNode = node.getNext();
        while (!tempNode.equals(node)) {
            if (tempNode.getElement().equals(o))
                return true;
            tempNode = tempNode.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return iterator;
    }

    @Override
    public boolean add(E e) {
        if (e == null)
            return false;
        try {
            ListNode<E> tempNode = new ListNode<>(e, node, node.previous);
            node.previous.setNext(tempNode);
            node.setPrevious(tempNode);
            return true;
        } catch (OutOfMemoryError error) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> tempNode = node.getNext();
        while (!tempNode.equals(node)) {
            if (tempNode.getElement().equals(o)) {
                tempNode.getPrevious().setNext(tempNode.getNext());
                tempNode.getNext().setPrevious(tempNode.getPrevious());
                return true;
            }
            tempNode = tempNode.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("get");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("indexOf");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E it : c) {
            if (!add(it))
                return false;
        }
        return true;
    }


    class CustomListIterator implements ListIterator<E> {

        ListNode<E> current;

        CustomListIterator(ListNode<E> node) {
            current = node;
        }

        @Override
        public boolean hasNext() {
            return !(current.getNext().getElement() == null);
        }

        @Override
        public E next() {
            if (!hasNext())
                return null;
            current = current.getNext();
            return current.getElement();
        }

        @Override
        public boolean hasPrevious() {
            return !(current.getPrevious().getElement() == null);
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                return null;
            current = current.getPrevious();
            return current.getElement();
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
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
