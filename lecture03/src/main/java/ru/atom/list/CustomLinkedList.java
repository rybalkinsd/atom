package ru.atom.list;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head = new ListNode<>(null, null, null);

    {
        head.next = head;
        head.prev = head;
    }

    @Override
    public int size() {
        ListNode current = head;
        int count = 0;
        while (current.next != head) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        if (head.next == null)
            return true;
        else
            return false;
    }

    @Override
    public boolean contains(Object o) {
        if (head == null) {
            return false;
        } else {
            ListNode current = head;
            while (current.next != head) {
                current = current.next;
                if (current.getElement().equals(o)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode current = head;

            @Override
            public boolean hasNext() {
                if (current.next == head)
                    return false;
                else
                    return true;
            }

            @Override
            public E next() {
                if (this.hasNext() == false) {
                    throw new NoSuchElementException();
                } else {
                    current = current.next;
                    return (E)current.getElement();
                }
            }
        };
    }

    @Override
    public boolean add(E e) {
        /*if (head.next == null) {
            ListNode<E> newNode = new ListNode<E>(e, head, head);
            head.prev = newNode;
            head.next = newNode;
            return true;
        } else {*/
        ListNode current = head;
        while (current.next != head) {
            current = current.next;
        }
        ListNode<E> newNode = new ListNode<>(e, current, head);
        current.next = newNode;
        head.prev = newNode;
        return true;
        /*}*/
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        } else {
            ListNode current = head;
            while (current.next != head) {
                current = current.next;
                if (current.getElement().equals(o)) {
                    ListNode changed = current.prev;
                    changed.next = current.next;
                    changed = current.next;
                    changed.prev = current.prev;
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e: c) {
            if (this.contains(e) == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head.next = null;
        head.prev = null;
    }

    @Override
    public E get(int index) {
        if (head == null) {
            return null;
        } else {
            ListNode current = head;
            while (current.next != head && index >= 0) {
                current = current.next;
            }
            if (index >= 0) return null;
            else
                return (E)current.getElement();
        }
    }

    @Override
    public int indexOf(Object o) {
        if (head == null) {
            return -1;
        } else {
            int index = -1;
            ListNode current = head;
            while (current.next != head) {
                current = current.next;
                index++;
                if (current.getElement().equals(o))
                    return index;
            }
            return -1;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (E e: c) {
            this.add(e);
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
