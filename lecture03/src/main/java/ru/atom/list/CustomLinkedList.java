package ru.atom.list;

import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.ListIterator;


public class CustomLinkedList<E> extends ListNode<E> implements List<E> {
    @Override
    public Iterator<E> iterator() {
        ListNode<E> spas = new ListNode<E>();
        spas.setNext(this);
        spas.setPrev(null);
        ListNode<E> header = spas;
        if (this.isEmpty()) {
            return Collections.<E>emptyList().iterator();
        } else {
            return new Iterator<E>() {
                private ListNode<E> currentNode = header;
                @Override
                public boolean hasNext() {
                    return currentNode.getNext() != null;
                }

                @Override
                public E next() {
                    if (hasNext()) {
                        currentNode = currentNode.getNext();
                        return   currentNode.getElement();
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }
    }

    @Override
    public int size() {
        ListNode<E> nya = this;
        int inta = 1;
        while (nya.getNext() != null) {
            inta++;
            nya = nya.getNext();
        }
        return inta;
    }

    @Override
    public boolean isEmpty() {
        return this == null;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem: c) {
            if (indexOf((E)elem) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(E e) {

        if (this.getElement() == null) {
            this.setPrev(null);
            this.setElement(e);
            return true;
        } else {
            ListNode<E> nya = new ListNode<E>();
            ListNode<E> end = this;
            nya.setElement(e);
            while (end.getNext() != null) {
                end = end.getNext();
            }
            end.setNext(nya);
            nya.setPrev(end);
            nya.setNext(null);

            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (getelem(indexOf(o)) != null) {
            ListNode<E> lish  = getelem(indexOf(o));
            if (lish.getPrev() != null) {
                if (lish.getNext() != null) {
                    lish.getPrev().setNext(lish.getNext());
                    lish.getNext().setPrev(lish.getPrev());
                    return true;
                } else {
                    lish.getPrev().setNext(null);
                    lish.setNext(null);
                    lish.setPrev(null);
                    lish.setElement(null);
                    return true;
                }
            } else {
                if (this.getNext() != null) {
                    this.setElement(this.getNext().getElement());
                    this.getNext().setPrev(null);
                    this.setNext(this.getNext().getNext());
                    if (this.getNext() != null) {
                        this.getNext().setPrev(this);
                    }
                    return true;
                } else {
                    this.setElement(null);
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public void clear() {
        this.setNext(null);
        this.setPrev(null);
        this.setElement(null);
    }

    @Override
    public E get(int index) {
        ListNode<E> elem = this.getHead();
        for (int i = 1; i <= index; i++) {
            if (elem.getNext() != null) {
                elem = elem.getNext();
            } else {
                return null;
            }

        }
        return  elem.getElement();
    }

    private ListNode<E> getelem(int index) {
        if (index >= 0) {
            ListNode<E> elem = this.getHead();
            for (int i = 1; i <= index; i++) {
                if (elem.getNext() != null) {
                    elem = elem.getNext();
                } else {
                    return null;
                }
            }
            return  elem;
        } else {
            return null;
        }
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> elem = this.getHead();
        o = (E) o;
        int inta = 0;
        while (elem != null) {
            if (elem.getElement() == o) {
                return inta;
            } else {
                inta++;
                elem = elem.getNext();
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E o : c) {
            if (o != null) {
                add(o);

            } else {
                return false;
            }
        }
        return true;
    }

    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */

    /**
     * Do not implement
     */

    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * Do not implement
     */

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */

    public void add(int index, E element) {
    }

    /**
     * Do not implement
     */

    public E remove(int index) {
        return null;
    }

    /**
     * Do not implement
     */

    public int lastIndexOf(Object o) {
        return 0;
    }

    /**
     * Do not implement
     */

    public ListIterator<E> listIterator() {
        return null;
    }

    /**
     * Do not implement
     */

    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * Do not implement
     */

    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Do not implement
     */

    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Do not implement
     */

    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Do not implement
     */

    public E set(int index, E element) {
        return null;
    }

}
