package ru.atom.list;

import java.util.List;
import java.util.Objects;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element;
    ListNode<E> prev;
    ListNode<E> next;
    private ListNode<E> size;

    public  ListNode(E element, ListNode<E> prev, ListNode<E> next){
      this.element = element;
      this.prev = prev;
      this.next = next;
    }



}
