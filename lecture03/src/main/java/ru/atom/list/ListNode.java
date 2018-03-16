package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private E data;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode(E element) {
        this.data = element;
        this.next = null;
        this.prev = null;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E newdata) {
        this.data = newdata;
    }


    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setNext(ListNode<E> nextNode) {
        this.next = nextNode;
    }

    public void setPrev(ListNode<E> prevNode) {

        this.prev = prevNode;
    }


}
