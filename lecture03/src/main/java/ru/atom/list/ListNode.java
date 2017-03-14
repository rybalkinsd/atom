package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E value;
    private ListNode<E> previous;
    private ListNode<E> next;

    public ListNode(E value, ListNode<E> previous, ListNode<E> next) {
        this.value = value;
        this.previous = previous;
        this.next = next;
    }

    public E getValue() {
        return value;
    }

    public ListNode<E> getPrevious() {
        return previous;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setPrevious(ListNode<E> previous) {
        this.previous = previous;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }
}
