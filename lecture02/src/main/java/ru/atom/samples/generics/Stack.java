package ru.atom.samples.generics;

/**
 * Created by s.rybalkin on 27.09.2016.
 */
public interface Stack<T> {
    void push(T elem);
    T pop();

    // взять верхушку без извлечения
    T peek();
}