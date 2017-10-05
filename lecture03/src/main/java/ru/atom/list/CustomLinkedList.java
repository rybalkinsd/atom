package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {

    private int size = 0;
    private ListNode<E> first;
    private ListNode<E> last;


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        ListNode<E> current = first;
        while (current.hasNext()) {
            if (current.get() == (E) o) return true;
            current = current.getNext();
        }
        if (current.get() == (E) o) return true;
        return false;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> current = first;

            public E next() {
                if (current.hasNext()) {
                    current = current.getNext();
                    return current.getPrev().get();
                } else {
                    E temp = current.get();
                    current.set(null);
                    return temp;
                }
            }

            public boolean hasNext() {
                return current.hasCurrent();
            }
        };
    }

    public boolean add(E e) {
        if (size == 0) {
            first = new ListNode<E>(e);
            last = first;
            size++;
        } else {
            last.setNext(new ListNode<E>(e));
            last.getNext().setPrev(last);
            last = last.getNext();
            size++;
        }
        return true;
    }

    public boolean remove(Object o) {
        ListNode<E> current = first;
        for (E e : this) {
            if (e == o) {
                size--;
                if (current == first) {
                    first = first.getNext();
                    first.setPrev((ListNode<E>) null);
                    current.setNext((ListNode<E>) null);
                } else if (current == last) {
                    last = last.getPrev();
                    last.setNext((ListNode<E>) null);
                    current.setPrev((ListNode<E>) null);
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public void clear() {

    }

    public E get(int index) {
        if (index > size) return null;
        int itr = 1;
        ListNode<E> current = first;
        while (itr < index) {
            current = current.getNext();
        }
        return current.get();
    }

    public int indexOf(Object o) {
        int itr = 1;
        ListNode<E> current = first;
        while (current.get() != o) {
            if (itr > size) return -1;
            itr++;
            current = current.getNext();
        }
        return itr;
    }

    public boolean addAll(Collection<? extends E> c) {
        Object[] objArray = c.toArray();
        if (objArray.length == 0) return false;
        for (int i = 0; i < objArray.length; i++) {
            this.add((E) objArray[i]);
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
