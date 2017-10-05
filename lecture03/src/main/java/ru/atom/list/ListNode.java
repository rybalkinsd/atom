package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
class ListNode<E> {
    public E var = null;
    public ListNode<E> next = this;
    public ListNode<E> prev = this;

    public ListNode() {
    }

    public ListNode(E var) {
        this.var = var;
    }
}