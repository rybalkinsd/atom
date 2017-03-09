package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element;
    ListNode<E> next;
    ListNode<E> prev;

    ListNode() {
        E element = null;
        next = this;
        prev = this;
    }

    ListNode(E element, ListNode<E> next, ListNode<E> prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || element.getClass() != obj.getClass()) {
            return false;
        } else {
            E el = (E)obj;
            return element.equals(el);
        }
    }
}
