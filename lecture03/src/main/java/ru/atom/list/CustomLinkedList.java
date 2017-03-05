package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> head;
    private ListNode<E> last;

    public CustomLinkedList() {
        head = null;
        last = null;
    }

    @Override
    public int size() {
        int size = 0;
        ListNode<E> tmp = head;

        while (tmp != null){
            tmp = tmp.getNext();
            ++size;
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> tmp = head;

        while (tmp != null){
            if (tmp.getValue().equals(o)) {
                return true;
            }
            tmp = tmp.getNext();
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator(head);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> tmp = new ListNode<>();
        tmp.setValue(e);

        if (head == null) {
            head = tmp;
            last = tmp;
        } else {
            last.setNext(tmp);
            tmp.setPrev(last);
            last = tmp;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> tmp = head;

        while (tmp != null){
            if (tmp.getValue().equals(o)) {
                if (tmp == head) {
                    head = head.getNext();
                    head.setPrev(null);
                    return true;
                } else {
                    tmp.getPrev().setNext(tmp.getNext());
                    tmp.getNext().setPrev(tmp.getPrev());
                    return true;
                }
            }
            tmp = tmp.getNext();
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = null;
        last = null;
    }

    @Override
    public E get(int index) {
        int i = 0;
        ListNode<E> tmp = head;

        while (tmp != null && i != index) {
            tmp = tmp.getNext();
            ++i;
        }

        return tmp.getValue();
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        ListNode<E> tmp = head;

        while (tmp != null){
            if (tmp.getValue().equals(o)) {
                return i;
            }
            tmp = tmp.getNext();
            ++i;
        }

        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
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
