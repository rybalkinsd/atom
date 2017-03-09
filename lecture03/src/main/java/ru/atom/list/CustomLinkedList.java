package ru.atom.list;


import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E>, Iterable<E> {

    private ListNode<E> head;

    CustomLinkedList() {
        this.head = new ListNode<>();
    }

    @Override
    public int size() {
        int numb = 0;
        ListNode temp = head.next;
        while (temp != head) {
            numb++;
            temp = temp.next;
        }
        return numb;
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
    }

    @Override
    public boolean contains(Object o) {
        ListNode temp = head.next;
        for (int i = 0; i < this.size(); i++) {
            if (temp.element.equals(o)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>(head);
    }

    @Override
    public boolean add(E e) {
        ListNode temp = new ListNode(e, head, null);
        if (head.prev == null) {
            head.prev = temp;
            head.next = temp;
            temp.prev = head;
            temp.next = head;
        } else {
            temp.prev = head.prev;
            head.prev.next = temp;
            head.prev = temp;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!this.contains(o)) {
            return false;
        } else {
            ListNode temp = head.next;
            for (int i = 0; i < this.size(); i++) {
                if (temp.equals(o)) {
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                    temp.element = null;
                    break;
                }
                temp = temp.next;
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Iterator<?> temp = c.iterator(); temp.hasNext();) {
            Object next = temp.next();
            if (!this.contains(next)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for (Iterator<E> temp = this.iterator(); temp.hasNext();) {
            E next = temp.next();
            this.remove(next);
        }
    }

    @Override
    public E get(int index) {
        ListNode<E> temp = head;
        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }
        return temp.element;
    }

    @Override
    public int indexOf(Object o) {
        if (!this.contains(o)) {
            return -1;
        } else {
            ListNode temp = head.next;
            for (int i = 0; i < this.size(); i++) {
                if (temp.equals(o)) {
                    return i;
                }
                temp = temp.next;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E el : c) {
            this.add(el);
        }
        return true;
    }






    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */

    /**
     * Do not implement(completed)
     *
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            for (Iterator<? extends E> temp = c.iterator(); temp.hasNext();) {
                E next = temp.next();
                this.add(index, next);
                index++;
            }
        }
        return true;
    }

    /**
     * Do not implement(completed)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            return false;
        } else {
            for (Iterator<E> temp = this.iterator(); temp.hasNext();) {
                E obj = temp.next();
                if (c.contains(obj)) {
                    temp.remove();
                }
            }
        }
        return true;
    }

    /**
     * Do not implement(completed)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            return false;
        } else {
            for (Iterator<E> temp = this.iterator(); temp.hasNext();) {
                E obj = temp.next();
                if (!c.contains(obj)) {
                    temp.remove();
                }
            }
        }
        return true;
    }

    /**
     * Do not implement(completed)
     */
    @Override
    public void add(int index, E element) {
        ListNode<E> temp = head;
        if (index == this.size()) {
            ListNode<E> el = new ListNode<E>(element, head, head.prev);
            head.prev.next = el;
            head.prev = el;
        } else {
            for (int i = 0; i <= index; i++) {
                temp = temp.next;
            }
            ListNode<E> el = new ListNode<E>(element, temp, temp.prev);
            temp.prev.next = el;
            temp.prev = el;
        }

    }

    /**
     * Do not implement(completed)
     */
    @Override
    public E remove(int index) {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }
        int position = 0;
        for (Iterator<E> temp = this.iterator(); temp.hasNext();) {
            E next = temp.next();
            position++;
            if (position > index) {
                temp.remove();
                return next;
            }
        }
        return null;
    }

    /**
     * Do not implement(completed)
     */
    @Override
    public int lastIndexOf(Object o) {
        if (!this.contains(o)) {
            return -1;
        } else {
            ListNode temp = head.prev;
            for (int i = this.size() - 1; i <= 0; i--) {
                if (temp.equals(o)) {
                    return i;
                }
                temp = temp.prev;
            }
        }
        return -1;
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
     * Do not implement(completed)
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> sub = new CustomLinkedList<E>();
        if (fromIndex > toIndex || fromIndex < 0 || toIndex >= this.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            int position = 0;
            for (E temp : this) {
                if (position >= fromIndex && position < toIndex) {
                    sub.add(temp);
                }
                position++;
            }
        }
        return sub;
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