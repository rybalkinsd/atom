package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> first;
    private ListNode<E> last;
    private int size;

    public CustomLinkedList() {
        size = 0;
        first = null;
        last = null;
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
        ListNode<E> node = first;
        while (node != null) {
            if (node.getValue().equals(o))
                return true;
            node = node.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomLinkedListIterator<E>(first);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<>();
        newNode.setValue(e);
        newNode.setPrev(last);
        if (last == null) first = newNode;
        else last.setNext(newNode);
        last = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = first;
        while (node != null) {
            if (!node.getValue().equals(o)) {
                node = node.getNext();
            }
            if (node.getNext() != null)
                node.getNext().setPrev(node.getPrev());
            if (node == first)
                first = node.getNext();
            else node.getPrev().setNext(node.getNext());
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e))
                return false;
        }
        return true;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        ListNode<E> node = first;
        while (index-- > 0)
            node = node.getNext();
        return node.getValue();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> node = first;
        int result = 0;
        while (node != null) {
            if (node.getValue().equals(o)) return result;
            result++;
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) add(e);
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
