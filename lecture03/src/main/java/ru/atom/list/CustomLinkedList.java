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
        throw new NotImplementedException();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node = new ListNode<>();
        node.element = e;
        node.next = head;
        node.prev = head.prev;

        if (head.prev != null) {
            head.prev.next = node;
        } else {
            head.next = node;
        }
        head.prev = node;

        size ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = head.next;
        while (node != null && node != head) {
            if (node.element.equals(o)) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
                return true;
            }
            else node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        while (head != null && head.prev != head) {
            remove(0);
            head = head.next;
        }
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
        throw
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new NotImplementedException();
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
