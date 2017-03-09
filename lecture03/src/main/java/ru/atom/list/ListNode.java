package ru.atom.list;


/**
 * Contains ref to next node, prev node and value
 */
class ListNode<E> {
    private E value;
    private ListNode<E> next;
    private ListNode<E> prev;

    ListNode() {
        this(null);
    }

    ListNode(E value) {
        this.value = value;
        this.prev = this;
        this.next = this;
    }

    E getValue() {
        return value;
    }

    void setValue(E value) {
        this.value = value;
    }

    ListNode<E> getNext() {
        return next;
    }

    void setNext(ListNode<E> next) {
        this.next = next;
    }

    ListNode<E> getPrev() {
        return prev;
    }

    void setPrev(ListNode<E> old) {
        this.prev = old;
    }
}
