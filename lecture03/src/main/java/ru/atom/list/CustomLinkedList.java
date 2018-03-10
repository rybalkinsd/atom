package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> head;
    private int size = 0;

    public class CommonListIterator<E> implements Iterator<E> {

        private int cur = 0;

        @Override
        public boolean hasNext() {
            return cur < size;
        }

        @Override
        public E next() {
            E temp = (E) get(cur);
            cur++;
            return temp;
        }

        @Override
        public void remove() {
            ListNode<E> temp = (ListNode<E>) head;
            for (int i = 0; i < cur - 1; i++)
                temp = temp.getNext();
            temp.getNext().setPrevious(temp.getPrevious());
            temp.getPrevious().setNext(temp.getNext());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    @Override
    public boolean contains(Object o) {
        E data = (E) o;
        ListNode<E> cur = head;
        if (cur.getDate().equals(data)) return true;
        if (cur.getNext().equals(cur)) return false;
        for (cur = cur.getNext(); cur != head; cur = cur.getNext()) {
            if (cur.getDate().equals(data)) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CommonListIterator<E>();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> addable = new ListNode(e);
        if (size == 0) {
            head = addable;
            head.setNext(head);
            head.setPrevious(head);
            size++;
            return true;
        }
        ListNode<E> last = head.getPrevious();
        last.setNext(addable);
        addable.setNext(head);
        head.setPrevious(addable);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) return false;
        if (contains(o)) {
            E data = (E) o;
            ListNode<E> cur = head;
            ListNode<E> curNext;
            ListNode<E> curPrevious;
            if (cur.getDate() == data) {
                curNext = cur.getNext();
                curPrevious = cur.getPrevious();
                curNext.setPrevious(curPrevious);
                curPrevious.setNext(curNext);
                head = curNext;
                size--;
                return true;
            }
            for (;cur.getNext() != head; cur = cur.getNext()) {
                if (cur.getDate() == data) {
                    curNext = cur.getNext();
                    curPrevious = cur.getPrevious();
                    curNext.setPrevious(curPrevious);
                    curPrevious.setNext(curNext);
                    size--;
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        ListNode<E> cur = head;
        for (;index != 0;index--) {
            cur = cur.getNext();
        }
        return cur.getDate();
    }

    @Override
    public int indexOf(Object o) {
        E data = (E) o;
        int counter = 0;
        ListNode<E> cur = head;
        if (cur.getDate().equals(data)) return counter;
        for (cur = cur.getNext(); cur != head; cur = cur.getNext()) {
            if (cur.getDate().equals(data)) return counter + 1;
            counter++;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] temp = c.toArray();
        if (temp.length == 0) return false;
        for (int i = 0; i < temp.length; i++)
            add((E) temp[i]);
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
