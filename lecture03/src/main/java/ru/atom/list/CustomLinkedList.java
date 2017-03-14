package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header = null;

    @Override
    public int size() {
        if (this.header == null) {
            return 0;
        }
        int counter = 0;
        ListNode<E> el = this.header;
        do {
            el = el.getNextValue();
            counter += 1;
        } while (!el.equals(header));
        return counter;
    }

    @Override
    public boolean isEmpty() {
        if (this.header == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        if (this.header == null) {
            return false;
        } else {
            ListNode<E> el = this.header;
            do {
                if (el.getValue().equals(o)) {
                    return true;
                }
                el = el.getNextValue();
            } while (!el.getNextValue().equals(header));
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            ListNode<E> element = header;
            boolean notTouchHeader = true;

            @Override
            public boolean hasNext() {
                if (header == null) {
                    return false;
                }
                return !element.getNextValue().equals(header);
            }

            @Override
            public E next() {
                if (notTouchHeader) {
                    notTouchHeader = false;
                    return element.getValue();
                } else if (hasNext()) {
                    element = element.getNextValue();
                    return element.getValue();
                } else {
                    throw new IndexOutOfBoundsException();
                }
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (this.header == null) {
            ListNode<E> node = new ListNode<E>(e);
            this.header = node;
        } else {
            ListNode<E> node = new ListNode<E>(e);
            node.setNextValue(header);
            node.setPastValue(header.getPastValue());
            header.getPastValue().setNextValue(node);
            header.setPastValue(node);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> el = this.header.getNextValue();
        boolean bolka = false;
        while (!el.equals(header)) {
            if (el.getValue().equals(o)) {
                ListNode<E> prev = el.getPastValue();
                ListNode<E> next = el.getNextValue();
                prev.setNextValue(next);
                next.setPastValue(prev);
                bolka = true;
            }
            el = el.getNextValue();
        }
        if (this.header.getValue().equals(o)) {
            if (this.size() == 1) {
                this.header = null;
                bolka = true;
            } else {
                ListNode<E> prev = header.getPastValue();
                ListNode<E> next = header.getNextValue();
                prev.setNextValue(next);
                next.setPastValue(prev);
                this.header = prev;
                bolka = true;
            }
        }
        return bolka;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        ListNode<E> elem = this.header;
        while (elem.getNextValue() != null) {
            ListNode<E> next = elem.getNextValue();
            elem = null;
            elem = next;
        }
    }

    @Override
    public E get(int index) {
        if (this.size() >= index) {
            throw new IndexOutOfBoundsException();
        } else {
            ListNode<E> elem = this.header;
            for (int i = 0; i < index; i++) {
                elem = elem.getNextValue();
            }
            return elem.getValue();
        }
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> elem = this.header;
        for (int i = 0; i < this.size(); i++) {
            if (elem.getValue().equals(o)) {
                return i;
            }
            elem = elem.getNextValue();
        }
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            for (Object o: c) {
                this.add((E) o);
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
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
