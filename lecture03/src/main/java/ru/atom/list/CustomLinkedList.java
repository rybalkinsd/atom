package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> extends ListNode<E> implements List<E> {

    private ListNode<E> head;
    private int size;

    public CustomLinkedList() {
        size = 0;
        head = new ListNode<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        } else {
            ListNode<E> node = head.next;
            while (node != null && node != head) {
                if (node.element.equals(o))
                    return true;
                else {
                    node = node.next;
                }
            }
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomLinkedListIterator(head.next);
    }

    private final class CustomLinkedListIterator implements Iterator<E> {

        private int currentIndex = 0;
        private ListNode<E> node;


        public CustomLinkedListIterator(ListNode<E> node) {
            this.node = node;
        }

        @Override
        public E next() {
            E element = node.element;
            node = node.next;
            currentIndex++;
            return element;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node = new ListNode<>();
        node.element = e;
        node.next = head;
        node.prev = head.prev;

        if (head.prev != null) {
            node.next = head;
            node.prev = head.prev;
            head.prev.next = node;
            head.prev = node;
        } else {
            node.next = head;
            node.prev = head;
            head.prev = node;
            head.next = node;
        }
        size ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = head.next;
        while (node != null && node != head) {
            if (node.element.equals(o)) {
                ListNode<E> oldNode = node.prev;
                ListNode<E> newNode = node.next;
                oldNode.next = newNode;
                //node.prev.next = node.next;
                //node.next.prev = node.prev;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
    }

    @Override
    public E get(int index) {
        ListNode<E> node = head.next;
        int count = 0;
        while (node != null && node != head) {
            if (index == count)
                return node.element;
            else {
                node = node.next;
                count++;
            }
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> node = head.next;
        int index = 0;
        while (node != null && node != head) {
            if (node.element.equals(o))
                return index;
            else {
                node = node.next;
                index++;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e: c)
            add(e);
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
