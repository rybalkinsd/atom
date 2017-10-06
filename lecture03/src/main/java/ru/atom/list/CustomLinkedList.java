package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

public class CustomLinkedList<E> implements List<E> {

    private final ListNode<E> head; //the field does not contain data (the element field is null)
    private ListNode<E> tail; //cache it to fast adding
    private int size; //cache it to avoid iteration over the list in the size method

    public CustomLinkedList() {
        head = new ListNode<>(null);
        tail = head;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return findNode(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator<E>(head);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node = new ListNode<>(e);
        tail.next = node;
        node.prev = tail;
        tail = tail.next;
        ++size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> iter = findNode(o);
        if (iter != null) {
            if (iter == tail) {
                tail = tail.prev;
                tail.next = null;
                iter.prev = null;
            } else {
                ListNode<E> leftNode = iter.prev;
                ListNode<E> rightNode = iter.next;
                leftNode.next = rightNode;
                rightNode.prev = leftNode;
                iter.prev = iter.next = null;
            }
            --size;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        while (tail != head) {
            ListNode<E> iter = tail;
            tail = tail.prev;
            tail.next = null;
            iter.prev = iter.next = null;
            iter = null;
        }
        tail = head;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(index);
        ListNode<E> iter = head.next;
        for (int ind = 0; ind < index; ++ind) {
            iter = iter.next;
        }
        return iter.element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Predicate<E> predicate = null;
        if (o == null) {
            predicate = (el) -> el == null;
        } else {
            predicate = (el) -> o.equals(el); //o cannot be null (we recently checked it), but el can be null
        }
        for (ListNode<E> iter = head.next; iter != null; iter = iter.next, ++index) {
            if (predicate.test(iter.element)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        for (E el : c) {
            ListNode<E> node = new ListNode<>(el);
            tail.next = node;
            node.prev = tail;
            tail = tail.next;
        }
        size += c.size();
        return true;
    }

    private ListNode<E> findNode(Object o) {
        Predicate<E> predicate = null;
        if (o == null) {
            predicate = (el) -> el == null;
        } else {
            predicate = (el) -> o.equals(el); //o cannot be null (we recently checked it), but el can be null
        }
        for (ListNode<E> iter = head.next; iter != null; iter = iter.next) {
            if (predicate.test(iter.element)) {
                return iter;
            }
        }
        return null;
    }


    /*
  !!! Implement methods below Only if you know what you are doing !!!
 */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
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
     * Last element is first if we iterate list from end to begin
     */
    @Override
    public int lastIndexOf(Object o) {
        Predicate<E> predicate = null;
        if (o == null) {
            predicate = (el) -> el == null;
        } else {
            predicate = (el) -> o.equals(el); //o cannot be null (we recently checked it), but el can be null
        }
        int index = size - 1;
        for (ListNode<E> iter = tail; iter != head; iter = iter.prev, --index) {
            if (predicate.test(iter.element)) {
                return index;
            }
        }
        return -1;
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

    static class CustomListIterator<E> implements Iterator<E> {

        private ListNode<E> current;

        CustomListIterator(ListNode<E> beginIter) {
            current = beginIter;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            return (current = current.next).element;
        }

    }
}