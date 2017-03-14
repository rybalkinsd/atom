package ru.atom.list;

public class ListNode<E> {
    private E value;
    private ListNode nextIndex;
    private ListNode prevIndex;

    public ListNode(E value, ListNode nextIndex, ListNode prevIndex) {
        this.value = value;
        this.nextIndex = nextIndex;
        this.prevIndex = prevIndex;
    }

    public ListNode getNextIndex() {
        return nextIndex;
    }

    public <E> void setNextIndex(ListNode<E> nextIndex) {
        this.nextIndex = nextIndex;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public <E> void setPrevIndex(ListNode<E> prevIndex) {
        this.prevIndex = prevIndex;
    }

    public ListNode getPrevIndex() {
        return prevIndex;
    }
}
