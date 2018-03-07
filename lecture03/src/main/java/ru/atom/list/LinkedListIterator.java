package ru.atom.list;

import java.util.Iterator;

/**
 * Created by imakarycheva on 07.03.18.
 */
public class LinkedListIterator<E> implements Iterator<E> {

    private int position = 0;
    private final CustomLinkedList<E> list;
    private ListNode<E> currNode;

    public LinkedListIterator(CustomLinkedList<E> list, ListNode<E> first) {
        this.list = list;
        currNode = first;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public E next() {
        E content = currNode.getContent();
        position++;
        currNode = currNode.getNext();
        return content;
    }
}
