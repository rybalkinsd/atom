package ru.atom.list;

import org.w3c.dom.NodeList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    ListNode header;
    int siz;

    CustomLinkedList() {
        header = new ListNode();
        header.elem = null;
        header.next = header;
        header.prev = header;
        siz = 0;
    }

    @Override
    public int size() {
        return siz;
    }

    @Override
    public boolean isEmpty() {
        return siz == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> cur = header.next;

        E tmp = (E) o;

        while (cur.elem != null) {
            if (cur.elem == tmp) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> current = header.next;

            @Override
            public boolean hasNext() {
                return !(current.elem == null);
            }

            @Override
            public E next() throws IndexOutOfBoundsException {
                E result = current.elem;
                if (current.elem == null) throw new IndexOutOfBoundsException("End of list.");
                current = current.next;
                return result;
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> cur = new ListNode<>();
        cur.prev = header.prev;
        cur.next = header;
        cur.elem = e;

        siz++;
        cur.prev.next = cur;
        header.prev = cur;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E tmp = (E) o;

        if (contains(o)) {
            ListNode<E> cur = header.next;

            while (cur.elem != tmp) {
                cur = cur.next;
            }
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;

            siz--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
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
        for (E x:c) {
            add(x);
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
