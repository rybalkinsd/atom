package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {

    private ListNode<E> header;
    private ListNode<E> tail;
    private int size;

    CustomLinkedList() {
        this.tail = null;
        this.header = new ListNode<E>(null, tail, tail);
        this.size = 0;
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
        if (size == 0) {
            return false;
        }

        ListNode<E> tmpNode = header;

        while (tmpNode != tail) {
            tmpNode = tmpNode.nextNode;
            if (tmpNode.element.equals(o)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            ListNode<E> currentNode = header;

            @Override
            public boolean hasNext() {
                return currentNode.nextNode != header;
            }

            @Override
            public E next() {
                currentNode = currentNode.nextNode;
                return currentNode.element;
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (tail == null) {
            tail = new ListNode<E>(e, header, header);
            header.nextNode = tail;
            header.prevNode = tail;
            size++;
        } else {
            ListNode<E> newNode = new ListNode<E>(e, header, tail);
            tail.nextNode = newNode;
            tail = newNode;
            header.prevNode = tail;
            size++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        ListNode<E> currNode = header.nextNode;
        if (o == null) {
            while (currNode != header) {
                if (currNode.element == null) {
                    ListNode<E> prevNode = currNode.prevNode;
                    ListNode<E> nextNode = currNode.nextNode;
                    prevNode.nextNode = nextNode;
                    nextNode.prevNode = prevNode;
                    size--;
                    return true;
                }
                currNode = currNode.nextNode;
            }
            return false;
        } else {
            while (currNode != header) {
                if (currNode.element.equals(o)) {
                    ListNode<E> prevNode = currNode.prevNode;
                    ListNode<E> nextNode = currNode.nextNode;
                    prevNode.nextNode = nextNode;
                    nextNode.prevNode = prevNode;
                    size--;
                    return true;
                }
                currNode = currNode.nextNode;
            }
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!this.contains(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        this.tail = null;
        this.header = new ListNode<E>(null, tail, tail);
        this.size = 0;
    }

    @Override
    public E get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        ListNode<E> currNode = header;
        for (int i = 0; i <= index; i++) {
            currNode = currNode.nextNode;
        }
        return currNode.element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        ListNode<E> currNode = header.nextNode;

        if (o == null) {
            while (currNode != header) {
                if (currNode.element == null) {
                    return index;
                }
                index++;
                currNode = currNode.nextNode;
            }
        } else {
            while (currNode != header) {
                if (currNode.element.equals(o)) {
                    return index;
                }
                index++;
                currNode = currNode.nextNode;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            this.add(element);
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
