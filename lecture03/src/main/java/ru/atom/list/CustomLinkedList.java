package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private int listSize;

    private ListNode<E> head;

    CustomLinkedList() {
        listSize = 0;
        head = null;
    }

    class CustomListIterator<E> implements Iterator<E> {
        private int index;
        private CustomLinkedList<E> l;

        CustomListIterator(CustomLinkedList<E> l) {
            index = -1;
            this.l = l;
        }

        @Override
        public boolean hasNext() {
            if ((index + 1) < l.listSize) {
                return true;
            }
            return false;
        }

        @Override
        public E next () {
            return l.get(++index);
        }
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        } else if (listSize == 0) {
            return false;
        } else {
            ListNode<E> temp = head;
            for (int i = 0; i < listSize; i ++) {
                if (temp.value.equals(o)) {
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator<>(this);
    }

    @Override
    public boolean add(E e) {
        if (listSize == 0) {
            head = new ListNode<E>();
            head.next = head;
            head.prev = head;
            head.value = e;
            listSize++;
            return true;
        } else {
            ListNode<E> temp = new ListNode<E>();
            temp.value = e;
            temp.next = head;
            temp.prev = head.prev;
            head.prev.next = temp;
            head.prev = temp;
            listSize++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        ListNode<E> temp = head;
        for(int i = 0; i < listSize; i++) {
            if (temp.value.equals(o)) {
                if (temp == head) {
                    head = temp.next;
                }
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                temp.prev = null;
                temp.next = null;
                temp.value = null;
                listSize--;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        listSize = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            return -1;
        }
        ListNode<E> temp = head;
        for(int i = 0; i < listSize; i++) {
            if (temp.value.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E o : c) {
            add(o);
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