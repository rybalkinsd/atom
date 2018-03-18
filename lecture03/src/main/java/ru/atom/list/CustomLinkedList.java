package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


class CustomLinkedList<E> implements List<E> {
    private int size = 0;
    private ListNode<E> first;
    private ListNode<E> last;

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
        return indexOf(o) >= 0;
    }

    private class MyIterator implements Iterator<E> {

        ListNode<E> curElem;

        MyIterator(CustomLinkedList<E> list) {
            curElem = list.first;
        }

        @Override
        public boolean hasNext() {
            return curElem != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new RuntimeException();
            }
            E curItem = curElem.getItem();
            curElem = curElem.getNext();
            return curItem;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator(this);
    }

    @Override
    public boolean add(E e) {
        final ListNode<E> l = last;
        final ListNode<E> newNode = new ListNode<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.setNext(newNode);
        size++;
        return true;
    }

    E unlink(ListNode<E> x) {
        // assert x != null;
        final E element = x.getItem();
        final ListNode<E> next = x.getNext();
        final ListNode<E> prev = x.getPrev();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
            x.setPrev(null);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
            x.setNext(null);
        }

        x.setItem(null);
        size--;
        return element;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.getNext()) {
                if (x.getItem() == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.getNext()) {
                if (o.equals(x.getItem())) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;

    }


    @Override
    public void clear() {
        for (ListNode<E> x = first; x != null; ) {
            ListNode<E> next = x.getNext();
            x.setItem(null);
            x.setNext(null);
            x.setPrev(null);
            x = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {

        int index = 0;
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.getNext()) {
                if (x.getItem() == null)
                    return index;
                index++;
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.getNext()) {
                if (o.equals(x.getItem()))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int len = c.toArray().length;
        if (len != 0) {
            for (E e : c) {
                add(e);
            }
            return true;
        }
        return false;
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
