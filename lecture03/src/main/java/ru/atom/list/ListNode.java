package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element;
    ListNode<E> nextNode;
    ListNode<E> prevNode;

    public ListNode(E element, ListNode<E> nextNode, ListNode<E> prevNode) {
        this.element = element;
        this.nextNode = nextNode;
        this.prevNode = prevNode;
    }
}
