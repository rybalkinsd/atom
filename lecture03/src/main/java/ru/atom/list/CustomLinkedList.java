package ru.atom.list;

import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> first;
    private ListNode<E> last;
    private ListNode<E> header;
    private int size;


    public CustomLinkedList() {
        header = new ListNode<>(null);
        first = null;
        last = null;
        size = 0;
    }


    @Override
    public int size() {
        return size;
        //  throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
        //throw new UnsupportedOperationException();
    }

    private class ListItr implements Iterator<E> {
        private ListNode<E> iterRef;

        ListItr() {
            iterRef = header;
        }

        @Override
        public boolean hasNext() {
            if (size == 0)
                return false;
            if (iterRef != last)
                return true;
            else
                return false;
        }

        @Override
        public E next() {
            E result  = iterRef.getNext().getData();
            iterRef = iterRef.getNext();
            return result;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new ListItr();
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> current = first;
        if (current == null) {
            first = new ListNode<>(e);
            size++;
            last = first;
            //next alredy is Null cuz ListNode<> constructor
            first.setNext(header);
            first.setPrev(header);
            header.setNext(first);
            header.setPrev(last);
            return true;
        } else {
            current = last;
            current.setNext(new ListNode<>(e));
            this.size++;
            current = current.getNext();
            current.setNext(header);
            current.setPrev(last);
            last = current;
            header.setPrev(last);
            return true;
        }
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = first;
        if (this.size == 0)
            return false;
        if (this.size == 1) {
            header.setNext(null);
            header.setPrev(null);
            this.size--;
            last = null;
            first = null;
            return true;
        }
        for (current = first; current != header; current = current.getNext()) {
            if (o.equals(current.getData())) {
                ListNode<E> tempPrev = current.getPrev();
                ListNode<E> tempNext = current.getNext();
                tempNext.setPrev(tempPrev);
                tempPrev.setNext(tempNext);
                this.size--;

                //if the rule for garbage collector is: if there is 0 reference on object then it will be deleted
                //then we need to null all object field references
                current.setData(null);
                current.setPrev(null);
                current.setNext(null);
                if (current == last)
                    last = tempPrev;
                else if (current == first)
                    first = tempNext;
                return true;
            }
        }
        //throw new UnsupportedOperationException();
        return false;
    }

    @Override
    public void clear() {
        ListNode<E> current = last;
        /*
        while (current != header) {
            //if the rule for garbage collector is: if there is 0 reference on object then it will be deleted
            //then we need to null all object field references
            current.setNext(null);
            current.setData(null);
            ListNode<E> temp = current.getPrev();
            current.setPrev(null);
            current = temp;

            this.size--;
        }
        */
        last = null;
        first = null;

    }

    @Override
    public E get(int index) {
        if (this.first == null || index >  size) throw new IndexOutOfBoundsException();

        ListNode<E> current = first;
        for (int i = 0; current != header; current = current.getNext(), i++) {
            if (i == index)
                return current.getData();

        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        if (this.size == 0)
            return -1;

        int resultIndex = -1;
        ListNode<E> current = first;
        for (int counter = 0; current != header; current = current.getNext(), counter++) {
            if (o.equals(current.getData()))
                resultIndex = counter;
        }

        return resultIndex;

    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e: c) add(e);

        return true;
        //throw new UnsupportedOperationException();
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




