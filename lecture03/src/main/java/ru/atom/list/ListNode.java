package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E date;
    private ListNode next;
    private ListNode previous;

    public ListNode(E a) {
        date = a;
        next = null;
        previous = null;
    }

    public E getDate() {
        return date;
    }

    public void setDate(E date) {
        this.date = date;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setNext(ListNode node) {
        next = node;
    }

    public void setPrevious(ListNode node) {
        previous = node;
    }

    public ListNode<E> getPrevious() {
        return previous;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListNode) {
            ListNode listnode = (ListNode) obj;
            if (next == listnode.next) return true;
        }
        return false;
    }
}
