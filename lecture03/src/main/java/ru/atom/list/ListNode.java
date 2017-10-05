package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public  class  ListNode<E> {
    E value;
    ListNode<E> next;
    ListNode<E> prev;

    public ListNode() {
        this.next = null;
        this.prev = null;
        this.value = null;
    }

    public ListNode(E value, ListNode<E> next, ListNode<E> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

}
