package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode(E element, ListNode<E> prev, ListNode<E> next) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

    public ListNode<E> getNext() { //дает ссылку на следующий элемент
        return next;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

}
