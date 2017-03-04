package ru.atom.list;

import java.util.Objects;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element;
    ListNode<E> next = this;
    ListNode<E> prev = this;
}
