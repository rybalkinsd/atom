package ru.atom.list;


import java.util.Collection;
import java.util.Objects;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> first = null;
    private int size = 0;
    private ListNode<E> last = null;

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
        ListNode<E> node = first;
        while (node != null) {
            if (Objects.equals(node.getValue(), o)) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<E>(first);
    }

    @Override
    public boolean add(E e) {
        if (first == null) {
            first = new ListNode<E>(e, null, null);
            last = first;
            size++;
            return true;
        }
        ListNode<E> node = last;
        last = new ListNode<E>(e, node, null);
        node.setNext(last);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> node = first;
        while (node != null) {
            if (Objects.equals(node.getValue(), o)) {
                size--;
                return deleteNode(node);
            }
            node = node.getNext();
        }
        return false;
    }

    private boolean deleteNode(ListNode<E> node) {
        ListNode<E> prev = node.getPrevious();
        ListNode<E> next = node.getNext();
        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
        }
        if (next == null) {
            last = prev;
        } else {
            next.setPrevious(prev);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        last = null;
        first = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int count = 0;
        ListNode<E> node = first;
        while (node != null) {
            if (count == index) {
                break;
            }
            node = node.getNext();
            count++;
        }
        return node.getValue();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        ListNode<E> node = first;
        while (node != null) {
            if (Objects.equals(node.getValue(), o)) {
                return index;
            }
            node = node.getNext();
            index++;
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
