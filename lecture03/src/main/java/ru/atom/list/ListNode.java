package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    E element ;
    E next ;
    E prev ;

    ListNode() {
        this.element = null;
        this.next = this.element;
        this.prev = this.element ;
    }
   // ListNode(E element, E next, E prev) {
    //    this.element = element;
     //   this.next = next;
    //    this.prev = prev;
   // }


}
