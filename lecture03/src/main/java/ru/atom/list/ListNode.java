package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private E element;
    private ListNode<E> prev;
    private ListNode<E> next;

    public ListNode<E> (E element, ListNode<E> prev, ListNode<E> next) {
        this.element = element;
        this.prev    = prev;
        this.next    = next;
    }

    public ListNode<E> getElement(){
        return element;
    }

    public boolean setElement(ListNode<E> element) {
        this.element = element;
        return this.element.equals(element);
    }

    public ListNode<E> getPrev(){
        return prev;
    }

    public boolean setPrev(ListNode<E> prev) {
        return this.element.equals(element);
    }

    public ListNode<E> getNext(){
        return next;
    }

    public boolean setNext(ListNode<E> next) {
        return this.next.equals(next);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    }
}
