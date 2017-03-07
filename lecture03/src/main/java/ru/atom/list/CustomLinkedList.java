package ru.atom.list;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E> {
    ListNode<E> head = new ListNode<>();

    @Override
    public int size() {
        int count  = 0;
        ListNode<E> listElem = head;
        while (listElem.getNext() != head) {
            listElem = listElem.getNext();
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head.getPrev() == head ;
    }

    @Override
    public boolean contains(Object o) throws ClassCastException , NullPointerException {
        if (o == null || isEmpty()) return false;
        else {
            E otherElement = (E)o;
            ListNode<E> listElement = head;
            boolean answer = false;
            for (int i = 0; i <= size(); i++) {
                if (listElement.getElement() == otherElement) answer = true;
                listElement = listElement.getNext();
            }
            return answer;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            ListNode<E> listElement = head;

            @Override
            public boolean hasNext() {
                return listElement.getNext() != head;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    listElement = listElement.getNext();
                    return listElement.getElement();
                } else throw new NoSuchElementException();

            }
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> list = new ListNode<>(e, head, head.getPrev());
        head.getPrev().setNext(list);
        head.setPrev(list);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || isEmpty()) return false;
        else {
            E otherElement = (E)o;
            ListNode<E> listElement = head;
            boolean answer = false;
            for (int i = 0; i <= size(); i++) {
                if (listElement.getElement() == otherElement) {
                    listElement.getPrev().setNext(listElement.getNext());
                    listElement.getNext().setPrev(listElement.getPrev());
                    answer = true;
                }
                listElement = listElement.getNext();
            }
            return answer;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) throws ClassCastException , NullPointerException {
        for (Object o: c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        head.setNext(head);
        head.setPrev(head);
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException  {
        ListNode<E> list = head;
        for (int i = 0; i <= index; i++) {
            list = list.getNext();
        }
        return list.getElement();
    }

    @Override
    public int indexOf(Object o) throws ClassCastException , NullPointerException {
        ListNode<E> list = head;
        for (int i = 0; i <= size(); i++) {
            if (list.getElement().equals(o)) return i;
            list = list.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException, ClassCastException,
            NullPointerException, IllegalArgumentException {
        c.forEach(this::add);
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
