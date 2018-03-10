package ru.atom.list;


import javax.swing.text.html.HTMLDocument;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {


    private int length ;
    private ListNode<E> header;


    class Item<E> implements Iterator<E> {
        int cur;

        Item() {
            cur = 0;
        }

        @Override
        public boolean hasNext() {
            return cur < length ;
        }

        @Override
        public E next() {
            E tmp = (E) get(cur);
            cur++;
            return tmp;
        }

        @Override
        public void remove() {
            ListNode tmp = header.getNext();
            for (int i = 0;i < cur;i++)
                tmp = tmp.getNext();
            tmp.getPrev().setNext(tmp.getNext());
            tmp.getNext().setPrev(tmp.getPrev());
        }
    }

    @Override
    public int size() {
        return length;
    }

    CustomLinkedList() {
        length = 0;
        header = new ListNode<>();
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode tmp = header.getNext();
        for (int i = 0;i < size();i++) {
            if (tmp.getElement().equals(o))
                return true;
            tmp = tmp.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Item<>();

    }

    @Override
    public boolean add(E e) {
        ListNode tmp = new ListNode();
        tmp.setElement(e);
        tmp.setPrev(header.getPrev());
        tmp.setNext(header);
        tmp.getNext().setPrev(tmp);
        tmp.getPrev().setNext(tmp);
        length++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            ListNode ptr = header.getNext();
            for (int i = 0; i < length ; i++) {
                if (ptr.getElement() == null) {
                    ptr.getPrev().setNext(ptr.getNext());
                    ptr.getNext().setPrev(ptr.getPrev());
                    length--;
                    return true;
                }
                ptr = ptr.getNext();
            }
        } else {
            ListNode ptr = header.getNext();
            for (int i = 0; i < length ; i++) {
                if (o.equals(ptr.getElement())) {
                    ptr.getPrev().setNext(ptr.getNext());
                    ptr.getNext().setPrev(ptr.getPrev());
                    length--;
                    return true;
                }
                ptr = ptr.getNext();
            }
        }
        return false;
    }

    @Override
    public void clear() {
        header.setNext(null);
        header.setPrev(null);
    }

    @Override
    public E get(int index) {
        ListNode ptr = header.getNext();
        for (int i = 0;i < index;i++)
            ptr = ptr.getNext();
        return (E) ptr.getElement();
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            ListNode ptr = header.getNext();
            for (int i = 0; i < length ; i++) {
                if (ptr.getElement() == null) {
                    ptr.getPrev().setNext(ptr.getNext());
                    ptr.getNext().setPrev(ptr.getPrev());
                    return i;
                }
                ptr = ptr.getNext();
            }
        } else {
            ListNode ptr = header.getNext();
            for (int i = 0; i < length ; i++) {
                if (o.equals(ptr.getElement())) {
                    ptr.getPrev().setNext(ptr.getNext());
                    ptr.getNext().setPrev(ptr.getPrev());
                    return i;
                }
                ptr = ptr.getNext();
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] tmp = c.toArray();
        if (tmp.length == 0) return false;
        for (int i = 0;i < tmp.length ; i++)
            add((E) tmp[i]);
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
