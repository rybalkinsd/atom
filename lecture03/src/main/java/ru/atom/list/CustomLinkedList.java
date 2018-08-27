package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CustomLinkedList<E> implements List<E> {

    private ListNode<E> first;
    private int size;

    public CustomLinkedList() {
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode<E> current = this.first;
        for(int i = 0; i < this.size; i++){
            if(current.getItem().equals(o)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Listerator();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> firstOld = first;
        ListNode<E> newNode = new ListNode<>(null, e, firstOld);
        this.first = newNode;
        if(firstOld != null){
            firstOld.setPrev(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> current = this.first;
        for(int i = this.size - 1; i > 0; i--){
            if(current.getItem().equals(o)){
                break;
            }
            current = current.getNext();
        }
        ListNode<E> prevNode = current.getPrev();
        ListNode<E> nextNode = current.getNext();
        if(nextNode != null) {
            nextNode.setPrev(prevNode);
        }
        if(prevNode != null) {
            prevNode.setNext(nextNode);
        }
        current.setNext(null);
        current.setPrev(null);
        size--;
        return true;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        ListNode<E> current = this.first;
        for(int i = this.size - 1; i > index; i--){
            current = current.getNext();
        }
        return current.getItem();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> current = this.first;
        for(int i = this.size - 1; i > 0; i--){
            if(current.getItem().equals(o)){
                return i;
            }
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E item : c){
            add(item);
        }
        return true;
    }


    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return true;
            }
        }
        return true;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public void add(int index, E element) {
    }

    /**
     * Do not implement
     */
    @Override
    public E remove(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Do not implement
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public E set(int index, E element) {
        return null;
    }

    private class Listerator implements Iterator<E> {

        private int cursor;

        public Listerator() {
            cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return this.cursor != CustomLinkedList.this.size();
        }

        @Override
        public E next() {
            return CustomLinkedList.this.get(cursor++);
        }
    }
}
