package ru.atom.list;


interface Iterable<T> extends Iterator {
    Iterator<T> iterator();

    default void forEach() {

    }

}

