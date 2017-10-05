package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public final E element;
    ListNode<E> next; //default
    ListNode<E> prev; //default

    ListNode() {
        element = null;
        next = prev = null;
    }

    ListNode(E element) {
        this.element = element;
        next = prev = null;
    }

    ListNode<E> getNext() {
        return next;
    }

    ListNode<E> getPrev() {
        return prev;
    }

    void add(ListNode<E> node) {
        ListNode<E> rightNode = next;
        connect(this, node);
        connect(node, rightNode);
    }

    static <E> void connect(ListNode<E> leftNode, ListNode<E> rightNode) {
        leftNode.next = rightNode;
        if (rightNode != null) {
            rightNode.prev = leftNode;
        }
    }
}
