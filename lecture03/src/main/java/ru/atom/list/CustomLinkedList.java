package ru.atom.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {

    private int size = 0;
    private int modCount = 0;
    private ListNode<E> first;
    private ListNode<E> last;

    public CustomLinkedList() {
    }

    private ListNode<E> head = null;

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        size();
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }


    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }


    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    private void linkLast(E e) {
        final ListNode<E> l = last;
        final ListNode<E> newListNode = new ListNode<>(l, e, null);
        last = newListNode;
        if (l == null)
            first = newListNode;
        else
            l.next = newListNode;
        size++;
        modCount++;
    }


    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    private E unlink(ListNode<E> x) {
        final E element = x.item;
        final ListNode<E> next = x.next;
        final ListNode<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!contains(object)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }


    ListNode<E> listNode(int index) {
        if (index < (size >> 1)) {
            ListNode<E> currentNode = first;
            for (int i = 0; i < index; i++)
                currentNode = currentNode.next;
            return currentNode;
        } else {
            ListNode<E> currentNode = last;
            for (int i = size - 1; i > index; i--)
                currentNode = currentNode.prev;
            return currentNode;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);

        Object[] object = c.toArray();
        int numNew = object.length;
        if (numNew == 0)
            return false;

        ListNode<E> pred;
        ListNode<E> succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = listNode(index);
            pred = succ.prev;
        }

        for (Object o : object) {
            E element = (E) o;
            ListNode<E> newListNode = new ListNode<>(pred, element, null);
            if (pred == null)
                first = newListNode;
            else
                pred.next = newListNode;
            pred = newListNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new NotImplementedException();
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    @Override
    public void clear() {
        for (ListNode<E> x = first; x != null; ) {
            ListNode<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return listNode(index).item;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (ListNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, listNode(index));
    }

    void linkBefore(E e, ListNode<E> succ) {
        final ListNode<E> pred = succ.prev;
        final ListNode<E> newNode = new ListNode<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(listNode(index));
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size;
        if (o == null) {
            for (ListNode<E> x = last; x != null; x = x.prev) {
                index--;
                if (x.item == null)
                    return index;
            }
        } else {
            for (ListNode<E> x = last; x != null; x = x.prev) {
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new ListItr(index);
    }


    /**
     * * @throws NoSuchElementException if this list is empty
     */

    private class ListItr implements ListIterator<E> {
        private ListNode<E> lastReturned;
        private ListNode<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index) {
            next = (index == size) ? null : listNode(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            ListNode<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        ListNode<E> currentNode = listNode(index);
        E oldVal = currentNode.item;
        currentNode.item = element;
        return oldVal;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> element = iterator();
        while (element.hasNext()) {
            if (c.contains(element.next())) {
                element.remove();
                modified = true;
            }
        }
        return modified;
    }


    /**
     * Methods Do not implement
     */

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }
}
