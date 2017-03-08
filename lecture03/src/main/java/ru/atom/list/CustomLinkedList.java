package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    ListNode<E> first;
    ListNode<E> last;
    int size;

    @Override
    public int size() {
        return  size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> qq = first;
        E el = (E) o;
        for (int i = 0; i < size; i++) {
            if  (el.equals(qq.element)) {
                return true;
            }
            qq = qq.next;

        }
        return false;
    }



    @Override
    public Iterator<E> iterator() {
        Iterator<E> elem = new Iterator<E>() {

            private ListNode<E> key = new ListNode(null,null, first);

            @Override
            public boolean hasNext() {
                if (key.next == null) return false;
                else return true;
            }

            @Override
            public E next() {
                if (this.hasNext() == false) throw new NoSuchElementException();
                else {
                    key = key.next;
                    return key.element;
                }
            }
        };
        return elem;
    }

    @Override
    public boolean add(E e) {
        ListNode<E> qq = new ListNode<E>(e, last, null);

        if (size == 0) {
            first = qq;
        } else {
            qq.prev.next = qq;
        }
        size++;
        last = qq;
        return  true;
    }

    @Override
    public boolean remove(Object o) {
        size --;
        ListNode<E> qq = first;
        E el = (E) o;
        for (int i = 0; i < size; i++) {
            if  (el.equals(qq.element)) {
                if (i != 0)  qq.prev.next = qq.next;
                if (i != (size - 1))  qq.next.prev = qq.prev;
                if (i == 0) first = qq.next;
                if (i == (size - 1)) last = qq.prev;
                return true;
            }
            qq = qq.next;

        }
        return false;

    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (Object i : c) {
            if (contains(i) == false) return false;
        }
        return  true;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> qq = first;

        for (int i = 0; i < index; i++) {
            qq = qq.next;
        }
        return qq.element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        while (contains(o) != true) index++;
        return index;

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
