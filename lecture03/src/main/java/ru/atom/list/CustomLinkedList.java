package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private int size = 0;
    private ListNode<E> first;
    private ListNode<E> last;


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return  (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListInterator<E>(first);
    }

    @Override
    public boolean add(E e){
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
        for (ListNode<E> x = first; x != null; x = x.next) {
            if (o.equals(x.value)) {
                unlink(x);
                return true;
            }
            }
        }
         return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
                       if (!contains(e))
                           return false;
        return true;
    }

    @Override
    public void clear() {
        for (ListNode<E> x = first; x != null; ) {
                       ListNode<E> next = x.next;
                       x.value = null;
                       x.next = null;
                       x.prev = null;
                       x = next;
                   }
               first = null;
               last = null;
               size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        ListNode<E> element;
        element = first;
        for (int i = 0; i < index; i++)
            element = element.next;
        return element.value;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (x.value == null)
                return index;
                index++;
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.value))
                return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] all = c.toArray();
        int numNew = all.length;
        if (numNew == 0)
            return false;
        ListNode<E> pred = last;
        for (Object o : all) {
            ListNode<E> newNode = new ListNode<>(pred, (E) o, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }
        last = pred;
        size += numNew;
        return true;
    }



    private void linkLast(E e) {
        final ListNode<E> l = last;
        final ListNode<E> newNode = new ListNode<>(l, e, null);
        last = newNode;
         if (l == null)
            first = newNode;
                else
        l.next = newNode;
        size++;
    }

    private E unlink(ListNode<E> node) {
        final E element = node.value;
        final ListNode<E> next = node.next;
        final ListNode<E> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
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
