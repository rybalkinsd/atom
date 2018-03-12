package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header;

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(CustomLinkedList.class);

    public void setHeader(ListNode header) {
        this.header = header;
    }

    public ListNode getHeader() {
        return header;
    }

    public CustomLinkedList() {
        header = new ListNode<E>(null,null,null);
        header.setPrev(header);
        header.setNext(header);
    }

    @Override
    public int size() {
        ListNode tmp = new ListNode(getHeader());
        int size = 0;
        while ((tmp != header.getPrev())) {
            size++;
            tmp  = tmp.getNext();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    @Override
    public boolean contains(Object o) {
        for (E currentElem : this) {
            log.info("contains()- o : {}, currentElem : {}", o, currentElem);
            if (currentElem.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        Itr<E> it = new Itr();
        return it;
    }

    private class Itr<E> implements Iterator<E> {
        ListNode<E> currentNode;

        public Itr() {
            currentNode = getHeader();

        }

        public ListNode getCurrentNode() {
            return currentNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != header.getPrev();
        }

        @Override
        public E next() {
            currentNode = currentNode.getNext();
            return currentNode.getElement();
        }

    }

    @Override
    public boolean add(E e) {
        ListNode toCreate = new ListNode(e, header, header.getPrev());
        header.getPrev().setNext(toCreate);
        header.setPrev(toCreate);
        log.info("add()- added {} succesfully", toCreate.getElement());
        return true;
    }

    /* Deletes only the first found element */
    @Override
    public boolean remove(Object o) {
        ListNode<E> tmp = new ListNode<E>(getHeader());
        while ((tmp != header.getPrev())) {
            if (o.equals(tmp.getElement())) {
                tmp.getPrev().setNext(tmp.getNext());
                tmp.getNext().setPrev(tmp.getPrev());
                log.info("remove()- removed {} succesfully", tmp.getElement());
                return true;
            }
            tmp = tmp.getNext();
        }
        log.info("remove()- {} not found", tmp.getElement());
        return false;
    }

    /* No tests for this function. Wonder if it must be implemented? */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /* No tests for this function. Wonder if it must be implemented. */
    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    /* No tests for this function. Wonder if it must be implemented. */
    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            for (E elem : c) {
                this.add(elem);
            }
            return true;
        } catch (Exception any) {
            log.error("addAll()- failed to add elements (caught exception)");
            return false;
        }
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
