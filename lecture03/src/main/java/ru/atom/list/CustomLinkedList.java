package ru.atom.list;


import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header;

    public CustomLinkedList() {
        header = new ListNode<E>();
    }

    class CustomLinkedListIterator implements Iterator<E> {
        ListNode<E> curr;

        CustomLinkedListIterator() {
            curr = CustomLinkedList.this.header;
        }

        public boolean hasNext() {
            return (curr.getNext() != CustomLinkedList.this.header
                    && curr.getNext() != null) ? true : false;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            curr = curr.getNext();
            return curr.getData();
        }

    }

    @Override
    public int size() {
        int result = 0;
        ListNode<E> curr = header.getNext();
        while (curr != header) {
            ++result;
            curr = curr.getNext();
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return header == header.getNext();
    }

    @Override
    public boolean contains(Object o) {
        E element = (E) o;
        for (E object : this) {
            if (object == element)
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomLinkedListIterator();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> lastNode = header.getPrev();
        ListNode<E> node = new ListNode();
        lastNode.setNext(node);
        node.setData(e);
        node.setPrev(lastNode);
        node.setNext(header);
        header.setPrev(node);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E element = (E) o;
        ListNode<E> curr = header.getNext();
        while (curr != header) {
            if (curr.getData().equals(element)) {
                ListNode<E> prev = curr.getPrev();
                ListNode<E> next = curr.getNext();
                // delete all links to current ListNode
                prev.setNext(next);
                next.setPrev(prev);
                curr.setNext(null);
                curr.setPrev(null);
                curr = null;
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        header.setPrev(header);
        header.setNext(header);
        // All ListNodes will be deleted by GC
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > (this.size() - 1))
            throw new IndexOutOfBoundsException();
        int currIndex = 0;
        E result = null;
        for (E element : this) {
            if (currIndex == index) {
                result = element;
                break;
            }
            ++currIndex;
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        E element = (E) o;
        int index = 0;
        for (E object : this) {
            if (object.equals(element))
                return index;
            ++index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E object : c) {
            if (!this.add(object))
                return false;
        }
        return true;
    }

    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
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
