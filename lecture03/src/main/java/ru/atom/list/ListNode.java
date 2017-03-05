package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E elem;
    private ListNode<E> next = null;

    public ListNode(E elem) {
        this.elem = elem;
        this.next = null;
    }

    public void setValue(E elem) {
        this.elem = elem;
    }

    public E getValue() {
        return this.elem;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public ListNode<E> getNext() {
        return this.next;
    }
}
