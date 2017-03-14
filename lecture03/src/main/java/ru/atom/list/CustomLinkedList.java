package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> header;
    private int size = 0;

    public CustomLinkedList() {
        header = new ListNode<E>(null, null, null);
        header.setNextIndex(header);
        header.setPrevIndex(header);
    }

    @Override
    public int size() {
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
            private ListNode<E> it = header;
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter != size;
            }

            @Override
            public E next() {
                E temp = it.getValue();
                it = it.getNextIndex();
                counter++;
                return temp;
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (size != 0) {
            ListNode<E> temp = new ListNode<>(e, header, header.getPrevIndex());
            temp.getPrevIndex().setNextIndex(temp);
            header.setPrevIndex(temp);
            size++;
        } else {
            header.setValue(e);
            size++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode temp = header;
        for (E it : this) {
            if (it.equals(o)) {
                size--;
                if (temp == header) {
                    temp.getNextIndex().setPrevIndex(header.getPrevIndex());
                    header = temp.getNextIndex();
                    return true;
                } else {
                    temp.getPrevIndex().setNextIndex(temp.getNextIndex());
                    temp.getNextIndex().setPrevIndex(temp.getPrevIndex());
                    return true;
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
