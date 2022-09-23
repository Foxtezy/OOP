package ru.nsu.fit.makhov.stack;

import java.util.Arrays;

public class Stack<T> {
  private T[] data;

  private int size = 0;

  public Stack() {
    data = (T[]) new Object[1];
  } // TODO: 23.09.2022 unchecked cast

  public Stack(T[] content) {
    data = Arrays.copyOf(content, content.length);
  }

  public T[] toArray() {
    return Arrays.copyOf(data, size);
  }

  private void grow(int size) {
    this.size += size;
    if (data.length <= size) {
      data = Arrays.copyOf(data, data.length * 2);
    }
  }

  private void trim(int size) {
    this.size -= size;
  }

  public void push(T item) {
    grow(1);
    data[size - 1] = item;
  }

  @SafeVarargs
  public final void pushStack(T... items) { // TODO: 21.09.2022 нормально ли тут использовать vararg?
    grow(items.length);
    System.arraycopy(items, 0, data, size - items.length, items.length);
  }

  public T pop() {
    trim(1);
    return data[size];
  }

  public T[] popStack(int popSize) {
    T[] popArray = (T[]) new Object[popSize]; // TODO: 23.09.2022 unchecked cast
    System.arraycopy(data, size - popSize, popArray, 0, popSize);
    trim(popSize);
    return popArray;
  }

  public int count() {
    return size;
  }
}
