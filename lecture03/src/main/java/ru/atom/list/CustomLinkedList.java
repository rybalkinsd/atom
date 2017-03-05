package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {

    private ListNode<E> head = new ListNode<>(null, null, null);

    @Override
    public int size() {
        if (head.next == null) {
            return new Integer(0);
        }
        int numb = 0;
        ListNode temp = head.next;
        while (temp != head) {
            numb++;
            temp = temp.next;
        }
        return numb;
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
    }

    @Override
    public boolean contains(Object o) {
        ListNode temp = head.next;
        for (int i = 0; i < this.size(); i++) {
            if (temp.equals(o)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>(head);
    }

    @Override
    public boolean add(E e) {
        ListNode temp = new ListNode(e, head, null);
        if (head.prev == null) {
            head.prev = temp;
            head.next = temp;
            temp.prev = head;
            temp.next = head;
        } else {
            temp.prev = head.prev;
            head.prev.next = temp;
            head.prev = temp;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!this.contains(o)) {
            return false;
        } else {
            ListNode temp = head.next;
            for (int i = 0; i < this.size(); i++) {
                if (temp.equals(o)) {
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                    break;
                }
                temp = temp.next;
            }
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
        ListNode<E> temp = head;
        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }
        return temp.element;
    }

    @Override
    public int indexOf(Object o) {
        if (!this.contains(o)) {
            return -1;
        } else {
            ListNode temp = head.next;
            for (int i = 0; i < this.size(); i++) {
                if (temp.equals(o)) {
                    return i;
                }
                temp = temp.next;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E el : c) {
            this.add(el);
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
