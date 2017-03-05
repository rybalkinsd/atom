package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

/**
 * Created by BBPax on 05.03.17.
 */
public class MyIterator<E> implements Iterator {
    private ListNode<E> current;

    MyIterator(ListNode<E> current) {
        this.current = current;
    }

    @Override
    public E next() {
        this.current = this.current.next;
        return this.current.element;
    }

    @Override
    public boolean hasNext() {
        return current.next.element != null;
    }
}
