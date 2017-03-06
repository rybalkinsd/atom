package ru.atom.list;
/**
 * Contains ref to next node, prev node and value
 */

public class ListNode<E> {

    private E element;
    private ListNode<E> next = null;
    private ListNode<E> prev = null;

    ListNode<E> getNext() {
        return next;
    }

    ListNode<E> getPrev() {
        return prev;
    }

    ListNode<E> getHead() {
        ListNode<E> elem = this;
        while (elem.getPrev() != null) {
            elem = elem.getPrev();
        }
        return elem;
    }

    E getElement() {
        return element;
    }

    void setNext(ListNode<E> next) {
        this.next = next;
    }

    void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    void setElement(E element) {
        this.element = element;
    }




}
