package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    int size = 0;
    ListNode<E> head;

    class CommonListIterator<E> implements Iterator<E> {
        int cur = 0;

        @Override
        public boolean hasNext() {
            return cur < size;
        }

        @Override
        public E next() {
            E tmp = (E) get(cur);
            cur++;
            return tmp;
        }

        @Override
        public void remove() {
            ListNode<E> tmp = (ListNode<E>) head;
            for (int i = 0; i < cur - 1; i++)
                tmp = tmp.getNext();
            tmp.getPrevious().setNext(tmp.getNext());
            tmp.getNext().setPrevious(tmp.getPrevious());
            cur--;
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        ListNode tmp = head;
        for (int i = 0;i < size();i++) {
            if (tmp.getDate().equals(o))
                return true;
            tmp = tmp.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CommonListIterator<E>();
    }

    @Override
    public boolean add(E e) {
        if (size == 0) {
            head = new ListNode<E>(e);
            head.setNext(head);
            head.setPrevious(head);
            size++;
            return true;
        }
        ListNode<E> cur = head;
        for (;cur.getNext() != head; cur = cur.getNext());
        cur.setNext(new ListNode<E>(e));
        cur.getNext().setPrevious(cur);
        cur.getNext().setNext(head);
        head.setPrevious(cur.getNext());
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            if (size == 0) {
                head = null;
                size--;
                return true;
            }
            ListNode<E> cur = head;
            if (cur.getDate() == o) {
                head.getPrevious().setNext(cur.getNext());
                head.getNext().setPrevious(cur.getPrevious());
                head = head.getNext();
                size--;
                return true;
            }
            for (cur = cur.getNext(); cur.getNext() != head; cur = cur.getNext())
                if (cur.getDate() == o) {
                    cur.getPrevious().setNext(cur.getNext());
                    cur.getNext().setPrevious(cur.getPrevious());
                    size--;
                }
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getDate();
    }

    @Override
    public int indexOf(Object o) {
        if (contains(o)) {
            int index = 0;
            for (ListNode<E> temp = head; temp.getDate() == o; temp = temp.getNext()) {
                index++;
            }
            return index;
        }
        return 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] temp = c.toArray();
        if (temp.length == 0) return false;
        for (int i = 0; i < temp.length; i++) {
            add((E) temp[i]);
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
