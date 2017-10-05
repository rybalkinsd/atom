package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private ListNode<E> prev;
    private ListNode<E> next;
    private E object;


    public ListNode(E object) {
        this.object = object;
    }

    public ListNode(E object, ListNode<E> prev) {
        this.object = object;
        this.prev = prev;
        this.prev.setNext(this);
    }

    public ListNode(E object, ListNode<E> prev, ListNode<E> next) {
        this.object = object;
        this.prev = prev;
        this.next = next;
        this.next.prev = this;
        this.prev.next = this;
    }

    public void setNext(ListNode<E> o) {
        this.next = o;
    }

    public void setPrev(ListNode<E> o) {
        this.prev = o;
    }

    public E get() {
        return this.object;
    }

    public void set(E e) {
        this.object = e;
    }

    public boolean hasNext() {
        if (this.next != (ListNode<E>) null) return true;
        return false;
    }

    public boolean hasPrev() {
        if (this.prev != (ListNode<E>) null) return true;
        return false;
    }

    public ListNode<E> getNext() {
        return this.next;
    }

    public ListNode<E> getPrev() {
        return this.prev;
    }
}
