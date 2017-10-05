package ru.atom.list;

import java.util.Iterator;

public class CllIterator<E> implements Iterator<E> {
    private ListNode<E> curNode;

    public CllIterator(ListNode<E> rootNode) {
        this.curNode = rootNode;
    }

    @Override
    public boolean hasNext() {
        return curNode.next.var != null;
    }

    @Override
    public E next() {
        curNode = curNode.next;
        return curNode.var;
    }
}
