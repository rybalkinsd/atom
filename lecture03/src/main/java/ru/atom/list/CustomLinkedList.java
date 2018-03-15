package ru.atom.list;


import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;


public class CustomLinkedList<E> implements List<E> {
    private ListNode<E> header;

    public CustomLinkedList() {
        header = new ListNode<E>();
    }

    class CustomIterator implements Iterator<E> {
        ListNode<E> curr;

        CustomIterator() {
            curr = CustomLinkedList.this.header;
        }

        public boolean hasNext() {
            return (curr.getNext() != CustomLinkedList.this.header
                    && curr.getNext() != null) ? true : false;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            curr = curr.getNext();
            return curr.getData();
        }

    }

    class CustomListIterator implements ListIterator<E> {
        int currIndex;
        ListNode<E> curr;

        CustomListIterator() {
            currIndex = -1;
            curr = CustomLinkedList.this.header;
        }

        public boolean hasNext() {
            return (curr.getNext() != CustomLinkedList.this.header
                    && curr.getNext() != null) ? true : false;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            curr = curr.getNext();
            ++currIndex;
            return curr.getData();
        }

        public boolean hasPrevious() {
            return (curr.getPrev() != CustomLinkedList.this.header
                    && curr.getPrev() != null) ? true : false;
        }

        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            curr = curr.getPrev();
            --currIndex;
            return curr.getData();
        }

        public int nextIndex() {
            if (!hasNext())
                return size();
            return currIndex + 1;
        }

        public int previousIndex() {
            if (!hasPrevious())
                return -1;
            return currIndex - 1;
        }

        public void add(E element) {
            ListNode<E> prev = curr.getPrev();
            ListNode<E> newNode = new ListNode<E>(element, curr, prev);
            prev.setNext(newNode);
            curr.setPrev(newNode);
            ++currIndex;
        }

        public void set(E element) {
            if (curr == CustomLinkedList.this.header)
                throw new UnsupportedOperationException();
            curr.setData(element);
        }

        public void remove() {
            if (curr == CustomLinkedList.this.header)
                throw new UnsupportedOperationException();
            ListNode<E> prev = curr.getPrev();
            ListNode<E> next = curr.getNext();
            prev.setNext(next);
            next.setPrev(prev);
            curr.setPrev(null);
            curr.setNext(null);
            curr = prev;
        }
    }

    @Override
    public int size() {
        int result = 0;
        ListNode<E> curr = header.getNext();
        while (curr != header) {
            ++result;
            curr = curr.getNext();
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return header == header.getNext();
    }

    @Override
    public boolean contains(Object o) {
        E element = (E) o;
        for (E object : this) {
            if (object == element)
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public boolean add(E e) {
        ListNode<E> lastNode = header.getPrev();
        ListNode<E> node = new ListNode();
        lastNode.setNext(node);
        node.setData(e);
        node.setPrev(lastNode);
        node.setNext(header);
        header.setPrev(node);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E element = (E) o;
        ListNode<E> curr = header.getNext();
        while (curr != header) {
            if (curr.getData().equals(element)) {
                ListNode<E> prev = curr.getPrev();
                ListNode<E> next = curr.getNext();
                // delete all links to current ListNode
                prev.setNext(next);
                next.setPrev(prev);
                curr.setNext(null);
                curr.setPrev(null);
                curr = null;
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        header.setPrev(header);
        header.setNext(header);
        // All ListNodes will be deleted by GC
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > (this.size() - 1))
            throw new IndexOutOfBoundsException();
        int currIndex = 0;
        E result = null;
        for (E element : this) {
            if (currIndex == index) {
                result = element;
                break;
            }
            ++currIndex;
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        E element = (E) o;
        int index = 0;
        for (E object : this) {
            if (object.equals(element))
                return index;
            ++index;
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E object : c) {
            if (!this.add(object))
                return false;
        }
        return true;
    }

    public void print() {
        for (E element : this) {
            System.out.print(element);
            System.out.print(' ');
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CustomLinkedList<Integer> intList = new CustomLinkedList();
        intList.add(42);
        intList.add(38);
        intList.addAll(Arrays.asList(1, 2, 2, 3, 3, 1));
        intList.print();
        intList.retainAll(Arrays.asList(1, 2));
        intList.print();
    }

    /*
      Extra methods
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > this.size())
            throw new IndexOutOfBoundsException();

        if (index == this.size()) {
            for (E object : c) {
                if (!this.add(object))
                    return false;
            }
            return true;
        }

        ListNode<E> curr = header.getNext();
        ListNode<E> oldLast = header.getPrev();
        int currIndex = 0;
        while (curr != header) {
            if (currIndex == index) {
                ListNode<E> prev = curr.getPrev();
                prev.setNext(header);
                header.setPrev(prev);
                // add elements from collection
                for (E object : c) {
                    if (!this.add(object))
                        return false;
                }
                // add rest tail
                ListNode<E> newLast = header.getPrev();
                newLast.setNext(curr);
                curr.setPrev(newLast);
                header.setPrev(oldLast);
                break;

            }
            ++currIndex;
            curr = curr.getNext();
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ListIterator<E> it = this.listIterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ListIterator<E> it = this.listIterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public void add(int index, E element) {
        addAll(index, Arrays.asList(element));
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > (this.size() - 1))
            throw new IndexOutOfBoundsException();
        ListNode<E> curr = header.getNext();
        int currIndex = 0;
        E removedElement = null;
        while (curr != header) {
            if (currIndex == index) {
                removedElement = curr.getData();
                ListNode<E> prev = curr.getPrev();
                ListNode<E> next = curr.getNext();
                prev.setNext(next);
                next.setPrev(prev);
                break;
            }
            ++currIndex;
            curr = curr.getNext();
        }
        return removedElement;
    }

    @Override
    public int lastIndexOf(Object o) {
        E element = (E) o;
        int index = size() - 1;
        ListNode<E> curr = header.getPrev();
        while (curr != header) {
            if (curr.getData().equals(element))
                return index;
            curr = curr.getPrev();
            --index;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > (this.size() - 1))
            throw new IndexOutOfBoundsException();
        ListIterator<E> result = new CustomListIterator();
        while (result.nextIndex() != index) {
            result.next();
        }
        return result;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        CustomLinkedList<E> resultList = new CustomLinkedList();
        ListIterator<E> it = this.listIterator(fromIndex);
        while (it.nextIndex() != toIndex) {
            resultList.add(it.next());
        }
        return resultList;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size()];
        int index = 0;
        for (E element : this) {
            result[index++] = element;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null)
            throw new NullPointerException();
        int index = 0;
        if (a.length < this.size()) {
            T[] result = (T[]) Array.newInstance(a[0].getClass(), this.size());
            for (E element : this) {
                result[index++] = (T) element;
            }
            return result;
        }

        for (E element : this) {
            a[index++] = (T) element;
        }

        if (a.length > this.size()) {
            a[index] = null;
        }
        
        return a;
    }

    @Override
    public E set(int index, E element) {
        ListIterator<E> it = this.listIterator(index);
        E prevElement = it.next();
        it.set(element);
        return prevElement;
    }
}
