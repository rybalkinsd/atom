package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    /**
     * @param size the number of elements in the linked list
     */
    private int size = 0;

    /**
     * @param first the first element in the linked list
     */
    private ListNode<E> first = null;

    /**
     * @param last the last element in the linked list
     */
    private ListNode<E> last = null;

    /**
     * @return size of linked list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return <tt>true</tt> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * @return iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr(first);
    }

    private class Itr implements Iterator<E> {
        private ListNode<E> item;

        public Itr(ListNode<E> item) {
            this.item = item;
        }

        @Override
        public boolean hasNext() {
            return item != null;
        }

        @Override
        public E next() {
            E nextNode = item.getValue();
            item = item.getNext();
            return nextNode;
        }
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(E e) {
        size++;
        ListNode<E> newListNode = new ListNode<>(e, last);
        if (first == null) {
            first = newListNode;
        } else {
            last.setNext(newListNode);
        }
        last = newListNode;
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        ListNode<E> item = first;
        while (item != null) {
            if (!item.getValue().equals(o)) {
                item = item.getNext();
                continue;
            }
            if (item.getNext() != null) {
                item.getNext().setPrev(item.getPrev());
            }
            if (item == first) {
                first = item.getNext();
            } else {
                item.getPrev().setNext(item.getNext());
            }
            size--;
            return true;
        }
        return false;
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified collection.
     *
     * @param c collection whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    @Override
    public void clear() {
        while (size > 0) {
            remove(first.getValue());
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        ListNode<E> newNode = first;
        while (index > 0) {
            newNode = newNode.getNext();
            index--;
        }
        return newNode.getValue();
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        ListNode<E> item = first;
        while (item != null) {
            if (item.getValue().equals(o)) {
                return index;
            }
            item = item.getNext();
            index++;
        }
        return -1;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            add(item);
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
