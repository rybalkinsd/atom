package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head;
    private int size;

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
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> node = head;
            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("End of list");
                }
                E tmp = node.value;
                node = node.next;
                return tmp;
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (head == null) {
            head = new ListNode<>(e,null,null);
            size++;
            return true;
        } else {
            ListNode<E> tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = new ListNode<>(e,null, tmp);
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> nodeBefor = head;
        if (nodeBefor.next == null) {
            if (o.equals(nodeBefor.value))
                return true;
            else
                return false;
        } else {
            if (o.equals(nodeBefor.value)) {
                ListNode<E> tmp = nodeBefor;
                nodeBefor = nodeBefor.next;
                head = nodeBefor;
                tmp.next = null;
                nodeBefor.prev = null;
                size--;
                return true;
            }
            boolean flag = false;
            while (nodeBefor.next != null) {
                if (o.equals(nodeBefor.next.value)) {
                    flag = true;
                    break;
                }
                nodeBefor = nodeBefor.next;
            }
            if (!flag)
                return false;

            if (nodeBefor.next.next == null) {
                ListNode<E> nodeDelete = nodeBefor.next;
                nodeBefor.next = null;
                nodeDelete.prev = null;
                size--;
                return true;
            } else {
                ListNode<E> nodeAfter = nodeBefor.next.next;
                nodeBefor.next.next = null;
                nodeBefor.next = nodeAfter;
                nodeAfter.prev.prev = null;
                nodeAfter.prev = nodeBefor;
                size--;
                return true;
            }
        }
    }

    @Override
    public void clear() {
        head.next = null;
        head.prev = null;
    }

    @Override
    public E get(int index) {
        ListNode<E> node = head;
        for (int i = 0; node != null && i < index ; node = node.next, i++) {

        }
        if (node == null) {
            new IndexOutOfBoundsException("We don't have such index");
        } else {
            return node.value;
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (ListNode<E> node = head; node != null; node = node.next, index++) {
                if (node.value == null)
                    return index;
            }
        } else {
            for (ListNode<E> node = head; node != null; node = node.next, index++) {
                if (o.equals(node.value))
                    return index;
            }
        }
        return -1;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] array = c.toArray();
        if (array.length == 0) {
            return false;
        } else {
            for (Object obj : array) {
                this.add((E)obj);
            }
            return true;
        }
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
