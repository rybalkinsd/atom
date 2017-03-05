package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> first;
    private ListNode<E> last;
    private int size;

    public CustomLinkedList() {
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
        for (ListNode<E> current = first;; current = current.getNext()) {
            if ((o) == current.getElement()) {
                return true;
            }
            if (current.getNext() == null)
                break;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<>(this.first);
    }

    @Override
    public boolean add(E e) {
        ListNode<E> lastNode = last;
        ListNode<E> newNode = new ListNode<>(lastNode, e, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.setNext(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (ListNode<E> current = first;; current = current.getNext()) {
            if ((o) == current.getElement()) {
                ListNode<E> prevNode = current.getPrev();
                ListNode<E> nextNode = current.getNext();
                if (prevNode == null) {
                    first = nextNode;
                } else {
                    prevNode.setNext(nextNode);
                    current.setPrev(null);
                }
                if (nextNode == null) {
                    last = prevNode;
                } else {
                    nextNode.setPrev(prevNode);
                    current.setNext(null);
                }
                current.setElement(null);
                size--;
                return true;
            }
            if (current.getNext() == null)
                break;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean isContains = true;
        Object[] array = c.toArray();
        int numNew = array.length;
        if (numNew == 0)
            return false;

        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == ((E) array[0])) {
                break;
            }
        }
        for (int i = 1; i < array.length; i++) {
            if (iterator.hasNext()) {
                if (iterator.next() != array[i]) {
                    isContains = false;
                    break;
                }
            }
        }
        return isContains;
    }

    @Override
    public void clear() {
        for (ListNode<E> current = first; current != null;) {
            current.setElement(null);
            current.setPrev(null);
            ListNode<E> nextNode = current.getNext();
            current.setNext(null);
            current = nextNode;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        E element = null;
        if (index < 0 || index > size)
            throw new ArrayIndexOutOfBoundsException(index);
        else {
            Iterator<E> iterator = this.iterator();
            int position = 0;
            while (iterator.hasNext()) {
                if (position == index) {
                    element = iterator.next();
                    break;
                }
            }
        }
        return element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (ListNode<E> current = first;; current = current.getNext()) {
                if (current.getElement() == null) {
                    return index;
                }
                if (current.getNext() == null) {
                    break;
                }
                index++;
            }
        } else {
            for (ListNode<E> current = first;; current = current.getNext()) {
                if (o.equals(current.getElement())) {
                    return index;
                }
                if (current.getNext() == null) {
                    break;
                }
                index++;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] array = c.toArray();
        int numNew = array.length;
        if (numNew == 0)
            return false;

        for (E element: c) {
            add(element);
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
