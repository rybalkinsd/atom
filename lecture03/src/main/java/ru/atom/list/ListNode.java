package ru.atom.list;

import java.awt.*;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E elem;
}
