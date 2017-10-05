package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> rootNode = new ListNode<>();
    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        ListNode<E> curNode = rootNode;
        for (int i = 0; i < this.size; i++) {
            curNode = curNode.next;
            if (o.equals(curNode.var)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CllIterator<E>(rootNode);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> node;
        try {
            node = new ListNode<>(e);
        } catch (Exception ex) {
            return false;
        }
        rootNode.prev.next = node;
        node.prev = rootNode.prev;
        rootNode.prev = node;
        node.next = rootNode;
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        ListNode<E> curNode = rootNode;
        for (int i = 0; i < size; i++) {
            curNode = curNode.next;
            if (o.equals(curNode.var)) {
                curNode.next.prev = curNode.prev;
                curNode.prev.next = curNode.next;
                --this.size;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        rootNode = new ListNode<>();
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index < this.size / 2) {
            ListNode<E> curNode = rootNode;
            for (int i = 0; i != index; i++) {
                curNode = curNode.next;
            }
            return curNode.var;
        }
        if (index >= this.size / 2) {
            ListNode<E> curNode = rootNode;
            for (int i = size; i != index; i--) {
                curNode = curNode.prev;
            }
            return curNode.var;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            return -1;
        }
        ListNode<E> curNode = rootNode;
        for (int i = 0; i < size; i++) {
            curNode = curNode.next;
            if (o.equals(curNode.var)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            if (!this.add(e)) {
                return false;
            }
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
