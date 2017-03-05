package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> header;

    public CustomLinkedList() {
        this.header = null;
    }

    @Override
    public int size() {
        int size = 0;
        for (E it : this)
            size++;
        return size;
    }

    @Override
    public boolean isEmpty() {
        return header == null;
    }

    @Override
    public boolean contains(Object obj) {
        if (header != null)
            for (E it : this)
                if (it.equals(obj))
                    return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> it = null;

            @Override
            public boolean hasNext() {
                if (it == null && header != null) {
                    return true;
                } else if (it != null) {
                    return it.getNextIndex() != null;
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                if (it == null)
                    it = header;
                else
                    it = it.getNextIndex();
                return it.getValue();
            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> temp = new ListNode<>();
        temp.setValue(e);
        if (header == null) {
            header = temp;
        } else {
            ListNode<E> it = header;
            while (it.getNextIndex() != null)
                it = it.getNextIndex();
            temp.setPrevIndex(it);
            it.setNextIndex(temp);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode temp = header;
        for (E it : this) {
            if (it.equals(o)) {
                if (temp.getPrevIndex() == null) {
                    temp.getNextIndex().setPrevIndex(null);
                    header = temp.getNextIndex();
                    return true;
                } else {
                    if (temp.getNextIndex() == null) {
                        temp.getPrevIndex().setNextIndex(null);
                        temp = null;
                        return true;
                    } else {
                        ListNode tempPrev = temp.getPrevIndex();
                        ListNode tempNext = temp.getNextIndex();
                        tempNext.setPrevIndex(temp.getPrevIndex());
                        tempPrev.setNextIndex(temp.getNextIndex());
                        temp = null;
                        return true;
                    }
                }
            }
            temp = temp.getNextIndex();
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public E get(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E it : c) {
            this.add(it);
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
