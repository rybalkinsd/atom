package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E value;
    private ListNode<E> nextValue;
    private ListNode<E> pastValue;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public ListNode<E> getNextValue() {
        return nextValue;
    }

    public void setNextValue(ListNode<E> nextValue) {
        this.nextValue = nextValue;
    }

    public ListNode<E> getPastValue() {
        return pastValue;
    }

    public void setPastValue(ListNode<E> pastValue) {
        this.pastValue = pastValue;
    }

    public ListNode(E value, ListNode<E> nextValue, ListNode<E> pastValue) {
        this.value = value;
        this.nextValue = nextValue;
        this.pastValue = pastValue;
    }

    public ListNode(E value) {
        this.value = value;
        this.nextValue = this;
        this.pastValue = this;
    }
}
