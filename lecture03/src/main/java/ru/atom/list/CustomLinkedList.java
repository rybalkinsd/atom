package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> first = null;
    private ListNode<E> last = null;
    private int count = 0;

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        if (count == 0) return true;
        else return false;
    }

    @Override
    public boolean contains(Object o) {
        CustomIter iter = new CustomIter();
        while (iter.hasNext()) {
            if (iter.next() == o) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIter();
    }

    private class CustomIter implements Iterator<E> {
        private ListNode<E> lastReturned = first;
        private ListNode<E> returned = first;
        private int num = 0;

        @Override
        public boolean hasNext() {
            return num < size();
        }

        @Override
        public E next() {
            returned = lastReturned;
            lastReturned = lastReturned.next;
            num++;
            return returned.object;
        }
    }

    @Override
    public boolean add(E e) {
        ListNode<E> obj = new ListNode<>(e);
        if (last == null) {
            obj.prev = obj;
            first = obj;
        } else {
            last.next = obj;
            obj.prev = last;
        }

        last = obj;
        count++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> basket = first;
        for (int num = 1; num < size(); num++) {
            if (basket.object == o) {
                if (basket == first) {
                    first = basket.next;
                    first.prev = first;
                } else if (basket.next == null) {
                    basket.prev.next = null;
                } else {
                    basket.prev.next = basket.next;
                    basket.next.prev = basket.prev;
                }
                count--;
                return true;
            }
            basket = basket.next;
        }
        return false;
    }

    @Override
    public void clear() {
        CustomIter iter = new CustomIter();
        while (iter.hasNext()) {
            remove(iter.next());
        }
    }

    @Override
    public E get(int index) {
        CustomIter iter = new CustomIter();
        while (iter.hasNext()) {
            if (iter.num == index) return iter.next();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object o) {
        CustomIter iter = new CustomIter();
        while (iter.hasNext()) {
            if (iter.next() == o) return iter.num;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E obj : c) {
            add(obj);
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
