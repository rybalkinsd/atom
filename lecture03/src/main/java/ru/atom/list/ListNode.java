package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public E elem;
    public ListNode<E> next = null;

    public ListNode(E elem) {
        this.elem = elem;
        this.next = null;
    }

    public static void main(String[] args) {
        ListNode<Integer> tmp = new ListNode<>(Integer.valueOf(10));
    }
}
