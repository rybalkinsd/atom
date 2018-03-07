package ru.atom.list;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<E> implements List<E> {

    private int size = 0;

    private ListNode<E> first;

    private ListNode<E> last;

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
        return new MyIterator<E>(first);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<E>(last, e, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;

    }

    E unlink(ListNode<E> x) {
        // assert x != null;
        final E element = x.item;
        final ListNode<E> next = x.next;
        final ListNode<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    ListNode<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            ListNode<E> node = first;
            for (int i = 0; i < index; i++)
                node = node.next;
            return node;
        } else {
            ListNode<E> node = last;
            for (int i = size - 1; i > index; i--)
                node = node.prev;
            return node;
        }
    }

    @Override
    public int indexOf(Object o) {

        int index = 0;
        if (o == null) {
            for (ListNode<E> node = first; node != null; node = node.next) {
                if (node.item == null)
                    return index;
                index++;
            }
        } else {
            for (ListNode<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item))
                    return index;
                index++;
            }
        }
        return -1;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        int index = size;
        Object[] obj = c.toArray();
        int numNew = obj.length;
        if (numNew == 0)
            return false;

        ListNode<E> pred;
        ListNode<E> succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : obj) {
            @SuppressWarnings("unchecked") E elem = (E) o;
            ListNode<E> newNode = new ListNode<>(pred, elem, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
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

    private class MyIterator<E> implements Iterator<E> {
        private ListNode<E> lastReturned;
        private ListNode<E> next;
        private int nextIndex;

        MyIterator(ListNode<E> first) {
            next = first;
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }
    }
}


