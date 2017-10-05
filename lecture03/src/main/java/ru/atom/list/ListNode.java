package ru.atom.list;

import java.util.ListIterator;

/**
 * Contains ref to next node, previous node and value
 */
public class ListNode<E> {

    private E element;
    public ListNode<E> next;
    public ListNode<E> previous;

    public ListNode(E e) {
        this.element = e;
    }

    public ListNode(E e, ListNode<E> next, ListNode<E> previous) {
        this.element = e;
        this.next = next;
        this.previous = previous;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setPrevious(ListNode<E> previous) {
        this.previous = previous;
    }

    public ListNode<E> getPrevious() {
        return previous;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListNode<?> listNode = (ListNode<?>) o;

        return element != null ? element.equals(listNode.element) : listNode.element == null;
    }
}