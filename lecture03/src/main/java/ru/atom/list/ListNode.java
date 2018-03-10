package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E date;
    ListNode<E> next;
    ListNode<E> previous;

    ListNode(E data) {
        date = data;
        next = null;
        previous = null;
    }

    public void setDate(E date) {
        this.date = date;
    }

    public E getDate() {
        return date;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrevious() {
        return previous;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrevious(ListNode<E> previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object obj) {
        ListNode<E> temp = (ListNode<E>) obj;
        if (next == temp.next) return true;
        return false;
    }
}
