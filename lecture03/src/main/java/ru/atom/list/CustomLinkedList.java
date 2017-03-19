package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Collection;

// import ru.atom.list.ListNode;

public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> head;
    private ListNode<E> tail;
    private int listSize = 0;

    @Override
    public int size() {
        return this.listSize;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null || this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        try {
            E elemValue = (E) o;
            ListNode<E> iterateVar = this.head;
            while (iterateVar != null) {
                if (iterateVar.getValue().equals(elemValue)) {
                    return true;
                }
                iterateVar = iterateVar.getNext();
            }
            return false;
        } catch (Exception anyExec) {
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private ListNode<E> it = null;

            @Override
            public boolean hasNext() {
                if (it == null && head != null) {
                    return true;
                } else if (it != null) {
                    return it.getNext() != null;
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                if (it == null)
                    it = head;
                else
                    it = it.getNext();
                    return it.getValue();
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (this.isEmpty()) {
            this.head = new ListNode<>(e);
            this.tail = this.head;
            this.listSize += 1;
            return true;
        } else {
            tail.setNext(new ListNode<E>(e));
            this.tail = tail.getNext();
            this.listSize += 1;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        try {
            E elemValue = (E) o;
            int countIndex = 0;
            ListNode<E> iterateVar = this.head;
            ListNode<E> prev = null;
            while (iterateVar.getNext() != null) {
                if (iterateVar.getValue().equals(elemValue)) {
                    if (countIndex == 0) {
                        this.head = iterateVar.getNext();
                    }
                    if (countIndex == this.size()) {
                        this.tail = prev;
                    }
                    iterateVar.setNext(iterateVar.getNext().getNext());
                    this.listSize -= 1;
                    return true;
                }
                countIndex += 1;
                prev = iterateVar;
                iterateVar = iterateVar.getNext();
            }
            return false;
        } catch (Exception anyExec) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Iterator i = c.iterator(); i.hasNext();) {
            boolean tmp = this.contains(i.next());
            if (!tmp) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        this.listSize = 0;
        this.head = null;
        this.tail = null;
    }

    @Override
    public E get(int index) {
        if (index >= this.size()) {
            return null;
        }
        int iterateVar = 0;
        ListNode<E> tmp = this.head;
        while ((iterateVar <= index) && (tmp.getNext() != null)) {
            if (iterateVar == index) {
                return tmp.getValue();
            }
            iterateVar += 1;
            tmp = tmp.getNext();
        }
        return tmp.getValue();
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) return -1;
        try {
            E elemValue = (E) o;
            int countIndex = 0;
            ListNode<E> iterateVar = this.head;
            while (iterateVar != null) {
                if (iterateVar.getValue().equals(elemValue)) {
                    return countIndex;
                }
                countIndex += 1;
                iterateVar = iterateVar.getNext();
            }
            return -1;
        } catch (Exception anyExec) {
            return -1;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Iterator i = c.iterator(); i.hasNext();) {
            boolean tmp = this.add((E) i.next());
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
