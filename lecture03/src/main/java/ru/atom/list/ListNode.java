
package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E value;
    ListNode<E> next;
    ListNode<E> prev;

    ListNode(ListNode<E> prev, E value, ListNode<E> next) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

}
