package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {

    private final ListNode<E> head; //the field does not contain data (the element field is null)
    private ListNode<E> tail; //cache it to fast adding
    private int sz; //cache it to avoid iteration over the list in the size method

    public CustomLinkedList() {
        head = new ListNode<>();
        tail = head;
        sz = 0;
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return sz == 0;
    }

    @Override
    public boolean contains(Object o) {
        return find(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator<E>(head);
    }

    @Override
    public boolean add(E e) {
        addAfter(tail, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> iter = find(o);
        if (iter != null) {
            removeNode(iter);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        while (head.next != null) {
            remove(head.next);
        }
        sz = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= sz)
            throw new ArrayIndexOutOfBoundsException(index);
        return getByIndex(index).element; //ListNode can not be null!!!
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (E el : this) {
            if (el.equals(o)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAfter(tail, c);
        return true;
    }


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
        if (index < 0 || index > sz)
            throw new ArrayIndexOutOfBoundsException(index);
        ListNode<E> leftNode = getByIndex(index - 1); //get node before insert position (it could be head if index == 0)
        addAfter(leftNode, c);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int prevsize = sz;
        for (Object el : c) {
            remove(el);
        }
        recoverTail();
        return prevsize > sz;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int prevsize = sz;
        for (ListNode<E> iter = head; iter.next != null;) {
            if (!c.contains(iter.next.element)) {
                removeNode(iter.next);
            } else {
                iter = iter.next;
            }
        }
        recoverTail();
        return prevsize > sz;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > sz)
            throw new ArrayIndexOutOfBoundsException(index);
        addAfter(getByIndex(index - 1), element);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= sz)
            throw new ArrayIndexOutOfBoundsException(index);
        ListNode<E> node = getByIndex(index);
        removeNode(node);
        recoverTail();
        return node.element;
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

    private ListNode<E> find(Object o) {
        for (ListNode<E> iter = head.next; iter != null; iter = iter.next) {
            if (iter.element.equals(o)) {
                return iter;
            }
        }
        return null;
    }

    private ListNode<E> getByIndex(int index) {
        ListNode<E> iter = head; //if index is less that 0 returns head
        for (int id = 0; id <= index; ++id) {
            iter = iter.next;
        }
        return iter;
    }
    
    private void addAfter(ListNode<E> leftNode, E el) {
        leftNode.add(new ListNode<E>(el));
        recoverTail();
        ++sz;
    }
    
    private void addAfter(ListNode<E> leftNode, Collection<? extends E> c) {
        for (E el : c) {
            leftNode.add(new ListNode<E>(el));
            leftNode = leftNode.next;
        }
        recoverTail();
        sz += c.size();
    }
    
    private void removeNode(ListNode<E> node) {
        ListNode.connect(node.prev, node.next);
        node.next = node.prev = null;
        node = null;
        --sz;
    }
    
    /*
     * Use it if tail could be changed (after additional in the end)
     */
    private void recoverTail() {
        while (tail.next != null) {
            tail = tail.next;
        }
    }

    static class CustomListIterator<E> implements Iterator<E> {

        private final ListNode<E> head;
        private ListNode<E> current;

        CustomListIterator(ListNode<E> head) {
            this.head = head;
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            current = current.next;
            return current.element;
        }
    }
}