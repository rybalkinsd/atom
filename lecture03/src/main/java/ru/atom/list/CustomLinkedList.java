package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    ListNode<E> head;
    ListNode<E> tail;

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }
        int res = 1;
        ListNode<E> iter = head;
        while (iter.getNext() != head) {
            res += 1;
            iter = iter.getNext();
        }
        //System.out.println(res);
        return res;
    }

    @Override
    public boolean isEmpty() {
        return !(head == null);
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        if (indexOf(o) != -1) {
            return true;
        }
        return false;

        //throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter(head);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> temp = new ListNode<>(null,null,e);
        if (head == null) {
            head = temp;
            head.setPrev(head);
            head.setNext(head);
            tail = head;
        } else {
            tail.setNext(temp);
            temp.setNext(head);
            temp.setPrev(tail);
            head.setPrev(temp);
            tail = temp;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }
        if (head.getNext() == head) {
            head = null;
            tail = null;
            return true;
        }
        ListNode<E> temp = head;
        while (true) {
            //if (o instanceof E )
            //if (( o instanceof E))
            if (temp.getValue().equals(o)) {
                temp.getPrev().setNext(temp.getNext());
                if (temp == head) {
                    head = temp.getNext();
                }
                temp.getNext().setPrev(temp.getNext());
                if (tail == temp) {
                    tail = temp.getPrev();
                }
                temp = null;
                return true;
            }
            temp = temp.getNext();
        }
    }

    @Override
    public void clear() {
        if (head == null) {
            return;
        }
        if (!iterator().hasNext()) {
            head = null;
            tail = null;
        }
        tail.setNext(null);
        tail = null;
        ListNode temp;
        while (head.getNext() != null) {
            temp = head.getNext();
            head = null;
            head = temp;
        }
    }

    @Override
    public E get(int index) {

        if (size() <= index) {
            return null;
        }
        ListNode<E> it = head;
        E res = null;
        while (index >= 0) {
            res = it.getValue();
            it = it.getNext();
            index = index - 1;
        }
        return res;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Iter it = (Iter) iterator();
        while (it.hasNext()) {
            if (it.getValue().equals(o)) {
                return ++index;
            }
            index++;
            it.next();
        }
        return -1;

        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E it : c) {
            add(it);
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
