package ru.nsu.fit.makhov.stack;

import java.util.Arrays;

public class Stack<T> {
  private Object[] data;

  private int size = 0;

  public Stack() {
    data = new Object[1];
  }

  public Stack(T[] content) {
    data = Arrays.copyOf(content, content.length);
  }

  public T[] toArray() {
    return (T[]) Arrays.copyOf(data, size); // По идее кастование тут верное
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

  public void pushStack(T... items) { // TODO: 21.09.2022 нормально ли тут использовать vararg?
    grow(items.length);
    System.arraycopy(items, 0, data, size - items.length, items.length);
  }

  public T pop() {
    trim(1);
    return (T) data[size];
  }

  public T[] popStack(int popSize) {
    Object[] popArray = new Object[popSize];
    System.arraycopy(data, size - popSize, popArray, 0, popSize);
    trim(popSize);
    return (T[]) popArray;
  }

  public int count() {
    return size;
  }
}
