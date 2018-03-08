package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements List<E> {
    ListNode<E> first;
    int sz;

    CustomLinkedList() {
        first = new ListNode();
        first.Element = null;
        first.Next = first;
        first.Prev = first;
        sz = 0;
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return (sz == 0);
    }

    @Override
    public boolean contains(Object o) {
        if (first.Element == o) {
            //можно проверить в цикле do while, но, наверное, тогда код будет хуже читаться
            //хотя можно и поспорить, что большее зло – do while или лишние проверки до цикла
            return true;
        }
        ListNode<E> tmp = first.Next;

        for (; tmp != first; tmp = tmp.Next) {
            if (tmp.Element == o) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //никогда не писал итераторы, тем более на java
        //честно позаимствовал отсюда: https://toster.ru/q/245173
        return new Iterator<E>() {
            private ListNode<E> tmp = first;
            private int index = 0;

            @Override
            public boolean hasNext() {
                return (tmp.Element != null && index < sz);
            }

            @Override
            public E next() throws IndexOutOfBoundsException {
                E res = tmp.Element;
                if (!hasNext()) throw new IndexOutOfBoundsException("End of list.");
                tmp = tmp.Next;
                index++;
                return res;
            }
        };
    }

    @Override
    public boolean add(E e) {
        if (sz == 0) {
            //в случае, если мы инициализировали, добавили элементы,
            //а потом все удалили
            first = new ListNode();
            first.Element = e;
            first.Next = first;
            first.Prev = first;
        }
        else {
            //ставим между первым и последним
            ListNode<E> newList = new ListNode<>();
            newList.Element = e;
            newList.Next = first;
            newList.Prev = first.Prev;
            first.Prev.Next = newList;
            first.Prev = newList;
        }
        sz++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (sz <= 0 || !contains(o)) {
            return false;
        }
        else {
            if (first.Element == o) {
                first.Prev.Next = first.Next;
                first.Next.Prev = first.Prev;
                first = first.Next;
                sz--;
                return true;
            }
            ListNode<E> tmp = first.Next;

            for (; tmp != first; tmp = tmp.Next) {
                if (tmp.Element == o) {
                    tmp.Prev.Next = tmp.Next;
                    tmp.Next.Prev = tmp.Prev;
                    return true;
                }
            }
            sz--;
        }
        return false;
    }

    @Override
    public void clear() {
        first = null;
        sz = 0;
    }

    @Override
    public E get(int index) {
        if (index >= sz) {
            throw new IndexOutOfBoundsException("Index >= sizeOfList");
        }
        ListNode<E> tmp = first;
        while (index < sz) {
            tmp = tmp.Next;
            index++;
        }
        return tmp.Element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (ListNode<E> tmp = first; tmp != null && index < sz; tmp = tmp.Next) {
            if (o == tmp.Element || o == null && tmp.Element == null) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for ( E newElem : c ) {
            this.add(newElem);
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
}
