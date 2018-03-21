package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {
    ListNode<E> beg;
    ListNode<E> end;

    CustomLinkedList() {
        beg = null;
        end = null;
    }

    @Override
    public int size() {
        int ret = 0;
        ListNode<E> cur = end;
        while (cur != null) {
            ++ret;
            cur = cur.next;
        }
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return beg == null;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> cur = end;
        while (cur != null) {
            if (cur.value == (E) o) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyCustomLinkedLinkList();
    }

    private class MyCustomLinkedLinkList implements Iterator<E> {

        private ListNode<E> cur;

        public MyCustomLinkedLinkList() {
            cur = CustomLinkedList.this.end;
        }

        public boolean hasNext() {
            return cur != null;
        }

        public E next() {
            if (!this.hasNext()) {
                throw new UnsupportedOperationException();
            }
            E value = cur.value;
            cur = cur.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<>();
        newNode.prev = beg;
        newNode.value = e;
        if (beg != null) {
            beg.next = newNode;
        }
        beg = newNode;
        if (end == null) {
            end = newNode;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> cur = end;

        while (cur != null) {
            if (cur.value == (E) o) {
                if (cur.prev != null) {
                    cur.prev.next = cur.next;
                }
                if (cur.next != null) {
                    cur.next.prev = cur.prev;
                }
                if (end == cur) {
                    end = cur.next;
                }
                if (beg == cur) {
                    beg = cur.prev;
                }
                return true;
            }
            cur = cur.next;
        }

        return false;
    }

    @Override
    public void clear() {
        beg = null;
        end = null;
    }

    @Override
    public E get(int index) {
        ListNode<E> cur = end;
        for (int i = 0; i < index; cur = cur.next) { }
        return cur.value;
    }

    @Override
    public int indexOf(Object o) {
        int ret = 0;
        ListNode<E> cur = end;
        while (cur != null) {
            if (cur.value == (E) o) {
                return ret;
            }
            cur = cur.next;
            ++ret;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object o : c) {
            E tmp = (E) o;
            add(tmp);
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
