package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    ListNode head;

    public CustomLinkedList() {
        head = null;
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }
        int sz = 0;
        ListNode tmp = head;
        while (tmp != null) {
            tmp = tmp.getNext();
            ++sz;
            if (tmp == head) {
                break;
            }
        }
        return sz;
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        E elem = (E)o;
        ListNode tmp = head;
        while (tmp != null) {
            if (tmp.getElem().equals(elem)) {
                return true;
            } else {
                tmp = tmp.getNext();
            }
            if (tmp == head) {
                break;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }
        };
        return it;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            return false;
        } else if (size() == 0) {
            head = new ListNode(e);
            return true;
        } else {
            ListNode tmp = head.getPrev();
            ListNode el = new ListNode(e);
            el.setPrev(tmp);
            el.setNext(head);
            tmp.setNext(el);
            head.setPrev(el);
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        E elem = (E)o;
        ListNode tmp = head;
        ListNode prev = head;
        while (tmp != null) {
            if (tmp.getElem().equals(elem)) {
                if (tmp == head) {
                    if (tmp.getNext() == head) {
                        head = null;
                    } else {
                        head = tmp.getNext();
                        prev = tmp.getPrev();
                        prev.setNext(head);
                    }
                } else {
                    prev = tmp.getPrev();
                    prev.setNext(tmp.getNext());
                    tmp.getNext().setPrev(prev);
                }
                return true;
            } else {
                tmp = tmp.getNext();
            }
            if (tmp == head) {
                break;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            return false;
        }
        int sz = c.size();
        E[] tmp = (E[]) c.toArray();
        for (int i = 0; i < sz; ++i) {
            if (!contains(tmp[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public E get(int index) {
        if (size() <= index) {
            return null;
        } else {
            ListNode tmp = head;
            int ind = 0;
            while (ind < index) {
                tmp = tmp.getNext();
                ++ind;
            }
            return (E)tmp.getElem();
        }
    }

    @Override
    public int indexOf(Object o) {
        E elem = (E)o;
        ListNode tmp = head;
        int rez = 0;
        while (tmp != null) {
            if (tmp.getElem().equals(elem)) {
                return rez;
            } else {
                tmp = tmp.getNext();
                ++rez;
            }
            if (tmp == head) {
                break;
            }
        }
        return rez;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            return false;
        }
        int sz = c.size();
        E[] tmp = (E[]) c.toArray();
        for (int i = 0; i < sz; ++i) {
            ListNode prev = head.getPrev();
            ListNode el = new ListNode(tmp[i]);
            el.setPrev(prev);
            el.setNext(head);
            prev.setNext(el);
            head.setPrev(el);
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
