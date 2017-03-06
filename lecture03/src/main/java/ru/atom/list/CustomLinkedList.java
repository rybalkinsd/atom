package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode header;

    CustomLinkedList() {
        header = new ListNode();
        header.setNext(header);
        header.setPrev(header);
        header.setContent((E) null);
    }


    @Override
    public int size() {
        ListNode<E> elem = header;
        int num = 0;
        if (isEmpty()) return 0;
        else {
            do {
                num++;
                elem = elem.getNext();
            } while (elem.getNext() != header);
            return num;
        }
    }

    @Override
    public boolean isEmpty() {
        return (header.getNext() == header);
    }

    @Override
    public boolean contains(Object o) {
        if (!isEmpty()) {
            ListNode<E> elem = header;
            if (elem.getNext().getContent().getClass().isInstance(o)) {
                do {
                    elem = elem.getNext();
                    if ((E) o == elem.getContent()) return true;
                } while (elem.getNext() != header);
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iter = new Iterator<E>() {

            private ListNode<E> elem = header;

            @Override
            public boolean hasNext() {
                return (elem.getNext() != header);
            }

            @Override
            public E next() {
                if (hasNext()) {
                    elem = elem.getNext();
                    return elem.getContent();
                } else throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return iter;
    }

    @Override
    public boolean add(E e) {
        ListNode<E> elem = header;

        ListNode<E> newElem = new ListNode<>();
        newElem.setContent((E)e);

        if (isEmpty()) {
            newElem.setPrev(header);
            header.setNext(newElem);
        } else {
            elem = header.getPrev();
            newElem.setPrev(elem);
            elem.setNext(newElem);
        }
        newElem.setNext(header);
        header.setPrev(newElem);
        return true;
    }

    @Override
    public boolean remove(Object o) {

        if (!isEmpty()) {

            ListNode<E> elem = header;
            if (elem.getNext().getContent().getClass().isInstance(o)) {
                do {
                    elem = elem.getNext();
                    if ((E) o == elem.getContent()) {
                        elem.getPrev().setNext(elem.getNext());
                        elem.getNext().setPrev(elem.getPrev());
                        return true;
                    }
                } while (elem.getNext() != header);
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (isEmpty()) return false;
        else {
            for (Object elem : c) {
                if (!contains(elem)) return false;
            }
            return true;
        }
    }

    @Override
    public void clear() {
        ListNode<E> elem = header;
        do {
            elem.setContent(null);
            elem.setPrev(null);
            elem = elem.getNext();
            elem.getPrev().setNext(null);
        } while (elem.getNext() != header);
        elem.getPrev().setNext(null);
        elem.getPrev().setContent(null);
        elem.getPrev().setPrev(null);
        header.setNext(header);
        header.setPrev(header);
    }

    @Override
    public E get(int index) {
        if (index < size() && index >= 0) {
            int num = 0;
            ListNode<E> elem = header.getNext();
            while (num != index) {
                elem = elem.getNext();
                num++;
            }
            return elem.getContent();
        } else throw new IndexOutOfBoundsException();

    }

    @Override
    public int indexOf(Object o) {
        if (!isEmpty()) {
            ListNode<E> elem = header;
            int num = -1;
            if (elem.getNext().getContent().getClass().isInstance(o)) {
                do {
                    elem = elem.getNext();
                    num++;
                    if ((E) o == elem.getContent()) return num;
                } while (elem.getNext() != header);
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        ListNode<E> elem = header.getPrev();
        for (E elemCollection : c) {
            ListNode<E> newElem = new ListNode<>();
            newElem.setContent(elemCollection);
            elem.setNext(newElem);
            newElem.setPrev(elem);
            newElem.setNext(header);
            header.setPrev(newElem);
            elem = newElem;
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
